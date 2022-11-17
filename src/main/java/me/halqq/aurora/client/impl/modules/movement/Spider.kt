// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module


class Spider : Module("Spider", Category.MOVEMENT) {
    override fun onUpdate() {
        if (mc.player == null) return
        if (mc.player.collidedHorizontally) {
            mc.player.motionY = 0.2
        }
    }
}