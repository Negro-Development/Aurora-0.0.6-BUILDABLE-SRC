// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat.autotrap

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.BlockUtil
import java.util.*

class AutoTrap : Module("AutoTrap", Category.COMBAT) {
    var modeTrap = create("Mode", "Full", Arrays.asList("Full", "Face"))

    init {
        INSTANCE = this
    }

    override fun onUpdate() {
        BlockUtil.placeBlock(pos.Full[0])
    }

    companion object {
        lateinit var INSTANCE: AutoTrap
    }
}