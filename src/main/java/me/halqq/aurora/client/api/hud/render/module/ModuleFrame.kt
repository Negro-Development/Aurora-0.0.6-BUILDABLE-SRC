// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud.render.module

import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.gui.util.Render2DUtil
import me.halqq.aurora.client.api.hud.Frameable
import me.halqq.aurora.client.api.hud.render.CategoryFrame
import me.halqq.aurora.client.api.hud.render.module.setting.SettingFrame
import me.halqq.aurora.client.api.hud.render.module.setting.settings.*
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.SettingManager
import me.halqq.aurora.client.api.setting.settings.*
import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import me.halqq.aurora.client.api.util.utils.MessageUtil
import me.halqq.aurora.client.impl.modules.client.ClickGui
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.Gui
import net.minecraft.init.SoundEvents
import net.minecraft.util.ResourceLocation
import java.awt.Color
import java.io.IOException

class ModuleFrame(val module: Module, var x: Int, var y: Int, val category: CategoryFrame) :
    Frameable, MinecraftInstance() {
    val width: Int

    private val buttonLogo = ResourceLocation("aurora/button.png")

    private val settings: MutableList<SettingFrame>
    private val permaHeight: Int
    private var firstModule: Int
    var height: Int
        private set
    var isExtended: Boolean
        private set

    init {
        width = category.width
        height = TextManager.INSTANCE.fontHeight + 6
        permaHeight = height
        firstModule = 3
        isExtended = false
        settings = ArrayList()
        var subButtonY = 0
        for (s in SettingManager.INSTANCE.getSettingsInModule(module)) {
            var button: SettingFrame? = null
            if (s is SettingBoolean) {
                button = BooleanFrame(this, x, y + height + subButtonY, s)
            } else if (s is SettingColor) {
                button = ColorFrame(this, x, y + height + subButtonY, s)
            } else if (s is SettingDouble) {
                button = DoubleFrame(this, x, y + height + subButtonY, s)
            } else if (s is SettingInteger) {
                button = IntegerFrame(this, x, y + height + subButtonY, s)
            } else if (s is SettingMode) {
                button = ModeFrame(this, x, y + height + subButtonY, s)
            } else if (s is SettingString) {
                button = StringFrame(this, x, y + height + subButtonY, s)
            }
            if (button != null) {
                settings.add(button)
                subButtonY += button.height
            }
        }
        settings.add(BindFrame(this, x, y + height + subButtonY))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int) {
        Gui.drawRect(x - 2, y, x + width + 2, y + TextManager.INSTANCE.fontHeight + 6, Color(0, 0, 0, 210).rgb)
        TextManager.INSTANCE.drawString(if(isExtended) "                        -" else "                       +", (x + 2).toFloat(), (y + 2).toFloat(), -1, false, true);
        Render2DUtil.drawOutlineRect(x - 2.0, y + 0.0, x + width + 1.2, y + TextManager.INSTANCE.fontHeight + 6.0, 2.0, getColor(mouseX, mouseY))
        TextManager.INSTANCE.drawStringWithShadow(module.name, (x + 2).toFloat(), (y + 2).toFloat(), -1, ModuleManager.INSTANCE.getModule(CustomFont::class.java).isEnabled)

        height = TextManager.INSTANCE.fontHeight + 6
        firstModule = 0
        if (isExtended) {
            for (b in settings) {
                if (b.x != x) b.x = x
                if (b.y != y + height) b.y = y + height
                b.drawScreen(mouseX, mouseY)
                height += b.height

            }
        }

    }

    private fun getColor(mouseX: Int, mouseY: Int): Int {
        val color = if (module.isEnabled) ModuleManager.INSTANCE.getModule(ClickGui::class.java).color else Color(
            50,
            50,
            50,
            140
        )
        val hovered = mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + TextManager.INSTANCE.fontHeight + 4
        return if (hovered) if (module.isEnabled)
            color.darker().darker().rgb
            else color.brighter().brighter().rgb
          else color.rgb
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + TextManager.INSTANCE.fontHeight + 4) {
            if (button == 0) {
                module.toggle()
                MessageUtil.toggleMessage(module)
                if (ModuleManager.INSTANCE.getModule(ClickGui::class.java).soundClick.value) {
                    Minecraftable.mc.getSoundHandler()
                        .playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f))
                }
            } else if (button == 1) {
                isExtended = !isExtended
            }
            return
        }
        if (isExtended) {
            for (sF in settings) {
                sF.mouseClicked(mouseX, mouseY, button)
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        if (isExtended) {
            for (sF in settings) {
                sF.mouseReleased(mouseX, mouseY, state)
            }
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (isExtended) {
            for (sF in settings) {
                sF.keyTyped(typedChar, keyCode)
            }
        }
    }
}