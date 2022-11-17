// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.event.events.PacketEvent.PacketSendEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.network.play.client.CPacketInput
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Freecam : Module("Freecam", Category.MISCELLANEOUS) {

    var xspeed = create("HorizontalSpeed", 7.0, 0.0, 1.0)
    var yspeed = create("VerticalSpeed", 0.5, 0.0, 1.0)

    var x = 0.0
    var y = 0.0
    var z = 0.0
    var yaw = 0f
    var pitch = 0f

    var entityOtherPlayerMP: EntityOtherPlayerMP? = null

    @SubscribeEvent
    fun onPacketSend(event: PacketSendEvent) {
        if (event.packet is CPacketPlayer || event.packet is CPacketInput) {
            event.isCanceled = true
        }
    }

    override fun onEnable() {
        if (mc.player == null) return
        x = mc.player.posX
        y = mc.player.posY
        z = mc.player.posZ
        yaw = mc.player.rotationYaw
        pitch = mc.player.rotationPitch
        entityOtherPlayerMP = EntityOtherPlayerMP(mc.world, mc.getSession().profile)
        entityOtherPlayerMP!!.copyLocationAndAnglesFrom(mc.player)
        entityOtherPlayerMP!!.rotationYawHead = mc.player.rotationYawHead
        mc.world.addEntityToWorld(-100, entityOtherPlayerMP)
        mc.player.noClip = true
    }

    override fun onDisable() {
        if (mc.player == null) return
        x = 0.0
        y = 0.0
        z = 0.0
        yaw = 0f
        pitch = 0f
        mc.player.setPositionAndRotation(x, y, z, yaw, pitch)
        mc.world.removeEntityFromWorld(-100)
        mc.player.noClip = false
    }

    @SubscribeEvent
    fun push(event: PlayerSPPushOutOfBlocksEvent) {
        event.isCanceled = true
    }

    override fun onUpdate() {

        if (mc.player == null) return

        mc.player.noClip = true

        mc.player.setVelocity(0.0, 0.0, 0.0)

        mc.player.jumpMovementFactor = 0.05f

        if (mc.gameSettings.keyBindJump.isKeyDown) {
            mc.player.motionY += yspeed.value
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown) {
            mc.player.motionY -= yspeed.value
        }

        if (mc.gameSettings.keyBindForward.isKeyDown) {
            mc.player.motionX -= Math.sin(Math.toRadians(yaw.toDouble())) * xspeed.value
            mc.player.motionZ += Math.cos(Math.toRadians(yaw.toDouble())) * xspeed.value
        }

        if (mc.gameSettings.keyBindBack.isKeyDown) {
            mc.player.motionX += Math.sin(Math.toRadians(yaw.toDouble())) * xspeed.value
            mc.player.motionZ -= Math.cos(Math.toRadians(yaw.toDouble())) * xspeed.value
        }

        if (mc.gameSettings.keyBindLeft.isKeyDown) {
            mc.player.motionX -= Math.sin(Math.toRadians((yaw - 90).toDouble())) * xspeed.value
            mc.player.motionZ += Math.cos(Math.toRadians((yaw - 90).toDouble())) * xspeed.value
        }

        if (mc.gameSettings.keyBindRight.isKeyDown) {
            mc.player.motionX -= Math.sin(Math.toRadians((yaw + 90).toDouble())) * xspeed.value
            mc.player.motionZ += Math.cos(Math.toRadians((yaw + 90).toDouble())) * xspeed.value
        }
    }
}