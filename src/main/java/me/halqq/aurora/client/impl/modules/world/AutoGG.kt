// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world

import me.halqq.aurora.client.api.event.events.DeathEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.network.play.client.CPacketChatMessage
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class AutoGG : Module("AutoGG", Category.WORLD) {
    var message = create("Message", "Ez! i use aurora client, nigger")
    var range = create("Range", 4, 1, 6)
    @SubscribeEvent
    fun onEntityDeath(event: DeathEvent) {
        val p = event.player
        if (range.value < mc.player.getDistance(p)) {
            if (p.isDead) {
                mc.player.connection.sendPacket(CPacketChatMessage(message.value))
            }
        }
    }
}