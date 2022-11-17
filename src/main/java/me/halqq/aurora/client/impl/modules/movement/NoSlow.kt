// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraftforge.client.event.InputUpdateEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class NoSlow

    : Module("NoSlow", Category.MOVEMENT) {
    @SubscribeEvent
    fun onInput(event: InputUpdateEvent) {
        if (mc.player.isHandActive && !mc.player.isRiding) {
            event.movementInput.moveStrafe *= 5f
            event.movementInput.moveForward *= 5f
        }
    }
}