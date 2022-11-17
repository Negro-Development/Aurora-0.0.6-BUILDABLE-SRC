// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module

class TimerChanger : Module("TimeChanger", Category.WORLD) {
    var time = create("Time", 18000, 0, 24000)
    override fun onRender2D() {
        if (fullNullCheck()) return
        mc.world.worldTime = time.value.toLong()
        mc.world.totalWorldTime = time.maxValue.toLong()
    }

    override fun onUpdate() {
        if (fullNullCheck()) return
        mc.world.worldTime = time.value.toLong()
        mc.world.totalWorldTime = time.maxValue.toLong()
    }
}