// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module


class CustomFov : Module("CustomFov", Category.RENDER) {

    var value = create("Value", 120, 0, 160)

    private var save = 0f

    override fun onEnable() {
        save = mc.gameSettings.fovSetting
    }

    override fun onUpdate() {
        mc.gameSettings.fovSetting = value.value.toFloat()
    }

    override fun onDisable() {
        mc.gameSettings.fovSetting = save
    }
}