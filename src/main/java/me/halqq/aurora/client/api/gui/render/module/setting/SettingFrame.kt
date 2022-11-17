// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.render.module.setting

import me.halqq.aurora.client.api.gui.Frameable
import me.halqq.aurora.client.api.gui.render.module.ModuleFrame
import java.io.IOException

open class SettingFrame(val module: ModuleFrame, var x: Int, var y: Int, var width: Int, var height: Int) :
    Frameable {

    override fun drawScreen(mouseX: Int, mouseY: Int) {}

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {}
    override fun keyTyped(typedChar: Char, keyCode: Int) {}
}