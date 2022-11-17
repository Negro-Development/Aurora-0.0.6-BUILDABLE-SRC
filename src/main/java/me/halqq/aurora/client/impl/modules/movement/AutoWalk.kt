// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.client.settings.KeyBinding

class AutoWalk : Module("AutoWalk", Category.MOVEMENT) {

    var sprint = create("Sprint", false)

    override fun onUpdate() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true)
        if (sprint.value) {
            mc.player.isSprinting = true
        }
    }

    override fun onDisable() {
        mc.player.isSprinting = false
    }
}