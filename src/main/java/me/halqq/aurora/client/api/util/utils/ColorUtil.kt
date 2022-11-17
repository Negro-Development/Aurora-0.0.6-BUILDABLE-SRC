// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.util.utils

import java.awt.Color

object ColorUtil {
    fun getRainbow(speed: Int, offset: Int, s: Float, b: Float): Int {
        val hue = ((System.currentTimeMillis() + offset.toLong()) % speed.toLong()).toFloat()
        return Color.getHSBColor(hue / speed.toFloat(), s, b).rgb
    }
}