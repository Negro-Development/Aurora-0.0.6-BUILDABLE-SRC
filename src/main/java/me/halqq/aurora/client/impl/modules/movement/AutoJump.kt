// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.MiscUtil

class AutoJump : Module("AutoJump", Category.MOVEMENT) {

    var timer = MiscUtil()

    override fun onUpdate() {
        if (mc.player.onGround && timer.setTick()) {
            mc.player.jump()
        }
    }
}