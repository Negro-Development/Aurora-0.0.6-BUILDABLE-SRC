// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render.playerchams

import org.lwjgl.opengl.GL11
import java.awt.Color

object ChamsUtil {

    fun e() {
        GL11.glEnable(2929)
        GL11.glDepthMask(true)
        GL11.glDisable(3008)
        GL11.glEnable(3553)
        GL11.glEnable(2896)
        GL11.glDisable(3042)
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        GL11.glPopAttrib()
    }

    fun z() {
        GL11.glPushMatrix()
        GL11.glPushAttrib(1048575)
    }

    fun x(scale: Double, texture: Boolean) {
        GL11.glScaled(scale, scale, scale)
        GL11.glEnable(3042)
        GL11.glDepthMask(false)
        if (!texture) {
            GL11.glDisable(3553)
        }
        GL11.glEnable(3553)
        GL11.glDisable(2929)
        GL11.glTexCoord3d(1.0, 1.0, 1.0)
        GL11.glEnable(3553)
        GL11.glBlendFunc(768, 771)
        GL11.glBlendFunc(770, 771)
        GL11.glEnable(2848)
        GL11.glHint(3154, 4354)
    }

    fun c() {
        GL11.glEnable(2929)
        GL11.glDisable(3553)
        GL11.glDepthMask(true)
        GL11.glDisable(3042)
        GL11.glPopAttrib()
        GL11.glPopMatrix()
    }

   @JvmStatic fun co(c: Color) {
        GL11.glColor4d(
            (c.red.toFloat() / 255.0f).toDouble(),
            (c.green.toFloat() / 255.0f).toDouble(),
            (c.blue.toFloat() / 255.0f).toDouble(),
            (c.alpha.toFloat() / 255.0f).toDouble()
        )
    }
}