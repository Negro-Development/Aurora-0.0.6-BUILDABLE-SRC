// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module


class FastFall : Module("FastFall", Category.MOVEMENT) {

    var bypass = create("Bypass", false)

    override fun onUpdate() {
        if (mc.player.isOnLadder && mc.player.onGround || mc.gameSettings.keyBindJump.isKeyDown) {
            return
        }
        if (!mc.player.isOnLadder && mc.player.onGround) {
            mc.player.motionY = -100.0
        } else if (bypass.value) {
            val player = mc.player
            player.motionY -= (if (bypass.value) 0.62f else 1.0f).toDouble()
        }
    }
}