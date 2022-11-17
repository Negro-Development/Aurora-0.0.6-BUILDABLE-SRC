// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.InventoryUtil
import me.halqq.aurora.client.api.util.utils.ItemUtil
import me.halqq.aurora.client.api.util.utils.RenderUtil
import net.minecraft.item.ItemSword
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import java.awt.Color


class NoWeb : Module("NoWeb", Category.COMBAT) {
    var sbreak = create("Break", true)
    var delay = create("Speed", 1, 1, 60)
    var autoS = create("AutoSword", true, false)
    var fastfall = create("FastFall", true)
    var red = create("Red", 0, 0, 225)
    var green = create("Green", 150, 0, 225)
    var blue = create("Blue", 255, 0, 225)
    var alpha = create("Alpha", 175, 0, 225)
    var oldSlot = 0
    var pos: BlockPos? = null
    var pos2: BlockPos? = null
    override fun onEnable() {
        oldSlot = mc.player.inventory.currentItem
    }

    override fun onRender3D(event: RenderEvent) {
        if (mc.player.isInWeb) {
            pos = BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)
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

    override fun onUpdate() {
        delay.visible = false
        if (sbreak.value) {
            delay.visible = true
        }
        pos = BlockPos(mc.player.posX, mc.player.posY + 1 - 1, mc.player.posZ)
        pos2 = BlockPos(mc.player.posX, mc.player.posY + 1, mc.player.posZ)
        if (mc.player.isInWeb) {
            if (sbreak.value) {
                if (autoS.value) {
                    ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemSword::class.java), false)
                }
                if (delay()) {
                    mc.playerController.onPlayerDamageBlock(pos, EnumFacing.DOWN)
                    mc.timer.updateTimer()
                }
                mc.timer.updateTimer()
            }
            if (fastfall.value) {
                mc.player.motionY = -10.0
            }
        }
        if (mc.player.onGround) {
            mc.player.motionY = 0.0
        }
    }

    override fun onDisable() {
        if (autoS.value) {
            if (mc.player.inventory.currentItem != oldSlot) {
                InventoryUtil.swapToHotbarSlot(oldSlot, false)
            }
        }
    }

    fun delay(): Boolean {
        return mc.player.ticksExisted % delay.value == 0
    }
}