// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud.render.module.setting.settings

import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.hud.render.module.ModuleFrame
import me.halqq.aurora.client.api.hud.render.module.setting.SettingFrame
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.settings.SettingString
import me.halqq.aurora.client.api.util.utils.TimerUtil
import me.halqq.aurora.client.impl.modules.client.ClickGui
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.gui.Gui
import net.minecraft.util.ChatAllowedCharacters
import org.lwjgl.input.Keyboard
import java.awt.Color
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

class StringFrame(module: ModuleFrame, x: Int, y: Int, private val setting: SettingString) :
    SettingFrame(module, x, y, module.width - 2, TextManager.INSTANCE.fontHeight + 6) {
    private val timer = TimerUtil()
    var listening = false
    private var idling = false
    private var currentString = CurrentString("")
    override fun drawScreen(mouseX: Int, mouseY: Int) {
        Gui.drawRect(x, y, x + 2, y + height, Color(0, 0, 0, 210).rgb)
        Gui.drawRect(x + 2, y, x + 2 + width, y + height - 1, color)
        Gui.drawRect(x + 2, y + height - 1, x + 2 + width, y + height, Color(0, 0, 0, 210).rgb)
        if (listening) {
            TextManager.INSTANCE.drawStringWithShadow(
                currentString.string + idleSign, x.toFloat() + 4, y.toFloat() + 4, -1, ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
        } else {
            TextManager.INSTANCE.drawStringWithShadow(
                setting.value, (x + 4).toFloat(), (y + 4).toFloat(), -1, ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
        }
    }

    private val color: Int
        private get() {
            val color = ModuleManager.INSTANCE.getModule(ClickGui::class.java).color
            return if (listening) color.rgb else Color(50, 50, 50, 140).rgb
        }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        listening = if (isMouseOnButton(mouseX, mouseY)) {
            !listening
        } else {
            false
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (listening) {
            if (keyCode == 1) {
                return
            }
            if (keyCode == 28) {
                enterString()
            } else if (keyCode == 14) {
                setString(removeLastChar(currentString.string))
            } else if (keyCode == 47 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
                try {
                    setString(currentString.string + Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                setString(currentString.string + typedChar)
            }
        }
    }

    private fun enterString() {
        if (!currentString.string.isEmpty()) {
            setting.value = currentString.string
        } else {
            setting.value = setting.value
        }
        setString("")
        listening = false
    }

    fun isMouseOnButton(x: Int, y: Int): Boolean {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15
    }

    fun setString(newString: String) {
        currentString = CurrentString(newString)
    }

    val idleSign: String
        get() {
            if (timer.passed(500L)) {
                idling = !idling
                timer.reset()
            }
            return if (idling) {
                "_"
            } else ""
        }

    class CurrentString(val string: String)
    companion object {
        fun removeLastChar(str: String?): String {
            var output = ""
            if (str != null && str.length > 0) {
                output = str.substring(0, str.length - 1)
            }
            return output
        }
    }
}