// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.InventoryUtil
import net.minecraft.block.BlockWeb
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos

class SelfWeb : Module("SelfWeb", Category.COMBAT) {
    var pos: BlockPos? = null
    var autodis = create("AutoDisable", true)
    override fun onUpdate() {
        doPlaceWeb()
    }

    fun doPlaceWeb() {
        val oldslot = mc.player.inventory.currentItem
        pos = BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)
        if (InventoryUtil.swapToHotbarSlot(InventoryUtil.findItem(BlockWeb::class.java), false) == -1) return
        mc.player.connection.sendPacket(
            CPacketPlayerTryUseItemOnBlock(
                pos,
                EnumFacing.UP,
                EnumHand.MAIN_HAND,
                0.0f,
                0.0f,
                0.0f
            )
        )
        if (mc.player.inventory.currentItem != oldslot) {
            InventoryUtil.swapToHotbarSlot(oldslot, false)
        }
        if (autodis.value) {
            toggle()
        }
    }
}