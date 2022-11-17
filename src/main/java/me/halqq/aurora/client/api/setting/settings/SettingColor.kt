// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting.settings

import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.setting.Setting
import java.awt.Color

class SettingColor(name: String?, module: Module?, private var value: Color, visible: Boolean) :
    Setting<Color>(name, module, visible) {

    override fun getValue(): Color {
        return value
    }

    val red: Int get() = value.red
    val green: Int get() = value.green
    val blue: Int get() = value.blue
    val alpha: Int get() = value.alpha

    override fun setValue(value: Color) {
        this.value = Color(value.red, value.green, value.blue, value.alpha)
    }
}