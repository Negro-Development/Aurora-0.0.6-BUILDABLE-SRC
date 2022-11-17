// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world

import com.mojang.authlib.GameProfile
import me.halqq.aurora.client.api.event.events.PacketEvent.PacketReceiveEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.EntityUtil
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.init.Items
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.network.play.server.SPacketExplosion
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.GameType
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent
import java.util.*


class FakePlayer : Module("FakePlayer", Category.WORLD) {

    var pop = create("TotemPop", true)

    private var clonedPlayer: EntityOtherPlayerMP? = null

    @SubscribeEvent
    open fun onPacketReceive(event: PacketReceiveEvent) {
        var damage = 0.0
        var explosion = SPacketExplosion()
        if (clonedPlayer == null) {
            return
        }
        val pos = BlockPos(explosion.x, explosion.y, explosion.z)
        if (event.packet is SPacketExplosion && (event.packet as SPacketExplosion?).also {
                explosion = SPacketExplosion()
            }?.let { clonedPlayer!!.getDistance(it.getX(), explosion.y, explosion.z) }!! <= 15.0 && EntityUtil.calculatePosDamage(
                pos,
                clonedPlayer
            ).also {
                damage = it.toDouble()
            } > 0.0 && pop.value
        ) {
            clonedPlayer!!.health = (clonedPlayer!!.health.toDouble() - MathHelper.clamp(damage, 0.0, 999.0)).toFloat()
        }
    }

    override fun onEnable() {
        if (mc.player == null || mc.player.isDead) {
            onDisable()
            return
        }
        clonedPlayer = EntityOtherPlayerMP(
            mc.world,
            GameProfile(UUID.fromString("48efc40f-56bf-42c3-aa24-28e0c053f325"), "AuroraOnTop")
        )
        clonedPlayer!!.copyLocationAndAnglesFrom(mc.player)
        clonedPlayer!!.rotationYawHead = mc.player.rotationYawHead
        clonedPlayer!!.rotationYaw = mc.player.rotationYaw
        clonedPlayer!!.rotationPitch = mc.player.rotationPitch
        clonedPlayer!!.setGameType(GameType.SURVIVAL)
        mc.world.addEntityToWorld(-12345, clonedPlayer)
        clonedPlayer!!.onLivingUpdate()
    }

    override fun onUpdate() {
        if (pop.value && clonedPlayer != null) {
            clonedPlayer!!.inventory.offHandInventory[0] = ItemStack(Items.TOTEM_OF_UNDYING)
            if (clonedPlayer!!.health <= 0.0f) {
                popEvent(clonedPlayer)
                clonedPlayer!!.health = 20.0f
                clonedPlayer!!.hurtTime = 5
            }
        }
    }

    private fun popEvent(entity: EntityOtherPlayerMP?) {
        mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.TOTEM, 30)
        mc.world.playSound(
            entity!!.posX,
            entity.posY,
            entity.posZ,
            SoundEvents.ITEM_TOTEM_USE,
            entity.soundCategory,
            1.0f,
            1.0f,
            false
        )
    }

    override fun onDisable() {
        if (mc.world != null) {
            mc.world.removeEntityFromWorld(-12345)
        }
    }

    @SubscribeEvent
    fun onClientDisconnect(event: ClientDisconnectionFromServerEvent?) {
        if (isEnabled) {
            onDisable()
        }
    }
}