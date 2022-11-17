// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.movement

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.network.play.client.CPacketPlayer
import java.util.*


class Step : Module("Step", Category.MOVEMENT) {
    var mode = create("Mode", "Vanilla", Arrays.asList("Vanilla", "Packet"))
    var height = create("Height", 2.0, 1.0, 3.0)
    override fun onUpdate() {
        if (fullNullCheck()) return
        if (!mc.player.onGround || mc.player.isOnLadder || mc.player.isInWater || mc.player.isInLava || mc.player.movementInput.jump || mc.player.noClip || mc.player.moveForward == 0f && mc.player.moveStrafing == 0f) return
        val n = nNormal
        when (mode.value) {
            "Vanilla" -> mc.player.stepHeight = height.value.toFloat()
            "Default" -> {
                if (n < 0 || n > 2) return
                if (n == 2.0) {
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.42,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.78,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.63,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.51,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.9,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.21,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.45,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.43,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.setPosition(mc.player.posX, mc.player.posY + 2.0, mc.player.posZ)
                }
                if (n == 1.5) {
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.41999998688698,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.7531999805212,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.00133597911214,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.16610926093821,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.24918707874468,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.1707870772188,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ)
                }
                if (n == 1.0) {
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.41999998688698,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.7531999805212,
                            mc.player.posZ,
                            mc.player.onGround
                        )
                    )
                    mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ)
                }
            }
        }
    }

    override fun onDisable() {
        if (fullNullCheck()) return
        mc.player.stepHeight = 0.5f
    }

    val nNormal: Double
        get() {
            mc.player.stepHeight = 0.5f
            var maxY = -1.0
            val grow = mc.player.entityBoundingBox.offset(0.0, 0.05, 0.0).grow(0.05)
            if (!mc.world.getCollisionBoxes(mc.player, grow.offset(0.0, 2.0, 0.0)).isEmpty()) return 100.0
            for (aabb in mc.world.getCollisionBoxes(mc.player, grow)) {
                if (aabb.maxY > maxY) {
                    maxY = aabb.maxY
                }
            }
            return maxY - mc.player.posY
        }
}