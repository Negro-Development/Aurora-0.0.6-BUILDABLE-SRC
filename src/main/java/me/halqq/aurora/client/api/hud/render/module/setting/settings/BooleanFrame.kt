// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud.render.module.setting.settings

import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.hud.render.module.ModuleFrame
import me.halqq.aurora.client.api.hud.render.module.setting.SettingFrame
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.settings.SettingBoolean
import me.halqq.aurora.client.impl.modules.client.ClickGui
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.gui.Gui
import java.awt.Color

class BooleanFrame(module: ModuleFrame, x: Int, y: Int, private val setting: SettingBoolean) :
    SettingFrame(module, x, y, module.width - 2, module.height) {
    override fun drawScreen(mouseX: Int, mouseY: Int) {
        Gui.drawRect(x, y, x + 2, y + height, Color(0, 0, 0, 210).rgb)
        Gui.drawRect(x + 2, y, x + 2 + width, y + height - 1, getColor(mouseX, mouseY))
        Gui.drawRect(x + 2, y + height - 1, x + 2 + width, y + height, Color(0, 0, 0, 210).rgb)
        TextManager.INSTANCE.drawStringWithShadow(
            setting.name, (x + 4).toFloat(), (y + 2).toFloat(), -1, ModuleManager.INSTANCE.getModule(
                CustomFont::class.java
            ).isEnabled
        )
    }

    private fun getColor(mouseX: Int, mouseY: Int): Int {
        val color =
            if (setting.value) ModuleManager.INSTANCE.getModule(ClickGui::class.java).color else Color(50, 50, 50, 140)
        val hovered = mouseX > x + 2 && mouseY > y && mouseX < x + 2 + width && mouseY < y + height - 1
        return if (hovered) if (setting.value) color.darker().darker().rgb else color.brighter()
            .brighter().rgb else color.rgb
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (mouseX > x + 2 && mouseY > y && mouseX < x + 2 + width && mouseY < y + height - 1 && button == 0) {
            setting.value = !setting.value
        }
    }
}