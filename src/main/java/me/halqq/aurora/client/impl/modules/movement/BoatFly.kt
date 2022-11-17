// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module

class BoatFly : Module("BoatFly", Category.MOVEMENT) {
    var verticalSpeed = create("YSpeed", 1, 1, 10)
    var horizontalSpeed = create("XZSpeed", 1, 1, 10)
    override fun onUpdate() {
        if (mc.player == null) return
        val entity = mc.player.ridingEntity
        if (mc.gameSettings.keyBindJump.isKeyDown) {
            entity.motionY = verticalSpeed.value.toDouble()
        }
        if (mc.gameSettings.keyBindSneak.isKeyDown) {
            entity.motionY = -verticalSpeed.value.toDouble()
        }
        if (mc.gameSettings.keyBindForward.isKeyDown) {
            entity.motionX = -Math.sin(Math.toRadians(mc.player.rotationYaw.toDouble())) * horizontalSpeed.value
            entity.motionZ = Math.cos(Math.toRadians(mc.player.rotationYaw.toDouble())) * horizontalSpeed.value
        }
        if (mc.gameSettings.keyBindBack.isKeyDown) {
            entity.motionX = Math.sin(Math.toRadians(mc.player.rotationYaw.toDouble())) * horizontalSpeed.value
            entity.motionZ = -Math.cos(Math.toRadians(mc.player.rotationYaw.toDouble())) * horizontalSpeed.value
        }
        if (mc.gameSettings.keyBindLeft.isKeyDown) {
            entity.motionX = -Math.sin(Math.toRadians((mc.player.rotationYaw + 90).toDouble())) * horizontalSpeed.value
            entity.motionZ = Math.cos(Math.toRadians((mc.player.rotationYaw + 90).toDouble())) * horizontalSpeed.value
        }
        if (mc.gameSettings.keyBindRight.isKeyDown) {
            entity.motionX = -Math.sin(Math.toRadians((mc.player.rotationYaw - 90).toDouble())) * horizontalSpeed.value
            entity.motionZ = Math.cos(Math.toRadians((mc.player.rotationYaw - 90).toDouble())) * horizontalSpeed.value
        }
    }
}