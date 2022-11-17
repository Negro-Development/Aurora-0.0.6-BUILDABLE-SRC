// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud.render.module.setting.settings

import com.mojang.realmsclient.gui.ChatFormatting
import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.hud.render.module.ModuleFrame
import me.halqq.aurora.client.api.hud.render.module.setting.SettingFrame
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.gui.Gui
import org.lwjgl.input.Keyboard
import java.awt.Color

class BindFrame(module: ModuleFrame, x: Int, y: Int) :
    SettingFrame(module, x, y, module.width - 2, TextManager.INSTANCE.fontHeight + 6) {
    private var listening = false
    override fun drawScreen(mouseX: Int, mouseY: Int) {
        Gui.drawRect(x, y, x + 2, y + height, Color(10, 10, 10, 200).rgb)
        Gui.drawRect(x + 2, y, x + 2 + width, y + height - 1, getColor(mouseX, mouseY))
        Gui.drawRect(x + 2, y + height - 1, x + 2 + width, y + height, Color(0, 0, 0, 210).rgb)
        TextManager.INSTANCE.drawStringWithShadow(
            "Bind", (x + 4).toFloat(), (y + 2).toFloat(), -1, ModuleManager.INSTANCE.getModule(
                CustomFont::class.java
            ).isEnabled
        )
        val `val` = ChatFormatting.GRAY.toString() + if (listening) "Listening" else module.module.keyName
        TextManager.INSTANCE.drawStringWithShadow(
            `val`,
            (x + width - TextManager.INSTANCE.getStringWidth(`val`) - 4).toFloat(),
            (y + 2).toFloat(),
            -1,
            ModuleManager.INSTANCE.getModule(
                CustomFont::class.java
            ).isEnabled
        )
    }

    private fun getColor(mouseX: Int, mouseY: Int): Int {
        val color = Color(50, 50, 50, 140)
        val hovered = mouseX > x + 2 && mouseY > y && mouseX < x + 2 + width && mouseY < y + height - 1
        return if (hovered) color.brighter().brighter().rgb else color.rgb
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (mouseX > x + 2 && mouseY > y && mouseX < x + 2 + width && mouseY < y + height - 1 && button == 0) {
            listening = !listening
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (listening) {
            if (keyCode != 0 && keyCode != Keyboard.KEY_ESCAPE) {
                if (keyCode == Keyboard.KEY_DELETE) module.module.key = -1 else module.module.key =
                    keyCode
            }
            listening = false
        }
    }
}