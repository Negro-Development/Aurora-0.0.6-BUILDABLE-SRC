// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.event.events.PacketEvent.PacketReceiveEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.network.play.server.SPacketEntityVelocity
import net.minecraft.network.play.server.SPacketExplosion
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Velocity : Module("Velocity", Category.MISCELLANEOUS) {
    @SubscribeEvent
    fun onPacketReceive(event: PacketReceiveEvent) {
        if (event.packet is SPacketEntityVelocity) event.isCanceled = true
        if (event.packet is SPacketExplosion) event.isCanceled = true
    }
}