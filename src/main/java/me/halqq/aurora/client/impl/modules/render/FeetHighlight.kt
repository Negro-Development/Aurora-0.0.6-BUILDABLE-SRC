// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.RenderUtil
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos

class FeetHighlight : Module("FeetHighlight", Category.RENDER) {

    var red = create("Red", 255, 0, 225)
    var green = create("Green", 0, 0, 225)
    var blue = create("Blue", 255, 0, 225)
    var alpha = create("Alpha", 170, 0, 225)

    var pos: BlockPos? = null

    override fun onUpdate() {}

    override fun onRender3D(event: RenderEvent) {
        if (mc.player.onGround) {
            pos = BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)
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
                RenderUtil.drawESP(
                    bb,
                    red.value.toFloat(),
                    green.value.toFloat(),
                    blue.value.toFloat(),
                    alpha.value.toFloat()
                )
            }
        }
    }
}