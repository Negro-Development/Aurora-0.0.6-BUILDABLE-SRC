// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render.popchams

import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import me.rina.turok.util.TurokMath
import me.rina.turok.util.TurokTick
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.entity.player.EntityPlayer
import org.lwjgl.opengl.GL11

class PopChamsRender : MinecraftInstance() {

    val timerStamp: TurokTick
    var entityPlayer: EntityPlayer? = null
    var entityPlayerAlphaDegreesAmount = 0f
    protected var renderPosX = 0.0
    protected var renderPosY = 0.0
    protected var renderPosZ = 0.0
    protected var renderYaw = 0f

    init {
        timerStamp = TurokTick()
    }

    fun setPositionRotation(x: Double, y: Double, z: Double, yaw: Float) {
        renderPosX = x
        renderPosY = y
        renderPosZ = z
        renderYaw = yaw
    }

    val isTimeReach: Boolean
        get() = timerStamp.isPassedMS((ModulePopChams.INSTANCE.settingStampDouble.value * 1000).toFloat()) || entityPlayerAlphaDegreesAmount <= 5

    fun onRender(partialTicks: Float) {
        entityPlayerAlphaDegreesAmount =
            TurokMath.clamp(ModulePopChams.INSTANCE.alpha.value - timerStamp.getCurrentTicksCount(10.0), 0, 255)
                .toFloat()

        if (isTimeReach) {
            return
        }
        GL11.glPushMatrix()
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        val d0 = renderPosX
        val d1 = renderPosY
        val d2 = renderPosZ
        val f = renderYaw
        val i = entityPlayer!!.brightnessForRender
        val j = i % 65536
        val k = i / 65536
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j.toFloat(), k.toFloat())
        if (ModulePopChams.INSTANCE.settingRenderMode.value.equals("Smooth", ignoreCase = true)) {
            GL11.glDisable(GL11.GL_LIGHTING)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glEnable(GL11.GL_CULL_FACE)
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL)
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL)
            GL11.glPolygonOffset(1.0f, -1100000.0f)
            GlStateManager.color(
                ModulePopChams.INSTANCE.red.value / 255.0f,
                ModulePopChams.INSTANCE.green.value / 255.0f,
                ModulePopChams.INSTANCE.blue.value / 255.0f,
                entityPlayerAlphaDegreesAmount / 255.0f
            )
        } else if (ModulePopChams.INSTANCE.settingRenderMode.value.equals("Wireframe", ignoreCase = true)) {
            GL11.glDisable(GL11.GL_LIGHTING)
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GL11.glEnable(GL11.GL_CULL_FACE)
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE)
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE)
            GL11.glPolygonOffset(1.0f, -1100000.0f)
            GlStateManager.color(
                ModulePopChams.INSTANCE.red.value / 255.0f,
                ModulePopChams.INSTANCE.green.value / 255.0f,
                ModulePopChams.INSTANCE.blue.value / 255.0f,
                entityPlayerAlphaDegreesAmount / 255.0f
            )
        } else if (ModulePopChams.INSTANCE.settingRenderMode.value.equals("Texture", ignoreCase = true)) {
            GL11.glDisable(GL11.GL_LIGHTING)
            GlStateManager.color(
                ModulePopChams.INSTANCE.red.value / 255.0f,
                ModulePopChams.INSTANCE.green.value / 255.0f,
                ModulePopChams.INSTANCE.blue.value / 255.0f,
                entityPlayerAlphaDegreesAmount / 255.0f
            )
        } else if (ModulePopChams.INSTANCE.settingRenderMode.value.equals("Solid", ignoreCase = true)) {
            GL11.glDisable(GL11.GL_TEXTURE_2D)
            GlStateManager.color(
                ModulePopChams.INSTANCE.red.value / 255.0f,
                ModulePopChams.INSTANCE.green.value / 255.0f,
                ModulePopChams.INSTANCE.blue.value / 255.0f,
                entityPlayerAlphaDegreesAmount / 255.0f
            )
        }
        Minecraftable.mc.renderManager.renderEntity(
            entityPlayer,
            d0 - Minecraftable.mc.getRenderManager().renderPosX,
            d1 - Minecraftable.mc.getRenderManager().renderPosY,
            d2 - Minecraftable.mc.getRenderManager().renderPosZ,
            f,
            partialTicks,
            false
        )
        GL11.glPopAttrib()
        GL11.glPopMatrix()
    }
}