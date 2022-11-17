// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.event.events.PacketEvent.PacketSendEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.ItemUtil
import net.minecraft.init.Items
import net.minecraft.item.ItemExpBottle
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.network.play.client.CPacketPlayerTryUseItem
import net.minecraft.util.EnumHand
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class AutoXp : Module("AutoXp", Category.COMBAT) {
    private var saveslot = 0
    override fun onEnable() {
        saveslot = mc.player.inventory.currentItem
        mc.player.inventory.currentItem = ItemUtil.switchToHotbarSlot(
            ItemUtil.findHotbarBlock(
                ItemExpBottle::class.java
            ), false
        )
    }

    override fun onUpdate() {
        if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() === Items.EXPERIENCE_BOTTLE) {
            mc.player.connection.sendPacket(CPacketPlayerTryUseItem(EnumHand.MAIN_HAND))
        }
    }

    @SubscribeEvent
    fun onPacketSend(event: PacketSendEvent) {
        if (event.packet is CPacketPlayerTryUseItem && mc.player.heldItemMainhand.getItem() is ItemExpBottle) {
            mc.player.connection.sendPacket(CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround))
        }
    }
}