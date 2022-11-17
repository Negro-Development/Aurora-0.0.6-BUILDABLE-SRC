// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud.render

import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.hud.HudEditorScreen
import me.halqq.aurora.client.api.hud.Frameable
import me.halqq.aurora.client.api.hud.render.module.ModuleFrame
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.impl.modules.client.ClickGui
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.gui.Gui
import org.lwjgl.input.Mouse
import java.awt.Color
import java.io.IOException

class CategoryFrame(x: Int, y: Int) :
    Frameable, MinecraftInstance() {
    val width = 85
    private val titleHeight: Int
    private val modules: MutableList<ModuleFrame>
    private var x: Int
    private var y: Int
    private var height: Int = 0
    private var extended = true
    private var dragging = false
    private var dragX = 0
    private var dragY = 0

    init {
        modules = ArrayList()
        this.x = x
        this.y = y
        titleHeight = TextManager.INSTANCE.fontHeight + 4
        var buttonY = y + titleHeight
        for (m in ModuleManager.INSTANCE.getModulesInCategory(Category.HUD)) {
            val button = ModuleFrame(m, this.x, buttonY, this)
            modules.add(button)
            buttonY += button.height
            height += button.height
        }
        height = buttonY
    }

    override fun drawScreen(mouseX: Int, mouseY: Int) {
        val color = ModuleManager.INSTANCE.getModule(ClickGui::class.java).color.rgb
        if (dragging) {
            x = dragX + mouseX
            y = dragY + mouseY
        }
        Gui.drawRect(x - 2, y - 2, x + width + 2, y + titleHeight - 1, color)
        TextManager.INSTANCE.drawStringWithShadow(
            "HudEditor", (x + 1).toFloat(), (y + 2).toFloat(), -1, ModuleManager.INSTANCE.getModule(
                CustomFont::class.java
            ).isEnabled
        )
        if (!extended) {
            Gui.drawRect(x - 2, y + titleHeight - 1, x + width + 2, y + titleHeight + 3, Color(0, 0, 0, 210).rgb)
            return
        }
        height = 0
        for (b in modules) {
            if (b.x != x) b.x = x
            if (b.y != y + titleHeight + height) b.y = y + titleHeight + height
            b.drawScreen(mouseX, mouseY)
            height += b.height
        }
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + titleHeight) {
            if (button == 0) {
                dragX = x - mouseX
                dragY = y - mouseY
                dragging = true
            } else if (button == 1) {
                extended = !extended
            }
            return
        }
        if (extended) {
            for (b in modules) {
                b.mouseClicked(mouseX, mouseY, button)
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        if (dragging) dragging = false
        for (b in modules) {
            b.mouseReleased(mouseX, mouseY, state)
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        for (b in modules) {
            b.keyTyped(typedChar, keyCode)
        }
    }

    fun updatePosition() {
        val scrollWheel = Mouse.getDWheel()
        for (cF in HudEditorScreen.categories) {
            if (scrollWheel < 0) {
                cF.y = cF.y - ModuleManager.INSTANCE.getModule(ClickGui::class.java).scrollSpeed.value
            } else if (scrollWheel > 0) {
                cF.y = cF.y + ModuleManager.INSTANCE.getModule(ClickGui::class.java).scrollSpeed.value
            }
        }
    }
}