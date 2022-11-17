// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world.packetmine

import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraft.block.Block
import net.minecraft.network.play.client.CPacketPlayerDigging
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

class BlockBreakProcessor(val master: ModulePacketMine) : MinecraftInstance() {
    var breakingBlockType: Block? = null
    var breakingBlockPosition: BlockPos? = null
    var breakingBlockFacing: EnumFacing? = null
    protected var firstPacketSent = false
    protected var requestNewPacket = false
    protected var amountTicks = 0

    val isPreventNullable: Boolean
        get() = Minecraftable.mc.player == null || Minecraftable.mc.world == null || breakingBlockPosition == null || breakingBlockType == null

    fun setBreaking(position: BlockPos?, block: Block?, facing: EnumFacing?) {
        breakingBlockPosition = position
        breakingBlockType = block
        breakingBlockFacing = facing
        firstPacketSent = false
        requestNewPacket = false
    }

    fun onUpdate() {
        if (isPreventNullable) {
            return
        }
        if (!firstPacketSent || requestNewPacket) {
            if (breakingBlockFacing == null) {
                breakingBlockFacing =
                    EnumFacing.getDirectionFromEntityLiving(breakingBlockPosition, Minecraftable.mc.player)
            }
            Minecraftable.mc.player.connection.sendPacket(
                CPacketPlayerDigging(
                    CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
                    breakingBlockPosition, breakingBlockFacing
                )
            )
            Minecraftable.mc.player.connection.sendPacket(
                CPacketPlayerDigging(
                    CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                    breakingBlockPosition, breakingBlockFacing
                )
            )
            firstPacketSent = true
            requestNewPacket = false
        }
        if (amountTicks >= master.settingOffTicksInteger.value) {
            requestNewPacket = true
            amountTicks = 0
        }
        amountTicks++
    }
}