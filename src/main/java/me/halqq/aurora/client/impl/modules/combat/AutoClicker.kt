// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import java.util.*

class AutoClicker : Module("AutoClicker", Category.COMBAT) {
    var delay = create("Speed", 22, 0, 100)
    var mode = create("Mode", "Left", Arrays.asList("Attack", "Place"))
    override fun onUpdate() {
        if (mode.value.equals("Attack", ignoreCase = true)) {
            mc.gameSettings.keyBindAttack.pressTime = delay.value
        }
        if (mode.value.equals("Place", ignoreCase = true)) {
            mc.gameSettings.keyBindUseItem.pressTime = delay.value
        }
    }
}