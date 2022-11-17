// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class SkyColor : Module("SkyColor", Category.RENDER) {

    var red = create("Red", 255, 0, 225)
    var green = create("Green", 0, 0, 225)
    var blue = create("Blue", 255, 0, 225)
    var alpha = create("Alpha", 170, 0, 225)

    @SubscribeEvent
    fun fogColors(event: FogColors) {
        event.red = red.value.toFloat() / 255f
        event.blue = green.value.toFloat() / 255f
        event.green = blue.value.toFloat() / 255f
    }
}