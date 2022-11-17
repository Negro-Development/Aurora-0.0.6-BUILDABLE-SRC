// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.event.events

import net.minecraft.network.Packet
import net.minecraftforge.fml.common.eventhandler.Cancelable

@Cancelable
open class PacketEvent(var packet: Packet<*>, stage: Stage?) : AuroraEvent(stage) {

    class PacketSendEvent(packet: Packet<*>, stage: Stage?) :
        PacketEvent(packet, stage)

    class PacketReceiveEvent(packet: Packet<*>, stage: Stage?) :
        PacketEvent(packet, stage)
}