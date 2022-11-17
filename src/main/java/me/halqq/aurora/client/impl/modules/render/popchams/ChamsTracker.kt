// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render.popchams

import com.mojang.authlib.GameProfile
import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.entity.player.EntityPlayer

class ChamsTracker(entityLiving: EntityPlayer?, stage: Int) : MinecraftInstance() {
    var entityPlayer: EntityPlayer? = null
    var popChamsRender: PopChamsRender? = null
    var totemUsageCount = 0
    var lastTotemUsage = 0
    protected var interpolatedAlpha = 0f
    var isFinished = false

    init {
        entityPlayer = entityLiving
        totemUsageCount = stage
    }

    fun createPopChamsRender() {
        popChamsRender = PopChamsRender()
    }

    fun onWorldRender(partialTicks: Float) {
        if (entityPlayer == null) {
            return
        }
    }

    fun onUpdate() {
        if (entityPlayer == null) {
            return
        }
        isFinished = popChamsRender!!.isTimeReach
    }

    fun addTotemUsage() {
        totemUsageCount++
        updateRenderEntity()
    }

    fun resetTotemUsage() {
        totemUsageCount = 0
    }

    fun updateRenderEntity() {
        createPopChamsRender()
        popChamsRender!!.timerStamp.reset()

        if (ModulePopChams.INSTANCE.settingStaticBoolean.value) {
            popChamsRender!!.entityPlayer =
                EntityOtherPlayerMP(Minecraftable.mc.world, GameProfile(entityPlayer!!.getGameProfile().id, ""))
            popChamsRender!!.entityPlayer?.setPositionAndRotation(
                entityPlayer!!.posX,
                entityPlayer!!.posY,
                entityPlayer!!.posZ,
                entityPlayer!!.rotationYaw,
                entityPlayer!!.rotationPitch
            )
            popChamsRender!!.entityPlayer?.setRenderYawOffset(entityPlayer!!.renderYawOffset)
            popChamsRender!!.entityPlayer?.renderOffsetX = entityPlayer!!.renderOffsetX
            popChamsRender!!.entityPlayer?.renderOffsetY = entityPlayer!!.renderOffsetY
            popChamsRender!!.entityPlayer?.renderOffsetZ = entityPlayer!!.renderOffsetZ
            popChamsRender!!.entityPlayer?.swingProgress = entityPlayer!!.swingProgress
            popChamsRender!!.entityPlayer?.limbSwing = entityPlayer!!.limbSwing
            popChamsRender!!.entityPlayer?.limbSwingAmount = entityPlayer!!.limbSwingAmount
            popChamsRender!!.entityPlayer?.rotationYawHead = entityPlayer!!.rotationYawHead
            popChamsRender!!.entityPlayer?.chasingPosX = entityPlayer!!.chasingPosX
            popChamsRender!!.entityPlayer?.chasingPosY = entityPlayer!!.chasingPosY
            popChamsRender!!.entityPlayer?.chasingPosZ = entityPlayer!!.chasingPosZ
            popChamsRender!!.entityPlayer?.cameraPitch = entityPlayer!!.cameraPitch
            popChamsRender!!.entityPlayer?.cameraYaw = entityPlayer!!.cameraYaw
        } else {
            popChamsRender!!.entityPlayer = entityPlayer
        }
        popChamsRender!!.setPositionRotation(
            entityPlayer!!.posX,
            entityPlayer!!.posY,
            entityPlayer!!.posZ,
            entityPlayer!!.rotationYaw
        )
    }
}