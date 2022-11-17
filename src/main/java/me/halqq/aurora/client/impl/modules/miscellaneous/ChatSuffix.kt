// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.event.events.PacketEvent.PacketSendEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.network.play.client.CPacketChatMessage
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ChatSuffix

    : Module("ChatSuffix", Category.MISCELLANEOUS) {
    var aurora = create("AuroraMode", true)
    var client = create("Prefix", "~")

    @SubscribeEvent
    fun onPacketSend(event: PacketSendEvent) {
        if (event.packet is CPacketChatMessage) {
            if ((event.packet as CPacketChatMessage).getMessage()
                    .startsWith("/") || (event.packet as CPacketChatMessage).getMessage().startsWith(client.value)
            ) return
            if (aurora.value) {
                (event.packet as CPacketChatMessage).message =
                    (event.packet as CPacketChatMessage).getMessage() + " \u23d0 " + "aurora"
            }
        }
    }
}