// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.event.events

import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

class PlayerDamageBlockEvent(val pos: BlockPos, directionFacing: EnumFacing?, stage: Stage?) :
    AuroraEvent(stage) {
    var direction: EnumFacing? = null

    init {
        direction = directionFacing
    }
}