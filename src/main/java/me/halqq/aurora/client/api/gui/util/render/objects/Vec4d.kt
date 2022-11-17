// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.util.render.objects

import org.lwjgl.opengl.GL11
import java.util.*

class Vec4d(first: DoubleArray, second: DoubleArray, third: DoubleArray, four: DoubleArray) {
    var x1: Double
    var x2: Double
    var y1: Double
    var y2: Double
    var x3: Double
    var x4: Double
    var y3: Double
    var y4: Double

    init {
        x1 = first[0]
        y1 = first[1]
        x2 = second[0]
        y2 = second[1]
        x3 = third[0]
        y3 = third[1]
        x4 = four[0]
        y4 = four[1]
    }

    fun toArray(): ArrayList<DoubleArray> {
        return ArrayList(
            Arrays.asList(
                doubleArrayOf(x1, y1),
                doubleArrayOf(x2, y2),
                doubleArrayOf(x3, y3),
                doubleArrayOf(x4, y4)
            )
        )
    }

    val minX: Float
        get() = Math.min(x1, Math.min(x2, Math.min(x3, x4))).toFloat()
    val minY: Float
        get() = Math.min(y1, Math.min(y2, Math.min(y3, y4))).toFloat()
    val maxX: Float
        get() = Math.max(x1, Math.max(x2, Math.max(x3, x4))).toFloat()
    val maxY: Float
        get() = Math.max(y1, Math.max(y2, Math.max(y3, y4))).toFloat()

    fun setupVectors() {
        GL11.glVertex2d(x1, y1)
        GL11.glVertex2d(x2, y2)
        GL11.glVertex2d(x3, y3)
        GL11.glVertex2d(x4, y4)
    }
}