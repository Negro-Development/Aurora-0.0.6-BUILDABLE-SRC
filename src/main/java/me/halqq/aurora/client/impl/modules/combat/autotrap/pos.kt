// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat.autotrap

import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraft.util.math.BlockPos


object pos : MinecraftInstance() {
    val Full = arrayOf(
        BlockPos(0, 2, 0),
        BlockPos(1, 1, 0),
        BlockPos(-1, 1, 0),
        BlockPos(0, 1, 1),
        BlockPos(0, 1, -1)
    )
}
