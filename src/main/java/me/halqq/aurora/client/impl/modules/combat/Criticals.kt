// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.event.events.PacketEvent.PacketSendEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.network.play.client.CPacketUseEntity
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

class Criticals
    : Module("Criticals", Category.COMBAT) {
    var mode = create("Mode", "Packet", Arrays.asList("Vanilla", "Packet", "Mini jump"))
    @SubscribeEvent
    fun onPacketSend(event: PacketSendEvent) {
        if (event.packet !is CPacketUseEntity) {
            return
        }
        if (mode.value.equals("Vanilla", ignoreCase = true)) {
            if (event.packet is CPacketUseEntity) {
                if ((event.packet as CPacketUseEntity).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.jump()
                }
            }
        } else if (mode.value.equals("Mini jump", ignoreCase = true)) {
            if (event.packet is CPacketUseEntity) {
                if ((event.packet as CPacketUseEntity).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.jump()
                    mc.player.motionY /= 3.0
                }
            }
        } else if (mode.value.equals("Packet", ignoreCase = true)) {
            if (event.packet is CPacketUseEntity) {
                if ((event.packet as CPacketUseEntity).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.1,
                            mc.player.posZ,
                            false
                        )
                    )
                    mc.player.connection.sendPacket(
                        CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY,
                            mc.player.posZ,
                            false
                        )
                    )
                }
            }
        }
    }
}