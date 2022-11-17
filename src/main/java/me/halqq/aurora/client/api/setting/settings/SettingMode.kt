// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting.settings

import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.setting.Setting

class SettingMode(
    name: String?,
    module: Module?,
    private var value: String,
    val modes: List<String>,
    visible: Boolean
) :
    Setting<String>(name, module, visible) {

    override fun getValue(): String {
        return value
    }

    override fun setValue(value: String) {
        this.value = value
    }

    val stringValues: Array<String>
        get() = modes.toTypedArray()

    fun getStringFromIndex(index: Int): String {
        return if (index != -1) {
            stringValues[index]
        } else {
            ""
        }
    }

    val selectedIndex: Int
        get() {
            val modes = stringValues
            var `object` = 0
            for (i in modes.indices) {
                val mode = modes[i]
                if (mode.equals(value, ignoreCase = true)) `object` = i
            }
            return `object`
        }
}
