// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.PlayerUtil
import net.minecraft.util.math.Vec3d
import org.lwjgl.opengl.GL11

class Breadcrumbs : Module("Breadcrumbs", Category.RENDER) {

    var width = create("Width", 2, 1, 6)
    var red = create("Red", 255, 0, 255)
    var green = create("Green", 0, 0, 255)
    var blue = create("Blue", 255, 0, 255)
    var alpha = create("Alpha", 255, 0, 255)

    var positions = ArrayList<Vec3d>()

    override fun onUpdate() {
        if (mc.player == null) return
        if (PlayerUtil.isMoving(mc.player)) {
            positions.add(mc.player.positionVector)
        }
    }

    override fun onDisable() {
        positions.clear()
    }

    override fun onEnable() {
        positions.clear()
    }

    override fun onRender3D(event: RenderEvent) {
        if (mc.player == null) return

        positions.add(mc.player.positionVector)

        val renderPosX = mc.getRenderManager().renderPosX
        val renderPosY = mc.getRenderManager().renderPosY
        val renderPosZ = mc.getRenderManager().renderPosZ

        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST)
        GL11.glLineWidth(width.value.toFloat())
        GL11.glColor4f(red.value / 255f, green.value / 255f, blue.value / 255f, alpha.value / 255f)
        GL11.glBegin(GL11.GL_LINE_STRIP)

        for (vec3d in positions) {
            GL11.glVertex3d(vec3d.x - renderPosX, vec3d.y - renderPosY, vec3d.z - renderPosZ)
        }

        GL11.glEnd()
        GL11.glDisable(GL11.GL_LINE_SMOOTH)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glDisable(GL11.GL_LINE_SMOOTH)
        GL11.glPopMatrix()
    }
}