// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.client

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.impl.Aurora

class HalqGui : Module("HalqGui", Category.OTHER) {
    override fun onEnable() {
        toggle()
    }
}