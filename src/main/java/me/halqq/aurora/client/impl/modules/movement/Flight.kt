// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module

class Flight : Module("Flight", Category.MOVEMENT) {

    var speed = create("speed", 5, 0, 10)
    var speedold = 0f

    override fun onEnable() {
        speedold = mc.player.capabilities.flySpeed
    }

    override fun onUpdate() {
        mc.player.capabilities.isFlying = true
        mc.player.capabilities.flySpeed = speed.value.toFloat()
    }

    override fun onDisable() {
        mc.player.capabilities.isFlying = false
        mc.player.capabilities.flySpeed = speedold
    }
}