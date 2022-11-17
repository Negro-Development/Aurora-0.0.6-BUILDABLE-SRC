// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.hud

import java.io.IOException

interface Frameable {
    fun drawScreen(mouseX: Int, mouseY: Int)

    @Throws(IOException::class)
    fun mouseClicked(mouseX: Int, mouseY: Int, button: Int)
    fun mouseReleased(mouseX: Int, mouseY: Int, state: Int)
    fun keyTyped(typedChar: Char, keyCode: Int)
}