// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting.settings

import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.setting.Setting

class SettingDouble(
    name: String?,
    module: Module?,
    private var value: Double,
    val minValue: Double,
    val maxValue: Double,
    visible: Boolean
) :
    Setting<Double>(name, module, visible) {

    override fun getValue(): Double {
        return value
    }

    override fun setValue(value: Double) {
        this.value = value
    }
}