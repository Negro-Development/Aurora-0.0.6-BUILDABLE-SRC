// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.event.events.PacketEvent.PacketSendEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.item.ItemExpBottle
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.network.play.client.CPacketPlayerTryUseItem
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class FootXp

    : Module("FootXp", Category.MISCELLANEOUS) {
    @SubscribeEvent
    fun onPacketSend(event: PacketSendEvent) {
        if (event.packet is CPacketPlayerTryUseItem && mc.player.heldItemMainhand.getItem() is ItemExpBottle) {
            mc.player.connection.sendPacket(CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround))
        }
    }
}