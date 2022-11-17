// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module

class Timer : Module("Timer", Category.WORLD) {
    var timer = create("Timer", 2.0, 0.0, 10.0)
    override fun onUpdate() {
        mc.timer.tickLength = (50.0 / timer.value).toFloat()
    }

    override fun onDisable() {
        mc.timer.tickLength = 50f
    }
}