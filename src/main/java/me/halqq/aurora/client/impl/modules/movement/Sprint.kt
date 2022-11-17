// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module

class Sprint

    : Module("Sprint", Category.MOVEMENT) {

    override fun onUpdate() {
        mc.player.isSprinting = true
    }

}