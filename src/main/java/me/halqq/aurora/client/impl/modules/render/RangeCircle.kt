// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.module.ModuleManager
import net.minecraft.entity.player.EntityPlayer
import org.lwjgl.opengl.GL11

class RangeCircle : Module("RangeCircle", Category.RENDER) {

    var red = create("Red", 255, 0, 225)
    var green = create("Green", 0, 0, 225)
    var blue = create("Blue", 255, 0, 225)
    var alpha = create("Alpha", 170, 0, 225)
    var range = create("Range", 5.0, 0.5, 10.0)
    var width = create("Width", 2, 1, 5)
    var p: EntityPlayer? = null

    override fun onUpdate() {}

    override fun onRender3D(event: RenderEvent) {
        if (ModuleManager.INSTANCE.getModule(RangeCircle::class.java).isEnabled) {
            renderCirclePlayer()
        }
    }

    fun renderCirclePlayer() {
        GL11.glPushMatrix()
        GL11.glTranslated(
            mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * mc.timer.renderPartialTicks.toDouble() - mc.renderManager.renderPosX,
            mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * mc.timer.renderPartialTicks.toDouble() - mc.renderManager.renderPosY,
            mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks.toDouble() - mc.renderManager.renderPosZ
        )
        GL11.glEnable(3042)
        GL11.glEnable(2848)
        GL11.glDisable(3553)
        GL11.glDisable(2929)
        GL11.glBlendFunc(770, 771)
        GL11.glLineWidth(width.value.toFloat())
        GL11.glColor4f(red.value.toFloat(), green.value.toFloat(), blue.value.toFloat(), alpha.value.toFloat())
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f)
        GL11.glBegin(3)
        var i = 0
        while (i <= 360) {
            GL11.glVertex2f(
                Math.cos(i.toDouble() * Math.PI / 180.0).toFloat() * range.value.toFloat(),
                Math.sin(i.toDouble() * Math.PI / 180.0).toFloat() * range.value.toFloat()
            )
            i += 5
        }
        GL11.glEnd()
        GL11.glDisable(3042)
        GL11.glEnable(3553)
        GL11.glEnable(2929)
        GL11.glDisable(2848)
        GL11.glPopMatrix()
    }

    fun renderForAll() {
        GL11.glPushMatrix()
        GL11.glTranslated(
            p!!.lastTickPosX + (p!!.posX - p!!.lastTickPosX) * mc.timer.renderPartialTicks.toDouble() - mc.renderManager.renderPosX,
            p!!.lastTickPosY + (p!!.posY - p!!.lastTickPosY) * mc.timer.renderPartialTicks.toDouble() - mc.renderManager.renderPosY,
            p!!.lastTickPosZ + (p!!.posZ - p!!.lastTickPosZ) * mc.timer.renderPartialTicks.toDouble() - mc.renderManager.renderPosZ
        )
        GL11.glEnable(3042)
        GL11.glEnable(2848)
        GL11.glDisable(3553)
        GL11.glDisable(2929)
        GL11.glBlendFunc(770, 771)
        GL11.glLineWidth(width.value.toFloat())
        GL11.glColor4f(red.value.toFloat(), green.value.toFloat(), blue.value.toFloat(), alpha.value.toFloat())
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f)
        GL11.glBegin(3)
        var i = 0
        while (i <= 360) {
            GL11.glVertex2f(
                Math.cos(i.toDouble() * Math.PI / 180.0).toFloat() * range.value.toFloat(),
                Math.sin(i.toDouble() * Math.PI / 180.0).toFloat() * range.value.toFloat()
            )
            i += 5
        }
        GL11.glEnd()
        GL11.glDisable(3042)
        GL11.glEnable(3553)
        GL11.glEnable(2929)
        GL11.glDisable(2848)
        GL11.glPopMatrix()
    }
}