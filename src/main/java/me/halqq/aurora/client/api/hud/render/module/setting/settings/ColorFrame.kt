// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud.render.module.setting.settings

import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.hud.render.module.ModuleFrame
import me.halqq.aurora.client.api.hud.render.module.setting.SettingFrame
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.settings.SettingColor
import me.halqq.aurora.client.api.util.Minecraftable
import me.halqq.aurora.client.api.util.utils.RenderUtil
import me.halqq.aurora.client.impl.modules.client.ClickGui
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException

class ColorFrame(module: ModuleFrame, x: Int, y: Int, private val setting: SettingColor) :
    SettingFrame(module, x, y, module.width - 2, module.height) {
    var pickingColor = false
    var pickingHue = false
    var pickingAlpha = false
    private var finalColor: Color? = null
    var isOpen = false
    var isSelected = false
    lateinit var color: FloatArray
    var pickerX = 0
    var pickerY = 0
    var hueSliderX = 0
    var hueSliderY = 0
    var alphaSliderX = 0
    var alphaSliderY = 0
    var pickerWidth = 0
    var pickerHeight = 0
    var hueSliderWidth = 0
    var hueSliderHeight = 0
    var alphaSliderWidth = 0
    var alphaSliderHeight = 0
    override fun drawScreen(mouseX: Int, mouseY: Int) {
        if (!isOpen) {
            RenderUtil.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + width + 2).toFloat(),
                (y + height).toFloat(),
                ModuleManager.INSTANCE.getModule(
                    ClickGui::class.java
                ).color.rgb
            )
            height = 15
            TextManager.INSTANCE.drawStringWithShadow(
                setting.name, (x + 2).toFloat(), (y + 2).toFloat(), -1, ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
        } else {
            RenderUtil.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + width + 1).toFloat(),
                (y + height).toFloat(),
                ModuleManager.INSTANCE.getModule(
                    ClickGui::class.java
                ).color.rgb
            )

            if (isOpen) {
                height = 122
                if (isSelected) height = 10
            }


            if (ModuleManager.INSTANCE.getModule(CustomFont::class.java).isEnabled) {
                TextManager.INSTANCE.drawStringWithShadow(
                    setting.name, (x + 2).toFloat(), (y + 2).toFloat(), -1, ModuleManager.INSTANCE.getModule(
                        CustomFont::class.java
                    ).isEnabled
                )
            } else {
                Minecraftable.mc.fontRenderer.drawStringWithShadow(
                    setting.name,
                    (x + 2).toFloat(),
                    (y + 2).toFloat(),
                    -1
                )
            }
            if (isOpen) {
                RenderUtil.drawRect(
                    (x + 1).toFloat(),
                    (y + height).toFloat(),
                    (x + width).toFloat(),
                    (y + if (isSelected) 130 else 120).toFloat(),
                    ModuleManager.INSTANCE.getModule(
                        ClickGui::class.java
                    ).color.rgb
                )
                drawPicker(setting, x + 1, y + 12, x + 80, y + 12, x + 1, y + 94, mouseX, mouseY)
                if (isSelected) {
                    height = 130
                }
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        pickingAlpha = false
        pickingColor = false
        pickingHue = false
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (isInsideHex(mouseX, mouseY) && button == 1) isSelected = !isSelected
        if (isInsideButtonOnly(mouseX, mouseY) && button == 1) isOpen = !isOpen
    }

    fun isInsideCopy(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x + 1 && mouseX < x + 107 / 2f && mouseY > y + 120 && mouseY < y + 130
    }

    fun isInsidePaste(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x + 107 / 2f && mouseX < x + 109 && mouseY > y + 120 && mouseY < y + 130
    }

    fun isInsideHex(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x + 1 && mouseX < x + 109 && mouseY > y + 107 && mouseY < y + 120
    }

    fun isInsideButtonOnly(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + 10
    }

    fun drawPicker(
        setting: SettingColor, pickerX: Int, pickerY: Int, hueSliderX: Int, hueSliderY: Int,
        alphaSliderX: Int, alphaSliderY: Int, mouseX: Int, mouseY: Int
    ) {
        try {
            color = floatArrayOf(
                Color.RGBtoHSB(
                    setting.value.red, setting.value.green, setting.value
                        .blue, null
                )[0], Color.RGBtoHSB(
                    setting.value.red, setting.value.green, setting.value
                        .blue, null
                )[1], Color.RGBtoHSB(
                    setting.value.red, setting.value.green, setting.value
                        .blue, null
                )[2], setting.value.alpha / 255f
            )
        } catch (ignored: Exception) {
        }
        pickerWidth = 78
        pickerHeight = 80
        hueSliderWidth = 4
        hueSliderHeight = 90
        alphaSliderWidth = 90
        alphaSliderHeight = 9
        if (!pickingColor && !pickingHue && !pickingAlpha) {
            pickingColor = Mouse.isButtonDown(0) && mouseOver(
                pickerX,
                pickerY,
                pickerX + pickerWidth,
                pickerY + pickerHeight,
                mouseX,
                mouseY
            )
            pickingHue = Mouse.isButtonDown(0) && mouseOver(
                hueSliderX,
                hueSliderY,
                hueSliderX + hueSliderWidth,
                hueSliderY + hueSliderHeight,
                mouseX,
                mouseY
            )
            pickingAlpha = Mouse.isButtonDown(0) && mouseOver(
                alphaSliderX,
                alphaSliderY,
                alphaSliderX + alphaSliderWidth,
                alphaSliderY + alphaSliderHeight,
                mouseX,
                mouseY
            )
        }
        if (pickingHue) {
            val restrictedY = Math.min(Math.max(hueSliderY, mouseY), hueSliderY + hueSliderHeight).toFloat()
            color[0] = (restrictedY - hueSliderY.toFloat()) / hueSliderHeight
            color[0] = Math.min(0.97, color[0].toDouble()).toFloat()
        }
        if (pickingAlpha) {
            val restrictedX = Math.min(Math.max(alphaSliderX, mouseX), alphaSliderX + pickerWidth).toFloat()
            color[3] = 1 - (restrictedX - alphaSliderX.toFloat()) / pickerWidth
            color[3] = Math.max(0.022222223, color[3].toDouble()).toFloat()
        }
        if (pickingColor) {
            val restrictedX = Math.min(Math.max(pickerX, mouseX), pickerX + pickerWidth).toFloat()
            val restrictedY = Math.min(Math.max(pickerY, mouseY), pickerY + pickerHeight).toFloat()
            color[1] = (restrictedX - pickerX.toFloat()) / pickerWidth
            color[2] = 1 - (restrictedY - pickerY.toFloat()) / pickerHeight
            color[2] = Math.max(0.04000002, color[2].toDouble()).toFloat()
            color[1] = Math.max(0.022222223, color[1].toDouble()).toFloat()
        }
        val selectedColor = Color.HSBtoRGB(color[0], 1.0f, 1.0f)
        val selectedRed = (selectedColor shr 16 and 0xFF) / 255.0f
        val selectedGreen = (selectedColor shr 8 and 0xFF) / 255.0f
        val selectedBlue = (selectedColor and 0xFF) / 255.0f
        drawPickerBase(pickerX, pickerY, pickerWidth, pickerHeight, selectedRed, selectedGreen, selectedBlue, 255f)
        drawHueSlider(hueSliderX, hueSliderY, hueSliderWidth, hueSliderHeight, color[0])
        val cursorX = (pickerX + color[1] * pickerWidth).toInt()
        val cursorY = (pickerY + pickerHeight - color[2] * pickerHeight).toInt()
        Gui.drawRect(cursorX - 2, cursorY - 2, cursorX + 2, cursorY + 2, -1)
        finalColor = getColor(
            Color(
                Color.HSBtoRGB(
                    color[0], color[1], color[2]
                )
            ), color[3]
        )
        drawAlphaSlider(
            alphaSliderX,
            alphaSliderY,
            pickerWidth,
            alphaSliderHeight,
            finalColor!!.red / 255f,
            finalColor!!.green / 255f,
            finalColor!!.blue / 255f,
            color[3]
        )
        setting.value = finalColor as Color
    }

    fun drawHueSlider(x: Int, y: Int, width: Int, height: Int, hue: Float) {
        var y = y
        var step = 0
        if (height > width) {
            RenderUtil.drawRect(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + 4).toFloat(), -0x10000)
            y += 4
            for (colorIndex in 0..5) {
                val previousStep = Color.HSBtoRGB(step.toFloat() / 6, 1.0f, 1.0f)
                val nextStep = Color.HSBtoRGB((step + 1).toFloat() / 6, 1.0f, 1.0f)
                drawGradientRect(
                    x.toFloat(),
                    y + step * (height / 6f),
                    (x + width).toFloat(),
                    y + (step + 1) * (height / 6f),
                    previousStep,
                    nextStep,
                    false
                )
                step++
            }
            val sliderMinY = (y + height * hue).toInt() - 4
            RenderUtil.drawRect(
                x.toFloat(),
                (sliderMinY - 1).toFloat(),
                (x + width).toFloat(),
                (sliderMinY + 1).toFloat(),
                -1
            )
        } else {
            for (colorIndex in 0..5) {
                val previousStep = Color.HSBtoRGB(step.toFloat() / 6, 1.0f, 1.0f)
                val nextStep = Color.HSBtoRGB((step + 1).toFloat() / 6, 1.0f, 1.0f)
                gradient(
                    x + step * (width / 6),
                    y,
                    x + (step + 1) * (width / 6),
                    y + height,
                    previousStep,
                    nextStep,
                    true
                )
                step++
            }
            val sliderMinX = (x + width * hue).toInt()
            RenderUtil.drawRect(
                (sliderMinX - 1).toFloat(),
                y.toFloat(),
                (sliderMinX + 1).toFloat(),
                (y + height).toFloat(),
                -1
            )
        }
    }

    fun drawAlphaSlider(x: Int, y: Int, width: Int, height: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        var left = true
        val checkerBoardSquareSize = height / 2
        var squareIndex = -checkerBoardSquareSize
        while (squareIndex < width) {
            if (!left) {
                RenderUtil.drawRect(
                    (x + squareIndex).toFloat(),
                    y.toFloat(),
                    (x + squareIndex + checkerBoardSquareSize).toFloat(),
                    (y + height).toFloat(),
                    -0x1
                )
                RenderUtil.drawRect(
                    (x + squareIndex).toFloat(),
                    (y + checkerBoardSquareSize).toFloat(),
                    (x + squareIndex + checkerBoardSquareSize).toFloat(),
                    (y + height).toFloat(),
                    -0x6f6f70
                )
                if (squareIndex < width - checkerBoardSquareSize) {
                    val minX = x + squareIndex + checkerBoardSquareSize
                    val maxX = Math.min(x + width, x + squareIndex + checkerBoardSquareSize * 2)
                    RenderUtil.drawRect(minX.toFloat(), y.toFloat(), maxX.toFloat(), (y + height).toFloat(), -0x6f6f70)
                    RenderUtil.drawRect(
                        minX.toFloat(),
                        (y + checkerBoardSquareSize).toFloat(),
                        maxX.toFloat(),
                        (y + height).toFloat(),
                        -0x1
                    )
                }
            }
            left = !left
            squareIndex += checkerBoardSquareSize
        }
        drawLeftGradientRect(x, y, x + width, y + height, Color(red, green, blue, 1F).getRGB(), 0)
        val sliderMinX = (x + width - width * alpha).toInt()
        RenderUtil.drawRect(
            (sliderMinX - 1).toFloat(),
            y.toFloat(),
            (sliderMinX + 1).toFloat(),
            (y + height).toFloat(),
            -1
        )
    }

    companion object {
        var tessellator = Tessellator.getInstance()
        var builder = tessellator.buffer
        fun mouseOver(minX: Int, minY: Int, maxX: Int, maxY: Int, mX: Int, mY: Int): Boolean {
            return mX >= minX && mY >= minY && mX <= maxX && mY <= maxY
        }

        fun getColor(color: Color, alpha: Float): Color {
            val red = color.red.toFloat() / 255
            val green = color.green.toFloat() / 255
            val blue = color.blue.toFloat() / 255
            return Color(red, green, blue, alpha)
        }

        fun drawPickerBase(
            pickerX: Int,
            pickerY: Int,
            pickerWidth: Int,
            pickerHeight: Int,
            red: Float,
            green: Float,
            blue: Float,
            alpha: Float
        ) {
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glShadeModel(GL11.GL_SMOOTH)
            GL11.glBegin(GL11.GL_POLYGON)
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
            GL11.glVertex2f(pickerX.toFloat(), pickerY.toFloat())
            GL11.glVertex2f(pickerX.toFloat(), (pickerY + pickerHeight).toFloat())
            GL11.glColor4f(red, green, blue, alpha)
            GL11.glVertex2f((pickerX + pickerWidth).toFloat(), (pickerY + pickerHeight).toFloat())
            GL11.glVertex2f((pickerX + pickerWidth).toFloat(), pickerY.toFloat())
            GL11.glEnd()
            GL11.glDisable(GL11.GL_ALPHA_TEST)
            GL11.glBegin(GL11.GL_POLYGON)
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f)
            GL11.glVertex2f(pickerX.toFloat(), pickerY.toFloat())
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f)
            GL11.glVertex2f(pickerX.toFloat(), (pickerY + pickerHeight).toFloat())
            GL11.glVertex2f((pickerX + pickerWidth).toFloat(), (pickerY + pickerHeight).toFloat())
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f)
            GL11.glVertex2f((pickerX + pickerWidth).toFloat(), pickerY.toFloat())
            GL11.glEnd()
            GL11.glEnable(GL11.GL_ALPHA_TEST)
            GL11.glShadeModel(GL11.GL_FLAT)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            GL11.glDisable(GL11.GL_BLEND)
        }

        fun readClipboard(): String? {
            return try {
                Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String
            } catch (exception: IOException) {
                null
            } catch (exception: UnsupportedFlavorException) {
                null
            }
        }

        fun drawGradientRect(leftpos: Double, top: Double, right: Double, bottom: Double, col1: Int, col2: Int) {
            val f = (col1 shr 24 and 0xFF) / 255.0f
            val f2 = (col1 shr 16 and 0xFF) / 255.0f
            val f3 = (col1 shr 8 and 0xFF) / 255.0f
            val f4 = (col1 and 0xFF) / 255.0f
            val f5 = (col2 shr 24 and 0xFF) / 255.0f
            val f6 = (col2 shr 16 and 0xFF) / 255.0f
            val f7 = (col2 shr 8 and 0xFF) / 255.0f
            val f8 = (col2 and 0xFF) / 255.0f
            GL11.glEnable(3042)
            GL11.glDisable(3553)
            GL11.glBlendFunc(770, 771)
            GL11.glEnable(2848)
            GL11.glShadeModel(7425)
            GL11.glPushMatrix()
            GL11.glBegin(7)
            GL11.glColor4f(f2, f3, f4, f)
            GL11.glVertex2d(leftpos, top)
            GL11.glVertex2d(leftpos, bottom)
            GL11.glColor4f(f6, f7, f8, f5)
            GL11.glVertex2d(right, bottom)
            GL11.glVertex2d(right, top)
            GL11.glEnd()
            GL11.glPopMatrix()
            GL11.glEnable(3553)
            GL11.glDisable(3042)
            GL11.glDisable(2848)
            GL11.glShadeModel(7424)
        }

        fun drawLeftGradientRect(left: Int, top: Int, right: Int, bottom: Int, startColor: Int, endColor: Int) {
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

        fun gradient(minX: Int, minY: Int, maxX: Int, maxY: Int, startColor: Int, endColor: Int, left: Boolean) {
            if (left) {
                val startA = (startColor and 0xFF) / 255.0f
                val startR = (startColor shr 16 and 0xFF) / 255.0f
                val startG = (startColor shr 8 and 0xFF) / 255.0f
                val startB = (startColor and 0xFF) / 255.0f
                val endA = (endColor and 0xFF) / 255.0f
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
            } else drawGradientRect(
                minX.toDouble(),
                minY.toDouble(),
                maxX.toDouble(),
                maxY.toDouble(),
                startColor,
                endColor
            )
        }

        fun gradientColor(color: Int, percentage: Int): Int {
            val r = (color and 0xFF0000 shr 16) * (100 + percentage) / 100
            val g = (color and 0xFF00 shr 8) * (100 + percentage) / 100
            val b = (color and 0xFF) * (100 + percentage) / 100
            return Color(r, g, b).hashCode()
        }

        fun drawGradientRect(
            left: Float,
            top: Float,
            right: Float,
            bottom: Float,
            startColor: Int,
            endColor: Int,
            hovered: Boolean
        ) {
            var startColor = startColor
            var endColor = endColor
            if (hovered) {
                startColor = gradientColor(startColor, -20)
                endColor = gradientColor(endColor, -20)
            }
            val c = (startColor shr 24 and 255).toFloat() / 255.0f
            val c1 = (startColor shr 16 and 255).toFloat() / 255.0f
            val c2 = (startColor shr 8 and 255).toFloat() / 255.0f
            val c3 = (startColor and 255).toFloat() / 255.0f
            val c4 = (endColor shr 24 and 255).toFloat() / 255.0f
            val c5 = (endColor shr 16 and 255).toFloat() / 255.0f
            val c6 = (endColor shr 8 and 255).toFloat() / 255.0f
            val c7 = (endColor and 255).toFloat() / 255.0f
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
            bufferbuilder.pos(right.toDouble(), top.toDouble(), 0.0).color(c1, c2, c3, c).endVertex()
            bufferbuilder.pos(left.toDouble(), top.toDouble(), 0.0).color(c1, c2, c3, c).endVertex()
            bufferbuilder.pos(left.toDouble(), bottom.toDouble(), 0.0).color(c5, c6, c7, c4).endVertex()
            bufferbuilder.pos(right.toDouble(), bottom.toDouble(), 0.0).color(c5, c6, c7, c4).endVertex()
            tessellator.draw()
            GlStateManager.shadeModel(7424)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        }
    }
}