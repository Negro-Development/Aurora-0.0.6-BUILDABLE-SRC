// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.event.events

import net.minecraft.client.gui.ScaledResolution

class Render2DEvent(var partialTicks: Float, var scaledResolution: ScaledResolution) : AuroraEvent() {
    @JvmName("setPartialTicks1")
    fun setPartialTicks(partialTicks: Float) {
        this.partialTicks = partialTicks
    }

    @JvmName("setScaledResolution1")
    fun setScaledResolution(scaledResolution: ScaledResolution) {
        this.scaledResolution = scaledResolution
    }

    val screenWidth: Double
        get() = scaledResolution.scaledWidth_double
    val screenHeight: Double
        get() = scaledResolution.scaledHeight_double
}