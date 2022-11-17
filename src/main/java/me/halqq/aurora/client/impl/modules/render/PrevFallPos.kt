// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.util.utils.RenderUtil
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos

class PrevFallPos : Module("PrevFallPos", Category.RENDER) {

    var red = this.create("Red", 0, 0, 225)
    var green = this.create("Green", 0, 0, 225)
    var blue = this.create("Blue", 170, 0, 225)
    var alpha = this.create("Alpha", 170, 0, 225)
    var pos: BlockPos? = null

    override fun onUpdate() {}

    override fun onRender3D(event: RenderEvent) {
        val fall = mc.player.fallDistance
        if (ModuleManager.INSTANCE.getModule(PrevFallPos::class.java).isEnabled && alpha.value > 1 && fall > 2.0f) {
            pos = BlockPos(mc.player.posX, mc.player.posY - fall.toDouble(), mc.player.posZ)
            val bb = AxisAlignedBB(
                pos!!.getX().toDouble() - mc.getRenderManager().viewerPosX,
                pos!!.getY().toDouble() - mc.getRenderManager().renderPosY,
                pos!!.getZ().toDouble() - mc.getRenderManager().viewerPosZ,
                (pos!!.getX() + 1).toDouble() - mc.getRenderManager().viewerPosX,
                (pos!!.getY() + 1).toDouble() - mc.getRenderManager().viewerPosY,
                (pos!!.getZ() + 1).toDouble() - mc.getRenderManager().viewerPosZ
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
                    red.value.toInt().toFloat(),
                    green.value.toInt().toFloat(),
                    blue.value.toInt().toFloat(),
                    alpha.value.toInt().toFloat()
                )
            }
        }
    }
}