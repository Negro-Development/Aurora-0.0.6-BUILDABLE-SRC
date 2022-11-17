// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.util

import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.util.*

object ColorUtils {
    val randomColour: Colour
        get() = Colour.fromHSB(floatArrayOf(Random().nextFloat(), 1f, 1f), 255)

    @JvmStatic
    fun twoColorEffect(color: Colour, color2: Colour, delay: Double): Colour {
        var delay = delay
        if (delay > 1.0) {
            val n2 = delay % 1.0
            delay = if (delay.toInt() % 2 == 0) n2 else 1.0 - n2
        }
        val n3 = 1.0 - delay
        return Colour(
            color.r * n3 + color2.r * delay,
            color.g * n3 + color2.g * delay, color.b * n3 + color2.b * delay, color.a * n3 + color2.a * delay
        )
    }

    @JvmStatic
    fun healthColor(hp: Float, maxHP: Float): Colour {
        val pct = (hp / maxHP * 255f).toInt()
        return Colour(Math.max(Math.min(255 - pct, 255), 0), Math.max(Math.min(pct, 255), 0), 0, 255)
    }

    @JvmStatic
    fun getAstolfoRainbow(offset: Int): Int {
        val speed = 2900f
        var hue = (System.currentTimeMillis() % speed.toInt()).toFloat() + (1000 - offset) * 9
        while (hue > speed) hue -= speed
        hue /= speed
        if (hue > 0.5) hue = 0.5f - (hue - 0.5f)
        hue += 0.5f
        return Color.HSBtoRGB(hue, 0.65f, 1f)
    }

    @JvmStatic
    fun rainbow(): Color {
        val offset = 999999999999L
        val hue = (System.nanoTime() + offset).toFloat() / 1.0E10f % 1.0f
        val color = Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)).toLong(16)
        val c = Color(color.toInt())
        return Color(
            c.red.toFloat() / 255.0f,
            c.green.toFloat() / 255.0f,
            c.blue.toFloat() / 255.0f,
            c.alpha.toFloat() / 255.0f
        )
    }

    @JvmStatic
    fun astolfoColors(yOffset: Int, yTotal: Int): Int {
        var hue: Float
        val speed = 2900.0f
        hue = (System.currentTimeMillis() % speed.toInt().toLong()).toFloat() + ((yTotal - yOffset) * 9).toFloat()
        while (hue > speed) {
            hue -= speed
        }
        if (speed.let { hue /= it; hue }.toDouble() > 0.5) {
            hue = 0.5f - (hue - 0.5f)
        }
        return Color.HSBtoRGB(0.5f.let { hue += it; hue }, 0.5f, 1.0f)
    }

    @JvmStatic
    @JvmOverloads
    fun astolfoColorsToColorObj(yOffset: Int, yTotal: Int, alpha: Int = 255): Color {
        val color = astolfoColors(yOffset, yTotal)
        return Color(getRed(color), getGreen(color), getBlue(color), alpha)
    }

    @JvmStatic
    fun rainbow(delay: Int, index: Long): Int {
        var rainbowState = Math.ceil((System.currentTimeMillis() + index + delay.toLong()).toDouble()) / 15.0
        return Color.getHSBColor(360f.also { rainbowState %= it } / 360f, 0.4f, 1.0f).rgb
    }

    @JvmStatic
    fun rainbowLT(delay: Int, index: Long): Int {
        var rainbowState = Math.ceil((System.currentTimeMillis() + index + delay.toLong()).toDouble()) / 3
        return Color.getHSBColor((248.0.also { rainbowState %= it } / 248.0).toFloat(), 0.5f, 0.6f).rgb
    }

    @JvmStatic
    fun glColor(hex: Int, alpha: Int) {
        val red = (hex shr 16 and 0xFF) / 255f
        val green = (hex shr 8 and 0xFF) / 255f
        val blue = (hex and 0xFF) / 255f
        GlStateManager.color(red, green, blue, alpha / 255f)
    }

    @JvmStatic
    fun color(r: Int, g: Int, b: Int, a: Int): Int {
        return Color(r, g, b, a).rgb
    }

    @JvmStatic
    fun color(r: Float, g: Float, b: Float, a: Float): Int {
        return Color(r, g, b, a).rgb
    }

    @JvmStatic
    fun getColor(a: Int, r: Int, g: Int, b: Int): Int {
        return a shl 24 or (r shl 16) or (g shl 8) or b
    }

    @JvmStatic
    fun getColor(r: Int, g: Int, b: Int): Int {
        return 255 shl 24 or (r shl 16) or (g shl 8) or b
    }

    @JvmStatic
    fun getColor(color: Color): Int {
        return color.red or (color.green shl 8) or (color.blue shl 16) or (color.alpha shl 24)
    }

    @JvmStatic
    fun rainbow(delay: Int, s: Float, b: Float): Color {
        return Color.getHSBColor((System.currentTimeMillis() + delay) % 11520L / 11520.0f, s, b)
    }

    @JvmStatic
    fun getRed(color: Int): Int {
        return Color(color).red
    }

    @JvmStatic
    fun getGreen(color: Int): Int {
        return Color(color).green
    }

    @JvmStatic
    fun getBlue(color: Int): Int {
        return Color(color).blue
    }

    @JvmStatic
    fun getAlpha(color: Int): Int {
        return Color(color).alpha
    }

    @JvmStatic
    fun rainbowRGB(delay: Int, s: Float, b: Float): Color {
        return Color(
            getRed(Color.HSBtoRGB((System.currentTimeMillis() + delay) % 11520L / 11520.0f, s, b)), getGreen(
                Color.HSBtoRGB((System.currentTimeMillis() + delay) % 11520L / 11520.0f, s, b)
            ), getBlue(Color.HSBtoRGB((System.currentTimeMillis() + delay) % 11520L / 11520.0f, s, b))
        )
    }

    @JvmStatic
    fun getColor(brightness: Int): Int {
        return getColor(brightness, brightness, brightness, 255)
    }

    @JvmStatic
    fun getColor(brightness: Int, alpha: Int): Int {
        return getColor(brightness, brightness, brightness, alpha)
    }

    @JvmStatic
    fun injectAlpha(color: Color, alpha: Int): Color {
        return Color(color.red, color.green, color.blue, alpha)
    }

    @JvmStatic
    fun injectAlpha(color: Int, alpha: Int): Color {
        return Color(getRed(color), getGreen(color), getBlue(color), alpha)
    }

    @JvmStatic
    fun glColor(color: Color) {
        GL11.glColor4f(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
    }
}