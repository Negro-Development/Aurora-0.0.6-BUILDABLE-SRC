// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.font

import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import me.halqq.aurora.client.api.util.utils.TimerUtil
import me.halqq.aurora.client.impl.modules.render.CrystalChams
import net.minecraft.util.math.MathHelper
import java.awt.Color
import java.awt.Font

class TextManager : MinecraftInstance() {
    private val idleTimer = TimerUtil()
    var scaledWidth = 0
    var scaledHeight = 0
    var scaleFactor = 0
    private var customFont = CustomFont(Font("Lato", Font.ITALIC, 18), true, true)
    private var customFont1 = CustomFont(Font("Whitney", 0, 10), true, true)

    private var idling = false
    fun drawRainbowString(
        text: String,
        x: Float,
        y: Float,
        startColor: Int,
        factor: Float,
        shadow: Boolean,
        custom: Boolean
    ) {
        var currentColor = Color(startColor)
        val hueIncrement = 1.0f / factor
        val rainbowStrings = text.split("\u00a7.".toRegex()).toTypedArray()
        var currentHue = Color.RGBtoHSB(currentColor.red, currentColor.green, currentColor.blue, null)[0]
        val saturation = Color.RGBtoHSB(currentColor.red, currentColor.green, currentColor.blue, null)[1]
        val brightness = Color.RGBtoHSB(currentColor.red, currentColor.green, currentColor.blue, null)[2]
        var currentWidth = 0
        var shouldRainbow = true
        var shouldContinue = false
        for (i in 0 until text.length) {
            val currentChar = text[i]
            val nextChar = text[MathHelper.clamp(i + 1, 0, text.length - 1)]
            val equals = currentChar.toString() + nextChar == "\u00a7r"
            if (equals) {
                shouldRainbow = false
            } else if (currentChar.toString() + nextChar == "\u00a7+") {
                shouldRainbow = true
            }
            if (shouldContinue) {
                shouldContinue = false
                continue
            }
            if (equals) {
                val escapeString = text.substring(i)
                drawString(escapeString, x + currentWidth.toFloat(), y, Color.WHITE.rgb, shadow, custom)
                break
            }
            drawString(
                if (currentChar.toString() == "\u00a7") "" else currentChar.toString(),
                x + currentWidth.toFloat(),
                y,
                if (shouldRainbow) currentColor.rgb else Color.WHITE.rgb,
                shadow,
                custom
            )
            if (currentChar.toString() == "\u00a7") {
                shouldContinue = true
            }
            currentWidth += getStringWidth(currentChar.toString())
            if (currentChar.toString() == " ") continue
            currentColor = Color(Color.HSBtoRGB(currentHue, saturation, brightness))
            currentHue += hueIncrement
        }
    }

    fun drawCenteredStringWithShadow(text: String?, x: Float, y: Float, color: Int, custom: Boolean) {
        drawStringWithShadow(text, x, y - fontHeight / 2, color, custom)
    }

    fun drawStringWithShadow(text: String?, x: Float, y: Float, color: Int, custom: Boolean) {
        drawString(text, x, y, color, true, custom)
    }

    fun drawString(text: String?, x: Float, y: Float, color: Int, shadow: Boolean, custom: Boolean) {
        if (custom) {
            if (shadow) {
                customFont.drawStringWithShadow(text, x.toDouble(), y.toDouble(), color)
                return
            }
            customFont.drawString(text, x, y, color)
            return
        }
        Minecraftable.mc.fontRenderer.drawString(text, x, y, color, shadow)
    }

    fun drawStringsize(text: String?, x: Float, y: Float, color: Int, shadow: Boolean, custom: Boolean) {
        if (custom) {
            if (shadow) {
                customFont1.drawStringWithShadow(text, x.toDouble(), y.toDouble(), color)
                return
            }
            customFont1.drawString(text, x, y, color)
            return
        }
        Minecraftable.mc.fontRenderer.drawString(text, x, y, color, shadow)
    }

    fun drawStringWithShadow(text: String?, x: Float, y: Float, color: Int) {
        drawString(text, x, y, color, true, true)
    }

    fun getStringWidth(text: String?): Int {
        return Minecraftable.mc.fontRenderer.getStringWidth(text)
    }

    val fontHeight: Int
        get() = Minecraftable.mc.fontRenderer.FONT_HEIGHT

    fun setFontRenderer(font: Font?, antiAlias: Boolean, fractionalMetrics: Boolean) {
        customFont = CustomFont(font, antiAlias, fractionalMetrics)
    }

    val currentFont: Font
        get() = customFont.getFont()

    fun updateResolution() {
        scaledWidth = Minecraftable.mc.displayWidth
        scaledHeight = Minecraftable.mc.displayHeight
        scaleFactor = 1
        val flag = Minecraftable.mc.isUnicode
        var i = Minecraftable.mc.gameSettings.guiScale
        if (i == 0) {
            i = 1000
        }
        while (scaleFactor < i && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor
        }
        if (flag && scaleFactor % 2 != 0 && scaleFactor != 1) {
            --scaleFactor
        }
        val scaledWidthD = scaledWidth.toDouble() / scaleFactor.toDouble()
        val scaledHeightD = scaledHeight.toDouble() / scaleFactor.toDouble()
        scaledWidth = MathHelper.ceil(scaledWidthD)
        scaledHeight = MathHelper.ceil(scaledHeightD)
    }

    val idleSign: String
        get() {
            if (idleTimer.passedMs(500L)) {
                idling = !idling
                idleTimer.reset()
            }
            return if (idling) {
                "_"
            } else ""
        }

    companion object {
        lateinit var INSTANCE: TextManager
    }
}