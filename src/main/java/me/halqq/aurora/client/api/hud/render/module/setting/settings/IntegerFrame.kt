// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud.render.module.setting.settings

import com.mojang.realmsclient.gui.ChatFormatting
import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.hud.render.module.ModuleFrame
import me.halqq.aurora.client.api.hud.render.module.setting.SettingFrame
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.settings.SettingInteger
import me.halqq.aurora.client.impl.modules.client.ClickGui
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.gui.Gui
import java.awt.Color
import java.text.DecimalFormat

class IntegerFrame(module: ModuleFrame, x: Int, y: Int, private val setting: SettingInteger) :
    SettingFrame(module, x, y, module.width - 2, TextManager.INSTANCE.fontHeight + 6) {
    private var dragging = false
    private var sliderWidth = 0
    private fun updateSlider(mouseX: Int) {
        val diff = Math.min(width, Math.max(0, mouseX - x)).toDouble()
        val min = setting.minValue.toDouble()
        val max = setting.maxValue.toDouble()
        sliderWidth = (width * (setting.value - min) / (max - min)).toInt()
        if (dragging) {
            if (diff == 0.0) {
                setting.value = setting.minValue
            } else {
                val format = DecimalFormat("##")
                val newValue = format.format(diff / width * (max - min) + min)
                setting.value = newValue.toInt()
            }
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int) {
        updateSlider(mouseX)

        Gui.drawRect(x, y, x + 2, y + height, Color(0, 0, 0, 210).rgb)

        Gui.drawRect(x + 2 + sliderWidth, y, x + 2 + width, y + height - 1, getColor(mouseX, mouseY))

        Gui.drawRect(x + 2, y, x + 2 + sliderWidth, y + height - 1, getSliderColor(mouseX, mouseY))

        Gui.drawRect(x + 2, y + height - 1, x + 2 + width, y + height, Color(0, 0, 0, 210).rgb)

        TextManager.INSTANCE.drawStringWithShadow(
            setting.name, (x + 4).toFloat(), (y + 2).toFloat(), -1, ModuleManager.INSTANCE.getModule(
                CustomFont::class.java
            ).isEnabled
        )

        val `val` = "" + ChatFormatting.GRAY + setting.value
        TextManager.INSTANCE.drawStringWithShadow(
            `val`,
            (x + width - TextManager.INSTANCE.getStringWidth(`val`)).toFloat(),
            (y + 2).toFloat(),
            -1,
            ModuleManager.INSTANCE.getModule(
                CustomFont::class.java
            ).isEnabled
        )
    }

    private fun getColor(mouseX: Int, mouseY: Int): Int {
        val color = Color(50, 50, 50, 140)
        val hovered = mouseX > x + 2 + sliderWidth && mouseY > y && mouseX < x + 2 + width && mouseY < y + height - 1
        return if (hovered) color.brighter().brighter().rgb else color.rgb
    }

    private fun getSliderColor(mouseX: Int, mouseY: Int): Int {
        val color = ModuleManager.INSTANCE.getModule(ClickGui::class.java).color
        val hovered = mouseX > x + 2 && mouseY > y && mouseX < x + 2 + sliderWidth && mouseY < y + height - 1
        return if (hovered) color.darker().darker().rgb else color.rgb
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (mouseX > x + 2 && mouseY > y && mouseX < x + 2 + width && mouseY < y + height - 1 && button == 0) {
            dragging = true
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        if (dragging) dragging = false
    }
}