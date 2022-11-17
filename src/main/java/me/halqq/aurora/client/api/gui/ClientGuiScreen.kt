// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui

import me.halqq.aurora.client.api.gui.render.CategoryFrame
import me.halqq.aurora.client.api.gui.util.render.objects.ParticleGenerator
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.impl.modules.client.ClickGui
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Mouse
import java.io.IOException


class ClientGuiScreen : GuiScreen() {
    init {
        categories = ArrayList()


        var x = 6
        for (category in Category.values().filter { it != Category.HUD }) {
            val panel = CategoryFrame(category, x, 5)
            (categories as ArrayList<CategoryFrame>).add(panel)
            x += panel.width + 6
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawDefaultBackground()

        if(ModuleManager.INSTANCE.getModule(ClickGui::class.java).particle.value) {
            ParticleGenerator.draw(Mouse.getX() * width / mc.displayWidth, height - Mouse.getY() * height / mc.displayHeight - 1)
        }

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
        var INSTANCE: ClientGuiScreen? = null
        lateinit var categories: List<CategoryFrame>
    }
}