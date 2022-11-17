// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.play.client.CPacketHeldItemChange
import net.minecraft.network.play.client.CPacketPlayerTryUseItem
import net.minecraft.util.EnumHand

class PacketEat : Module("PacketEat", Category.MISCELLANEOUS) {
    var auto = create("AutoEat", false)
    var dick = 0
    override fun onEnable() {
        dick = mc.player.inventory.currentItem
    }

    override fun onUpdate() {
        if (auto.value) {
            val slot = findGap(Items.GOLDEN_APPLE)
            if (findGap(Items.GOLDEN_APPLE) != -1) {
                mc.player.connection.sendPacket(CPacketHeldItemChange(slot))
                mc.player.connection.sendPacket(CPacketPlayerTryUseItem(EnumHand.MAIN_HAND))
            }
        } else {
            val slot = findGap(Items.GOLDEN_APPLE)
            if (findGap(Items.GOLDEN_APPLE) != -1) {
                mc.player.connection.sendPacket(CPacketHeldItemChange(slot))
                mc.player.connection.sendPacket(CPacketPlayerTryUseItem(EnumHand.MAIN_HAND))
                toggle()
            }
        }
    }

    companion object {
        fun findGap(itemToFind: Item): Int {

            var slot = -1
            for (i in 0..8) {
                val stack = mc.player.inventory.getStackInSlot(i)
                if (stack == ItemStack.EMPTY) continue
                stack.getItem()
                val item = stack.getItem()
                if (item != itemToFind) continue
                slot = i
                break
            }
            return slot
        }
    }
}