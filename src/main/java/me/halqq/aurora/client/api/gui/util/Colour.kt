// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.util

import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.math.Vec3d
import java.awt.Color

class Colour {

    @JvmField    var r: Int
    @JvmField    var g: Int
    @JvmField    var b: Int
    @JvmField    var a = 0
    @JvmField   var r1: Float
    @JvmField   var g1: Float
    @JvmField  var b1: Float
    @JvmField   var a1 = 0f
    @JvmField  var isInt = true

    constructor(hsb: FloatArray) : this(Color.getHSBColor(hsb[0], hsb[1], hsb[2])) {}
    constructor(r: Int, g: Int, b: Int, a: Int) {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
        r1 = r / 255f
        g1 = g / 255f
        b1 = b / 255f
        a1 = a / 255f
        fixColorRange()
    }

    constructor(r: Double, g: Double, b: Double, a: Double) : this(r.toInt(), g.toInt(), b.toInt(), a.toInt()) {}
    constructor(r: Int, g: Int, b: Int) {
        this.r = r
        this.g = g
        this.b = b
        a = 255
        r1 = r / 255f
        g1 = g / 255f
        b1 = b / 255f
        a1 = 1f
        fixColorRange()
    }

    constructor(color: Colour, a: Int) {
        r = color.r
        g = color.g
        b = color.b
        this.a = a
        r1 = r / 255f
        g1 = g / 255f
        b1 = b / 255f
        a1 = a / 255f
        fixColorRange()
    }

    constructor(color: Color, a: Float) : this(color) {
        this.a = a.toInt() * 255
        a1 = a
        fixColorRange()
    }

    constructor(r: Float, g: Float, b: Float) {
        r1 = r
        g1 = g
        b1 = b
        this.r = r1.toInt() * 255
        this.g = g1.toInt() * 255
        this.b = b1.toInt() * 255
        isInt = false
        fixColorRange()
    }

    constructor(r: Float, g: Float, b: Float, a: Float) {
        r1 = r
        g1 = g
        b1 = b
        a1 = a
        this.r = r1.toInt() * 255
        this.g = g1.toInt() * 255
        this.b = b1.toInt() * 255
        this.a = a1.toInt() * 255
        isInt = false
        fixColorRange()
    }

    constructor(color: Int) {
        r = ColorUtils.getRed(color)
        g = ColorUtils.getGreen(color)
        b = ColorUtils.getBlue(color)
        a = ColorUtils.getAlpha(color)
        r1 = r / 255f
        g1 = g / 255f
        b1 = b / 255f
        a1 = a / 255f
        fixColorRange()
    }

    fun toVec3d(): Vec3d {
        return Vec3d(r1.toDouble(), g1.toDouble(), b1.toDouble())
    }

    fun nextColor() {
        val hsb = RGBtoHSB()
        var rainbowState = Math.ceil((System.currentTimeMillis() + 200) / 20.0)
        rainbowState %= 360.0
        hsb[0] = (rainbowState / 360.0).toFloat()
        setColour(fromHSB(hsb, a))
    }

    fun syns(color: Colour) {
        r = color.r
        g = color.g
        b = color.b
        a = color.a
        r1 = r / 255f
        g1 = g / 255f
        b1 = b / 255f
        a1 = a / 255f
        fixColorRange()
    }

    fun setBrightness(brightness: Float): Colour {
        val hsb = RGBtoHSB()
        setColour(Colour(floatArrayOf(hsb[0], hsb[1], brightness)))
        return this
    }

    fun setAlpha(alpha: Float): Colour {
        a = (alpha * 255f).toInt()
        a1 = alpha
        fixColorRange()
        return this
    }

    fun setHue(hue: Float): Colour {
        val hsb = RGBtoHSB()
        setColour(Colour(floatArrayOf(hue, hsb[1], hsb[2])))
        return this
    }

    fun setSaturation(saturation: Float): Colour {
        val hsb = RGBtoHSB()
        setColour(Colour(floatArrayOf(hsb[0], saturation, hsb[2])))
        return this
    }

    private fun setColour(color: Colour) {
        r = color.r
        g = color.g
        b = color.b
        a = color.a
        r1 = r / 255f
        g1 = g / 255f
        b1 = b / 255f
        a1 = a / 255f
        fixColorRange()
    }

    val hue: Float
        get() = RGBtoHSB()[0]

    fun RGBtoHSB(): FloatArray {
        return Color.RGBtoHSB(r, g, b, null)
    }

    private fun fixColorRange() {
        if (r > 255) r = 255 else if (r < 0) r = 0
        if (g > 255) g = 255 else if (g < 0) g = 0
        if (b > 255) b = 255 else if (b < 0) b = 0
        if (a > 255) a = 255 else if (a < 0) a = 0
        if (r1 > 1) r1 = 1f else if (r1 < 0) r1 = 0f
        if (g1 > 1) g1 = 1f else if (g1 < 0) g1 = 0f
        if (b1 > 1) b1 = 1f else if (b1 < 0) b1 = 0f
        if (a1 > 1) a1 = 1f else if (a1 < 0) a1 = 0f
    }

    @JvmOverloads
    constructor(color: Color = Color.RED) : this(color.rgb) {
    }

    val color: Color
        get() = Color(r, g, b, a)
    val rGB: Int
        get() = Color(r, g, b, a).rgb

    fun getR(): Float {
        return if (isInt) r.toFloat() / 255 else r1
    }

    fun getG(): Float {
        return if (isInt) g.toFloat() / 255 else g1
    }

    fun getB(): Float {
        return if (isInt) b.toFloat() / 255 else b1
    }

    fun getA(): Float {
        return if (isInt) a.toFloat() / 255 else a1
    }

    val alpha: Int
        get() = if (isInt) a else a1.toInt() * 255

    fun glColor() {
        GlStateManager.color(r1, g1, b1, a1)
    }

    companion object {
        var COLOR_RAINBOW = 0
        var COLOR_ASTOLFO = 0
        var COLOR_PRIMARY = 0

        init {
            COLOR_RAINBOW = 0
            COLOR_ASTOLFO = 1
            COLOR_PRIMARY = 2
        }

        fun fromHSB(hsb: FloatArray, alpha: Int): Colour {
            return Colour(
                ColorUtils.injectAlpha(
                    Color.getHSBColor(
                        hsb[0], hsb[1], hsb[2]
                    ), alpha
                )
            )
        }
    }
}