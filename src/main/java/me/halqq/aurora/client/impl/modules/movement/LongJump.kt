// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module

class LongJump : Module("LongJump", Category.MOVEMENT) {
    var height = create("Height", 1, 1, 10)
    override fun onUpdate() {
        if (mc.player == null) return
        if (mc.player.onGround) {
            mc.player.jump()
            mc.player.motionY = height.value.toDouble()
        }
    }
}