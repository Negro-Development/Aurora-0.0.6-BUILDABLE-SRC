// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.event.events

import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.Tessellator
import net.minecraft.util.math.Vec3d

class RenderEvent(private val tessellator: Tessellator, private val renderPos: Vec3d, val partialTicks: Float) {

    val buffer: BufferBuilder
        get() = tessellator.buffer

    fun setTranslation(translation: Vec3d) {
        buffer.setTranslation(-translation.x, -translation.y, -translation.z)
    }

    fun resetTranslation() {
        setTranslation(renderPos)
    }
}