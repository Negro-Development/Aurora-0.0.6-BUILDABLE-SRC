// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting.settings

import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.setting.Setting

class SettingString(name: String?, module: Module?, private var value: String, visible: Boolean) :
    Setting<String>(name, module, visible) {

    override fun getValue(): String {
        return value
    }

    override fun setValue(value: String) {
        this.value = value
    }
}