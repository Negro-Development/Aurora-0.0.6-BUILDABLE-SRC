// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud

import me.halqq.aurora.client.api.hud.render.CategoryFrame
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.impl.modules.client.ClickGui
import net.minecraft.client.gui.GuiScreen
import java.io.IOException


class HudEditorScreen : GuiScreen() {

    init {
        categories = ArrayList()

        var x = 6
            val panel = CategoryFrame(x, 5)
            (categories as ArrayList<CategoryFrame>).add(panel)
            x += panel.width + 6
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawDefaultBackground()

        for (c in categories) {
            c.updatePosition()
            c.drawScreen(mouseX, mouseY)
        }
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    @Throws(IOException::class)
    override fun keyTyped(typedChar: Char, keyCode: Int) {

        for (cF in categories) {
            cF.keyTyped(typedChar, keyCode)
        }
        super.keyTyped(typedChar, keyCode)
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {

        for (cF in categories) {
            cF.mouseClicked(mouseX, mouseY, mouseButton)
        }
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        for (cF in categories) {
            cF.mouseReleased(mouseX, mouseY, state)
        }
        super.mouseReleased(mouseX, mouseY, state)
    }

    override fun onGuiClosed() {
        ModuleManager.INSTANCE.getModule(ClickGui::class.java).setDisabled()
        super.onGuiClosed()
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    companion object {
        var INSTANCE: HudEditorScreen? = null
        lateinit var categories: List<CategoryFrame>
    }
}