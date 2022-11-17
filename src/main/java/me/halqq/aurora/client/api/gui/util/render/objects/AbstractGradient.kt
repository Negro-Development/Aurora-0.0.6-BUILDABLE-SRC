// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.util.render.objects

import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import java.awt.Color

class AbstractGradient @JvmOverloads constructor(
    var vec: Vec4d,
    var start: Color,
    var end: Color,
    var vertical: Boolean = false
) :
    Gui() {
    var width = 0f
    fun render() {
        GL11.glPushMatrix()
        if (vertical) {
            val f = (start.rgb shr 24 and 255).toFloat() / 255.0f
            val f1 = (start.rgb shr 16 and 255).toFloat() / 255.0f
            val f2 = (start.rgb shr 8 and 255).toFloat() / 255.0f
            val f3 = (start.rgb and 255).toFloat() / 255.0f
            val f4 = (end.rgb shr 24 and 255).toFloat() / 255.0f
            val f5 = (end.rgb shr 16 and 255).toFloat() / 255.0f
            val f6 = (end.rgb shr 8 and 255).toFloat() / 255.0f
            val f7 = (end.rgb and 255).toFloat() / 255.0f
            GlStateManager.disableTexture2D()
            GlStateManager.enableBlend()
            GlStateManager.disableAlpha()
            GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
            )
            GlStateManager.shadeModel(7425)
            val tessellator = Tessellator.getInstance()
            val bufferbuilder = tessellator.buffer
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR)
            bufferbuilder.pos(vec.x2, vec.y2, zLevel.toDouble()).color(f1, f2, f3, f).endVertex()
            bufferbuilder.pos(vec.x1, vec.y1, zLevel.toDouble()).color(f1, f2, f3, f).endVertex()
            bufferbuilder.pos(vec.x4, vec.y4, zLevel.toDouble()).color(f5, f6, f7, f4).endVertex()
            bufferbuilder.pos(vec.x3, vec.y3, zLevel.toDouble()).color(f5, f6, f7, f4).endVertex()
            tessellator.draw()
            GlStateManager.shadeModel(7424)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        } else {
            val startA = (start.rgb shr 24 and 0xFF) / 255.0f
            val startR = (start.rgb shr 16 and 0xFF) / 255.0f
            val startG = (start.rgb shr 8 and 0xFF) / 255.0f
            val startB = (start.rgb and 0xFF) / 255.0f
            val endA = (end.rgb shr 24 and 0xFF) / 255.0f
            val endR = (end.rgb shr 16 and 0xFF) / 255.0f
            val endG = (end.rgb shr 8 and 0xFF) / 255.0f
            val endB = (end.rgb and 0xFF) / 255.0f
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glShadeModel(GL11.GL_SMOOTH)
            GL11.glBegin(GL11.GL_POLYGON)
            run {
                GL11.glColor4f(startR, startG, startB, startA)
                GL11.glVertex2d(vec.x1, vec.y1)
                GL11.glVertex2d(vec.x4, vec.y4)
                GL11.glColor4f(endR, endG, endB, endA)
                GL11.glVertex2d(vec.x3, vec.y3)
                GL11.glVertex2d(vec.x2, vec.y2)
            }
            GL11.glEnd()
            GL11.glShadeModel(GL11.GL_FLAT)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(GL11.GL_BLEND)
        }
        GL11.glPopMatrix()
    }
}