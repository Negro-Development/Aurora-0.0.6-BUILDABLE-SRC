// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting.settings

import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.setting.Setting


class SettingInteger(
    name: String?,
    module: Module?,
    private var value: Int,
    val minValue: Int,
    val maxValue: Int,
    visible: Boolean
) :
    Setting<Int>(name, module, visible) {

    override fun getValue(): Int {
        return value
    }

    override fun setValue(value: Int) {
        this.value = value
    }
}