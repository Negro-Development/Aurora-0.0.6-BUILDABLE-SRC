// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.friend.FriendManager
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.InventoryUtil
import me.halqq.aurora.client.api.util.utils.RenderUtil
import net.minecraft.block.BlockWeb
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import java.awt.Color

class AutoWeb : Module("AutoWeb", Category.COMBAT) {
    var range = create("Range", 4, 0, 6)
    var render = create("Render", true)
    var red = create("Red", 0, 0, 225)
    var green = create("Green", 0, 0, 225)
    var blue = create("Blue", 0, 0, 225)
    var alpha = create("Alpha", 0, 0, 225)
    var pos: BlockPos? = null
    override fun onUpdate() {
        val oldslot = mc.player.inventory.currentItem
        for (player in mc.world.playerEntities) if (player !== mc.player) {
            pos = BlockPos(player.posX, player.posY - 1, player.posZ)
            if (!FriendManager.isFriend(player.name)) {
                if (mc.player.getDistance(player) < range.value) {
                    if (InventoryUtil.swapToHotbarSlot(
                            InventoryUtil.findItem(BlockWeb::class.java),
                            false
                        ) == -1
                    ) return
                    mc.connection!!.sendPacket(
                        CPacketPlayerTryUseItemOnBlock(
                            pos,
                            EnumFacing.UP,
                            EnumHand.MAIN_HAND,
                            0.5f,
                            0.5f,
                            0.5f
                        )
                    )
                    if (mc.player.inventory.currentItem != oldslot) {
                        InventoryUtil.swapToHotbarSlot(oldslot, false)
                    }
                }
            }
        }
    }

     override fun onRender3D(event: RenderEvent) {
        for (player in mc.world.playerEntities) if (player !== mc.player) {
            pos = BlockPos(player.posX, player.posY, player.posZ)
            if (render.value) {
                val bb = AxisAlignedBB(
                    pos!!.getX() - mc.getRenderManager().viewerPosX,
                    pos!!.getY() - mc.getRenderManager().viewerPosY,
                    pos!!.getZ() - mc.getRenderManager().viewerPosZ,
                    pos!!.getX() + 1 - mc.getRenderManager().viewerPosX,
                    pos!!.getY() + 1 - mc.getRenderManager().viewerPosY,
                    pos!!.getZ() + 1 - mc.getRenderManager().viewerPosZ
                )
                if (RenderUtil.isInViewFrustrum(
                        AxisAlignedBB(
                            bb.minX + mc.getRenderManager().viewerPosX,
                            bb.minY + mc.getRenderManager().viewerPosY,
                            bb.minZ + mc.getRenderManager().viewerPosZ,
                            bb.maxX + mc.getRenderManager().viewerPosX,
                            bb.maxY + mc.getRenderManager().viewerPosY,
                            bb.maxZ + mc.getRenderManager().viewerPosZ
                        )
                    )
                ) {
                    RenderUtil.drawGradientFilledBox(
                        bb,
                        Color(red.value, green.value, blue.value, alpha.value),
                        Color(red.value, green.value, blue.value, 75)
                    )
                }
            }
        }
    }
}