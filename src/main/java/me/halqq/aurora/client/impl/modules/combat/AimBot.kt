// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBow
import net.minecraft.util.math.MathHelper

class AimBot : Module("AimBot", Category.COMBAT) {

    var range = create("Range", 1.0, 1.0, 10.0)
    var bow = create("Bow", true)

    override fun onUpdate() {
        if (mc.player == null) return
        if (bow.value) {
            if (mc.player.heldItemMainhand.getItem() is ItemBow) {
                mc.world.playerEntities.stream().filter { entityPlayer: EntityPlayer -> entityPlayer !== mc.player }
                    .filter { entityPlayer: EntityPlayer? ->
                        mc.player.getDistance(
                            entityPlayer
                        ) <= range.value
                    }.forEach { entityPlayer: EntityPlayer ->
                        val rotations = getRotations(entityPlayer)
                        mc.player.rotationYaw = rotations[0]
                        mc.player.rotationPitch = rotations[1]
                    }
            } else {
                mc.world.playerEntities.stream().filter { entityPlayer: EntityPlayer -> entityPlayer !== mc.player }
                    .filter { entityPlayer: EntityPlayer? ->
                        mc.player.getDistance(
                            entityPlayer
                        ) <= range.value
                    }.forEach { entityPlayer: EntityPlayer ->
                        val rotations = getRotations(entityPlayer)
                        mc.player.rotationYaw = rotations[0]
                        mc.player.rotationPitch = rotations[1]
                    }
            }
        }
    }

    fun getRotations(entityPlayer: EntityPlayer): FloatArray {
        val x = entityPlayer.posX - mc.player.posX
        val z = entityPlayer.posZ - mc.player.posZ
        val y = entityPlayer.posY + entityPlayer.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight())
        val dist = MathHelper.sqrt(x * x + z * z).toDouble()
        val yaw = (Math.atan2(z, x) * 180.0 / Math.PI).toFloat() - 90.0f
        val pitch = -(Math.atan2(y, dist) * 180.0 / Math.PI).toFloat()
        return floatArrayOf(yaw, pitch)
    }
}