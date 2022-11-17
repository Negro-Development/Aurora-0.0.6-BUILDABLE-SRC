// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.InventoryUtil
import me.halqq.aurora.client.impl.modules.miscellaneous.PacketEat.Companion.findGap
import net.minecraft.init.Items
import net.minecraft.network.play.client.CPacketHeldItemChange
import net.minecraft.network.play.client.CPacketPlayerTryUseItem
import net.minecraft.util.EnumHand

class SilentPearl : Module("SilentPearl", Category.MISCELLANEOUS) {
    var dick = 0
    override fun onUpdate() {
        val oldslot = mc.player.inventory.currentItem
        val slot: Int = findGap(Items.ENDER_PEARL)
        if (findGap(Items.ENDER_PEARL) !== -1) {
            mc.player.connection.sendPacket(CPacketHeldItemChange(slot))
            mc.player.connection.sendPacket(CPacketPlayerTryUseItem(EnumHand.MAIN_HAND))
            if (mc.player.inventory.currentItem != oldslot) {
                InventoryUtil.swapToHotbarSlot(oldslot, false)
            }
            toggle()
        }
    }
}