// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.BlockUtil
import me.halqq.aurora.client.api.util.utils.InventoryUtil
import net.minecraft.block.BlockObsidian
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos

class Surround : Module("Surround", Category.COMBAT) {
    var helper = create("Help", true)
    var autodis = create("JumpDisable", true)
    var bps = create("BlockPerSec", 4, 1, 8)
    private val placedblock = 0
    private var y = 0.0
    var placedone = false
    private var placed = 0
    var bpps = bps.value
    private var centerV = false
    override fun onEnable() {
        y = mc.player.posY
        centerV = false
    }

    private fun placeBlocks(blocks: List<BlockPos>) {
        for (bp in blocks) {
            if (placedblock >= bpps) return
            val oldslot = mc.player.inventory.currentItem
            if (InventoryUtil.swapToHotbarSlot(InventoryUtil.findItem(BlockObsidian::class.java), false) == -1) return
            when (BlockUtil.getInstance().isPlaceable(bp)) {
                0 -> {
                    BlockUtil.placeBlock(bp)
                    placedone = true
                    placed++
                }
                1 -> {}
            }
            if (mc.player.inventory.currentItem != oldslot) {
                InventoryUtil.swapToHotbarSlot(oldslot, false)
            }
        }
    }

    private fun onSurround() {
        if (mc.player.posY != y && autodis.value) setDisabled()
        val offset =
            if (mc.world.getBlockState(BlockPos(mc.player.positionVector)).block === Blocks.ENDER_CHEST && mc.player.posY - Math.floor(
                    mc.player.posY
                ) > 0.5
            ) 1 else 0
        if (BlockUtil.getInstance().getUnsafePositions(mc.player.positionVector, offset).size == 0) {
        }
        if (helper.value) {
            placeBlocks(BlockUtil.getInstance().getUnsafePositions(mc.player.positionVector, offset - 1))
        }
        placeBlocks(BlockUtil.getInstance().getUnsafePositions(mc.player.positionVector, offset))
        placed = 0
        placed = 0
    }

    override fun onUpdate() {
        onSurround()
    }
}