// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.other

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.ColorUtil
import java.awt.Color


class Colors : Module("Colors", Category.OTHER) {

    var red = create("Red", 127, 0, 255)
    var green = create("Green", 0, 0, 255)
    var blue = create("Blue", 127, 0, 255)
    var alpha = create("Alpha", 128, 0, 255)
    var rainbow = create("Rainbow", true)
    var saturation = create("Saturation", 60, 1, 100)
    var brightness = create("Brightness", 100, 1, 100)
    var speed = create("Speed", 40, 1, 100)

    override fun onUpdate() {
        if (rainbow.value) {
            val color = Color(
                ColorUtil.getRainbow(
                    speed.value * 100,
                    0,
                    saturation.value.toFloat() / 100.0f,
                    brightness.value.toFloat() / 100.0f
                )
            )
            red.value = color.red
            green.value = color.green
            blue.value = color.blue
        }
    }

    val color: Color
        get() = Color(red.value, green.value, blue.value, alpha.value)
}