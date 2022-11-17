// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world.packetmine

import me.halqq.aurora.client.api.event.events.PlayerDamageBlockEvent
import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos

class BlockEventCollector(val master: ModulePacketMine) : MinecraftInstance() {
    var lastEvent: PlayerDamageBlockEvent? = null
    var currentEvent: PlayerDamageBlockEvent? = null

    var queue: MutableList<PlayerDamageBlockEvent> = ArrayList()

    @JvmName("getQueue1")
    fun getQueue(): List<PlayerDamageBlockEvent> {
        return queue
    }

    val isPreventNullable: Boolean
        get() = Minecraftable.mc.player == null || Minecraftable.mc.world == null

    operator fun contains(pos: BlockPos): Boolean {
        var contains = false
        for (events in queue) {
            val position = events.pos
            if (position.x == pos.x && position.y == pos.y && position.z == pos.z) {
                contains = true
                break
            }
        }
        return contains
    }

    fun add(event: PlayerDamageBlockEvent) {
        if (this.contains(event.pos)) {
            return
        }
        queue.add(event)
    }

    fun onUpdate() {
        if (isPreventNullable) {
            return
        }
        if (!queue.isEmpty()) {
            currentEvent = queue[0]

            val IsNonExistentOrDistant =
                Minecraftable.mc.world.getBlockState(queue[0].pos).block === Blocks.AIR || isDistantFromPlayer(
                    queue[0].pos
                )
            if (IsNonExistentOrDistant) {
                lastEvent = queue[0]
                queue.removeAt(0)
            }
        }
    }

    fun isDistantFromPlayer(position: BlockPos): Boolean {
        val x = Math.floor(position.x.toDouble()) + 0.5f
        val y = Math.floor(position.y.toDouble()) + 0.5f
        val z = Math.floor(position.z.toDouble()) + 0.5f
        return Minecraftable.mc.player.getDistance(x, y, z) > master.settingUpdateDistance.value
    }
}