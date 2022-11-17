// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.Vec3d
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.util.function.Consumer

class Tracers : Module("Tracers", Category.RENDER) {

    var sky = create("Sky", false)
    var width = create("Width", 1.0, 0.1, 3.0)
    var range = create("Range", 80.0, 1.0, 200.0)

    override fun onRender3D(event: RenderEvent) {
        if (fullNullCheck()) {
            return
        }
        GlStateManager.pushMatrix()
        mc.world.loadedEntityList.forEach(Consumer { entity: Entity ->
            if (entity !is EntityPlayer || entity === mc.player) return@Consumer
            val player = entity as EntityPlayer
            if (mc.player.getDistance(player) > range.value) return@Consumer
            val colour = getColorByDistance(entity)
            drawLineToEntity(entity, colour[0], colour[1], colour[2], colour[3])
        })
        GlStateManager.popMatrix()
    }

    fun interpolate(now: Double, then: Double): Double {
        return then + (now - then) * mc.renderPartialTicks.toDouble()
    }

    fun interpolate(entity: Entity): DoubleArray {
        val posX = this.interpolate(entity.posX, entity.lastTickPosX) - mc.getRenderManager().renderPosX
        val posY = this.interpolate(entity.posY, entity.lastTickPosY) - mc.getRenderManager().renderPosY
        val posZ = this.interpolate(entity.posZ, entity.lastTickPosZ) - mc.getRenderManager().renderPosZ
        return doubleArrayOf(posX, posY, posZ)
    }

    fun drawLineToEntity(e: Entity, red: Float, green: Float, blue: Float, opacity: Float) {
        val xyz = this.interpolate(e)
        drawLine(xyz[0], xyz[1], xyz[2], e.height.toDouble(), red, green, blue, opacity)
    }

    fun drawLine(
        posx: Double,
        posy: Double,
        posz: Double,
        up: Double,
        red: Float,
        green: Float,
        blue: Float,
        opacity: Float
    ) {
        val eyes =
            Vec3d(0.0, 0.0, 1.0).rotatePitch(-Math.toRadians(mc.player.rotationPitch.toDouble()).toFloat()).rotateYaw(
                -Math.toRadians(
                    mc.player.rotationYaw.toDouble()
                ).toFloat()
            )
        if (!sky.value) {
            drawLineFromPosToPos(
                eyes.x,
                eyes.y + mc.player.getEyeHeight().toDouble(),
                eyes.z,
                posx,
                posy,
                posz,
                up,
                red,
                green,
                blue,
                opacity
            )
        } else {
            drawLineFromPosToPos(posx, 256.0, posz, posx, posy, posz, up, red, green, blue, opacity)
        }
    }

    fun drawLineFromPosToPos(
        posx: Double,
        posy: Double,
        posz: Double,
        posx2: Double,
        posy2: Double,
        posz2: Double,
        up: Double,
        red: Float,
        green: Float,
        blue: Float,
        opacity: Float
    ) {
        GL11.glBlendFunc(770, 771)
        GL11.glEnable(3042)
        GL11.glLineWidth(width.value.toFloat())
        GL11.glDisable(3553)
        GL11.glDisable(2929)
        GL11.glDepthMask(false)
        GL11.glColor4f(red, green, blue, opacity)
        GL11.glLoadIdentity()
        mc.entityRenderer.orientCamera(mc.renderPartialTicks)
        GL11.glBegin(1)
        GL11.glVertex3d(posx, posy, posz)
        GL11.glVertex3d(posx2, posy2, posz2)
        GL11.glVertex3d(posx2, posy2, posz2)
        GL11.glEnd()
        GL11.glEnable(3553)
        GL11.glEnable(2929)
        GL11.glDepthMask(true)
        GL11.glDisable(3042)
        GL11.glColor3d(1.0, 1.0, 1.0)
    }

    fun getColorByDistance(entity: Entity?): FloatArray {
        val col = Color(
            Color.HSBtoRGB(
                (Math.max(
                    0.0,
                    Math.min(mc.player.getDistanceSq(entity), 2500.0) / 2500.0f
                ) / 3.0).toFloat(), 1.0f, 0.8f
            ) or -0x1000000
        )
        return floatArrayOf(col.red.toFloat() / 255.0f, col.green.toFloat() / 255.0f, col.blue.toFloat() / 255.0f, 1.0f)
    }
}