// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.util

import me.halqq.aurora.client.api.gui.util.render.objects.AbstractGradient
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import java.awt.Color

object Render2DUtil : GuiScreen() {
    var zLevel: Double
        get() = this.zLevel.toDouble()
        set(zLevel) {
            super.zLevel = zLevel.toFloat()
        }

        var instance = Render2DUtil
        private val mc = Minecraft.getMinecraft()
        private val shadowCache = HashMap<Int, Int>()

    @JvmStatic
        fun drawAbstract(drawing: AbstractGradient?) {
            drawing?.render()
        }

    @JvmStatic
    fun drawRectWH(x: Double, y: Double, width: Double, height: Double, color: Int) {
            drawRect(x, y, x + width, y + height, color)
        }

    @JvmStatic
        fun drawRect(left: Double, top: Double, right: Double, bottom: Double, color: Int) {
            var left = left
            var top = top
            var right = right
            var bottom = bottom
            var j: Double
            if (left < right) {
                j = left
                left = right
                right = j
            }
            if (top < bottom) {
                j = top
                top = bottom
                bottom = j
            }
            val f3 = (color shr 24 and 255).toFloat() / 255.0f
            val f = (color shr 16 and 255).toFloat() / 255.0f
            val f1 = (color shr 8 and 255).toFloat() / 255.0f
            val f2 = (color and 255).toFloat() / 255.0f
            val tessellator = Tessellator.getInstance()
            val bufferbuilder = tessellator.buffer
            GlStateManager.enableBlend()
            GlStateManager.disableTexture2D()
            GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
            )
            GlStateManager.color(f, f1, f2, f3)
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION)
            bufferbuilder.pos(left, bottom, 0.0).endVertex()
            bufferbuilder.pos(right, bottom, 0.0).endVertex()
            bufferbuilder.pos(right, top, 0.0).endVertex()
            bufferbuilder.pos(left, top, 0.0).endVertex()
            tessellator.draw()
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
        }

    @JvmStatic
    fun drawPolygonPart(x: Double, y: Double, radius: Int, part: Int, color: Int, endcolor: Int) {
            val alpha = (color shr 24 and 0xFF) / 255.0f
            val red = (color shr 16 and 0xFF) / 255.0f
            val green = (color shr 8 and 0xFF) / 255.0f
            val blue = (color and 0xFF) / 255.0f
            val alpha2 = (endcolor shr 24 and 0xFF) / 255.0f
            val red2 = (endcolor shr 16 and 0xFF) / 255.0f
            val green2 = (endcolor shr 8 and 0xFF) / 255.0f
            val blue2 = (endcolor and 0xFF) / 255.0f
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
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR)
            bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex()
            val TWICE_PI = 6.283185307179586
            for (i in part * 90..part * 90 + 90) {
                val angle = 6.283185307179586 * i / 360.0 + Math.toRadians(180.0)
                bufferbuilder.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0.0)
                    .color(red2, green2, blue2, alpha2).endVertex()
            }
            tessellator.draw()
            GlStateManager.shadeModel(7424)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        }

    @JvmStatic
    fun drawVGradientRect(left: Float, top: Float, right: Float, bottom: Float, startColor: Int, endColor: Int) {
            val f = (startColor shr 24 and 0xFF) / 255.0f
            val f2 = (startColor shr 16 and 0xFF) / 255.0f
            val f3 = (startColor shr 8 and 0xFF) / 255.0f
            val f4 = (startColor and 0xFF) / 255.0f
            val f5 = (endColor shr 24 and 0xFF) / 255.0f
            val f6 = (endColor shr 16 and 0xFF) / 255.0f
            val f7 = (endColor shr 8 and 0xFF) / 255.0f
            val f8 = (endColor and 0xFF) / 255.0f
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
            bufferbuilder.pos(right.toDouble(), top.toDouble(), 0.0).color(f2, f3, f4, f).endVertex()
            bufferbuilder.pos(left.toDouble(), top.toDouble(), 0.0).color(f2, f3, f4, f).endVertex()
            bufferbuilder.pos(left.toDouble(), bottom.toDouble(), 0.0).color(f6, f7, f8, f5).endVertex()
            bufferbuilder.pos(right.toDouble(), bottom.toDouble(), 0.0).color(f6, f7, f8, f5).endVertex()
            tessellator.draw()
            GlStateManager.shadeModel(7424)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        }

        fun drawGlow(x: Double, y: Double, x1: Double, y1: Double, color: Int) {
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
            drawVGradientRect(
                x.toInt().toFloat(),
                y.toInt().toFloat(),
                x1.toInt().toFloat(),
                (y + (y1 - y) / 2.0).toInt().toFloat(),
                ColorUtils.injectAlpha(
                    Color(color), 0
                ).rgb,
                color
            )
            drawVGradientRect(
                x.toInt().toFloat(),
                (y + (y1 - y) / 2.0).toInt().toFloat(),
                x1.toInt().toFloat(),
                y1.toInt().toFloat(),
                color,
                ColorUtils.injectAlpha(
                    Color(color), 0
                ).rgb
            )
            val radius = ((y1 - y) / 2.0).toInt()
            drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 0, color, ColorUtils.injectAlpha(Color(color), 0).rgb)
            drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 1, color, ColorUtils.injectAlpha(Color(color), 0).rgb)
            drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 2, color, ColorUtils.injectAlpha(Color(color), 0).rgb)
            drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 3, color, ColorUtils.injectAlpha(Color(color), 0).rgb)
            GlStateManager.shadeModel(7424)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        }

    @JvmStatic
        fun gradient(minX: Int, minY: Int, maxX: Int, maxY: Int, startColor: Int, endColor: Int, left: Boolean) {
            if (left) {
                val startA = (startColor shr 24 and 0xFF) / 255.0f
                val startR = (startColor shr 16 and 0xFF) / 255.0f
                val startG = (startColor shr 8 and 0xFF) / 255.0f
                val startB = (startColor and 0xFF) / 255.0f
                val endA = (endColor shr 24 and 0xFF) / 255.0f
                val endR = (endColor shr 16 and 0xFF) / 255.0f
                val endG = (endColor shr 8 and 0xFF) / 255.0f
                val endB = (endColor and 0xFF) / 255.0f
                GL11.glEnable(GL11.GL_BLEND)
                GL11.glDisable(GL11.GL_TEXTURE_2D)
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
                GL11.glShadeModel(GL11.GL_SMOOTH)
                GL11.glBegin(GL11.GL_POLYGON)
                run {
                    GL11.glColor4f(startR, startG, startB, startA)
                    GL11.glVertex2f(minX.toFloat(), minY.toFloat())
                    GL11.glVertex2f(minX.toFloat(), maxY.toFloat())
                    GL11.glColor4f(endR, endG, endB, endA)
                    GL11.glVertex2f(maxX.toFloat(), maxY.toFloat())
                    GL11.glVertex2f(maxX.toFloat(), minY.toFloat())
                }
                GL11.glEnd()
                GL11.glShadeModel(GL11.GL_FLAT)
                GL11.glEnable(GL11.GL_TEXTURE_2D)
                GL11.glDisable(GL11.GL_BLEND)
            } else instance.drawGradientRect(minX, minY, maxX, maxY, startColor, endColor)
        }

    @JvmStatic
        fun drawLeftGradientRect(left: Int, top: Int, right: Int, bottom: Int, startColor: Int, endColor: Int) {
            val tessellator = Tessellator.getInstance()
            val builder = tessellator.buffer
            GlStateManager.disableTexture2D()
            GlStateManager.enableBlend()
            GlStateManager.disableAlpha()
            GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
            )
            GlStateManager.shadeModel(GL11.GL_SMOOTH)
            builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
            builder.pos(right.toDouble(), top.toDouble(), 0.0).color(
                (endColor shr 24 and 255).toFloat() / 255.0f,
                (endColor shr 16 and 255).toFloat() / 255.0f,
                (endColor shr 8 and 255).toFloat() / 255.0f,
                (endColor shr 24 and 255).toFloat() / 255.0f
            ).endVertex()
            builder.pos(left.toDouble(), top.toDouble(), 0.0).color(
                (startColor shr 16 and 255).toFloat() / 255.0f,
                (startColor shr 8 and 255).toFloat() / 255.0f,
                (startColor and 255).toFloat() / 255.0f,
                (startColor shr 24 and 255).toFloat() / 255.0f
            ).endVertex()
            builder.pos(left.toDouble(), bottom.toDouble(), 0.0).color(
                (startColor shr 16 and 255).toFloat() / 255.0f,
                (startColor shr 8 and 255).toFloat() / 255.0f,
                (startColor and 255).toFloat() / 255.0f,
                (startColor shr 24 and 255).toFloat() / 255.0f
            ).endVertex()
            builder.pos(right.toDouble(), bottom.toDouble(), 0.0).color(
                (endColor shr 24 and 255).toFloat() / 255.0f,
                (endColor shr 16 and 255).toFloat() / 255.0f,
                (endColor shr 8 and 255).toFloat() / 255.0f,
                (endColor shr 24 and 255).toFloat() / 255.0f
            ).endVertex()
            tessellator.draw()
            GlStateManager.shadeModel(GL11.GL_FLAT)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        }

    @JvmStatic
        fun drawRoundedRect2(x: Double, y: Double, width: Double, height: Double, radius: Double, color: Int) {
            var x = x
            var y = y
            var x1 = x + width
            var y1 = y + height
            val f = (color shr 24 and 0xFF) / 255.0f
            val f1 = (color shr 16 and 0xFF) / 255.0f
            val f2 = (color shr 8 and 0xFF) / 255.0f
            val f3 = (color and 0xFF) / 255.0f
            GL11.glPushAttrib(0)
            GL11.glScaled(0.5, 0.5, 0.5)
            GlStateManager.enableBlend()
            x *= 2.0
            y *= 2.0
            x1 *= 2.0
            y1 *= 2.0
            GL11.glColor4f(f1, f2, f3, f)
            GL11.glDisable(3553)
            GL11.glEnable(2848)
            GL11.glBegin(9)
            var i: Int
            i = 0
            while (i <= 90) {
                GL11.glVertex2d(
                    x + radius + Math.sin(i * Math.PI / 180.0) * radius * -1.0,
                    y + radius + Math.cos(i * Math.PI / 180.0) * radius * -1.0
                )
                i += 3
            }
            i = 90
            while (i <= 180) {
                GL11.glVertex2d(
                    x + radius + Math.sin(i * Math.PI / 180.0) * radius * -1.0,
                    y1 - radius + Math.cos(i * Math.PI / 180.0) * radius * -1.0
                )
                i += 3
            }
            i = 0
            while (i <= 90) {
                GL11.glVertex2d(
                    x1 - radius + Math.sin(i * Math.PI / 180.0) * radius,
                    y1 - radius + Math.cos(i * Math.PI / 180.0) * radius
                )
                i += 3
            }
            i = 90
            while (i <= 180) {
                GL11.glVertex2d(
                    x1 - radius + Math.sin(i * Math.PI / 180.0) * radius,
                    y + radius + Math.cos(i * Math.PI / 180.0) * radius
                )
                i += 3
            }
            GL11.glEnd()
            GL11.glEnable(3553)
            GL11.glDisable(2848)
            GL11.glEnable(3553)
            GlStateManager.disableBlend()
            GL11.glScaled(2.0, 2.0, 2.0)
            GL11.glPopAttrib()
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        }

    @JvmStatic
        fun drawOutlineRect(x1: Double, y1: Double, x2: Double, y2: Double, lineWidth: Double, color: Int) {
            drawRect(x1, y1, x2, y1 + lineWidth, color)
            drawRect(x1, y2 - lineWidth, x2, y2, color)
            drawRect(x1, y1, x1 + lineWidth, y2, color)
            drawRect(x2 - lineWidth, y1, x2, y2, color)
        }

    @JvmStatic
        fun drawGradientRect(left: Double, top: Double, right: Double, bottom: Double, startColor: Int, endColor: Int) {
            val f = (startColor shr 24 and 255).toFloat() / 255.0f
            val f1 = (startColor shr 16 and 255).toFloat() / 255.0f
            val f2 = (startColor shr 8 and 255).toFloat() / 255.0f
            val f3 = (startColor and 255).toFloat() / 255.0f
            val f4 = (endColor shr 24 and 255).toFloat() / 255.0f
            val f5 = (endColor shr 16 and 255).toFloat() / 255.0f
            val f6 = (endColor shr 8 and 255).toFloat() / 255.0f
            val f7 = (endColor and 255).toFloat() / 255.0f
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
            bufferbuilder.pos(right, top, instance.zLevel.toDouble()).color(f1, f2, f3, f).endVertex()
            bufferbuilder.pos(left, top, instance.zLevel.toDouble()).color(f1, f2, f3, f).endVertex()
            bufferbuilder.pos(left, bottom, instance.zLevel.toDouble()).color(f5, f6, f7, f4).endVertex()
            bufferbuilder.pos(right, bottom, instance.zLevel.toDouble()).color(f5, f6, f7, f4).endVertex()
            tessellator.draw()
            GlStateManager.shadeModel(7424)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        }

    @JvmStatic
        fun drawTexturedRect(x: Float, y: Float, width: Float, height: Float, filter: Int) {
            drawTexturedRect(x, y, width, height, 0f, 1f, 0f, 1f, filter)
        }

    @JvmStatic
        @JvmOverloads
        fun drawTexturedRect(
            x: Float,
            y: Float,
            width: Float,
            height: Float,
            uMin: Float = 0f,
            uMax: Float = 1f,
            vMin: Float = 0f,
            vMax: Float = 1f,
            filter: Int = GL11.GL_NEAREST
        ) {
            GlStateManager.enableBlend()
            GL14.glBlendFuncSeparate(
                GL11.GL_SRC_ALPHA,
                GL11.GL_ONE_MINUS_SRC_ALPHA,
                GL11.GL_ONE,
                GL11.GL_ONE_MINUS_SRC_ALPHA
            )
            drawTexturedRectNoBlend(x, y, width, height, uMin, uMax, vMin, vMax, filter)
            GlStateManager.disableBlend()
        }

    @JvmStatic
        fun drawTexturedRectNoBlend(
            x: Float,
            y: Float,
            width: Float,
            height: Float,
            uMin: Float,
            uMax: Float,
            vMin: Float,
            vMax: Float,
            filter: Int
        ) {
            GlStateManager.enableTexture2D()
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter)
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter)
            val tessellator = Tessellator.getInstance()
            val builder = tessellator.buffer
            builder.begin(7, DefaultVertexFormats.POSITION_TEX)
            builder.pos(x.toDouble(), (y + height).toDouble(), 0.0).tex(uMin.toDouble(), vMax.toDouble()).endVertex()
            builder.pos((x + width).toDouble(), (y + height).toDouble(), 0.0).tex(uMax.toDouble(), vMax.toDouble()).endVertex()
            builder.pos((x + width).toDouble(), y.toDouble(), 0.0).tex(uMax.toDouble(), vMin.toDouble()).endVertex()
            builder.pos(x.toDouble(), y.toDouble(), 0.0).tex(uMin.toDouble(), vMin.toDouble()).endVertex()
            tessellator.draw()
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
        }

    @JvmStatic
        fun drawBorderedRect(
            x: Double,
            y: Double,
            width: Double,
            height: Double,
            lineWidth: Float,
            lineColor: Int,
            bgColor: Int
        ) {
            drawRect(x, y, x + width, y + height, bgColor)
            val f = (lineColor shr 24 and 255).toFloat() / 255.0f
            val f1 = (lineColor shr 16 and 255).toFloat() / 255.0f
            val f2 = (lineColor shr 8 and 255).toFloat() / 255.0f
            val f3 = (lineColor and 255).toFloat() / 255.0f
            GL11.glPushMatrix()
            GL11.glPushAttrib(1048575)
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glDisable(GL11.GL_CULL_FACE)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glColor4f(f1, f2, f3, f)
            GL11.glLineWidth(lineWidth)
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glBegin(GL11.GL_LINES)
            GL11.glVertex2d(x, y)
            GL11.glVertex2d(x + width, y)
            GL11.glVertex2d(x + width, y)
            GL11.glVertex2d(x + width, y + height)
            GL11.glVertex2d(x + width, y + height)
            GL11.glVertex2d(x, y + height)
            GL11.glVertex2d(x, y + height)
            GL11.glVertex2d(x, y)
            GL11.glEnd()
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glEnable(GL11.GL_CULL_FACE)
            GL11.glDisable(GL11.GL_BLEND)
            GL11.glPopAttrib()
            GL11.glPopMatrix()
        }

    @JvmStatic
        fun isHovered(mouseX: Double, mouseY: Double, x: Double, y: Double, width: Double, height: Double): Boolean {
            return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y
        }

    @JvmStatic
        @JvmOverloads
        fun startScissor(x: Double, y: Double, width: Double, height: Double, factor: Double = 1.0) {
            val resolution = ScaledResolution(mc)
            var scaleWidth = mc.displayWidth.toDouble() / resolution.scaledWidth_double
            var scaleHeight = mc.displayHeight.toDouble() / resolution.scaledHeight_double
            scaleWidth *= factor
            scaleHeight *= factor
            GL11.glScissor(
                (x * scaleWidth).toInt(),
                mc.displayHeight - ((y + height) * scaleHeight).toInt(),
                (width * scaleWidth).toInt(),
                (height * scaleHeight).toInt()
            )
            GL11.glEnable(GL11.GL_SCISSOR_TEST)
        }

    @JvmStatic
        fun stopScissor() {
            GL11.glDisable(GL11.GL_SCISSOR_TEST)
        }
    }