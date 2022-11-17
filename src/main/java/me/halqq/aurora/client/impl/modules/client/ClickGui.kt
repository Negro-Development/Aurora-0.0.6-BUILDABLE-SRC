// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.client

import me.halqq.aurora.client.api.gui.ClientGuiScreen
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.ColorUtil
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Keyboard
import java.awt.Color

class ClickGui : Module("ClickGui", Category.OTHER) {

    var scrollSpeed = create("ScrollSpeed", 12, 1, 20)
    var soundClick = create("SoundClick", true)
    var red = create("Red", 0, 0, 225)
    var green = create("Green", 0, 0, 225)
    var blue = create("Blue", 255, 0, 225)
    var alpha = create("Alpha", 170, 0, 225)
    var rainbow = create("Rainbow", false)
    var speed = create("Speed", 40, 1, 100)
    var blur = create("Blur", false)
    var particle = create("Particle", true)
    var particleamount = create("ParticleAmount", 80, 10, 100)

    init {
        if (key == 0) {
            key = Keyboard.KEY_RCONTROL
        }
    }

    override fun onUpdate(){
        if (rainbow.value) {
            val color = Color(
                ColorUtil.getRainbow(
                    speed.value * 100,
                    0,
                    0.6f,
                    1f
                )
            )
            red.value = color.red
            green.value = color.green
            blue.value = color.blue
        }
    }

    override fun onEnable() {
        if (fullNullCheck()) return
        if (blur.value) {
            mc.entityRenderer.loadShader(ResourceLocation("shaders/post/blur.json"))
        }
        mc.displayGuiScreen(ClientGuiScreen.INSTANCE)
    }

    override fun onDisable() {
        if (fullNullCheck()) return
        try {
            if (mc.entityRenderer.isShaderActive) {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup()
            }
        } catch (ignored: Exception) {
        }
        mc.displayGuiScreen(null)
    }

    val color: Color
        get() = Color(red.value, green.value, blue.value, alpha.value)

    companion object{
        lateinit var INSTANCE: ClickGui
    }
}