// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting.settings

import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.setting.Setting



class SettingBoolean(name: String, module: Module, private var value: Boolean, visible: Boolean) :

    Setting<Boolean>(name, module, visible) {

    override fun setValue(value: Boolean) {
        this.value = value;
    }

     override fun getValue(): Boolean {
        return value;
    }

}