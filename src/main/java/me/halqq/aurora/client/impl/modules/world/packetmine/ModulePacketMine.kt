// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world.packetmine

import me.halqq.aurora.client.api.event.events.PlayerDamageBlockEvent
import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.RenderUtil
import net.minecraft.init.Blocks
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ModulePacketMine : Module("PacketMine", Category.WORLD) {
    var settingOffTicksInteger = create("OffTicks", 23, 0, 100)
    var settingUpdateDistance = create("UpdateDistance", 4.5, 2.0, 6.0)
    var redq = create("RedQ", 255, 0, 225)
    var greenq = create("GreenQ", 0, 0, 225)
    var blueq = create("BlueQ", 0, 0, 225)
    var alphaq = create("AlphaQ", 170, 0, 225)
    var redb = create("RedB", 0, 0, 225)
    var greenb = create("GreenB", 255, 0, 225)
    var blueb = create("BlueB", 0, 0, 225)
    var alphab = create("AlphaB", 170, 0, 225)

    protected var processor: BlockBreakProcessor? = null

    protected var collector: BlockEventCollector? = null

    init {
        onInit()
    }

    fun onInit() {
        processor = BlockBreakProcessor(this)
        collector = BlockEventCollector(this)
    }

    @SubscribeEvent
    fun onBlockDamageEvent(event: PlayerDamageBlockEvent) {
        val state = mc.world.getBlockState(event.pos)
        val block = state.block
        val hardness = state.getBlockHardness(mc.world, event.pos)
        if (hardness == -1f || block === Blocks.WEB || block === Blocks.AIR) {
            return
        }
        collector!!.add(event)
    }

    override fun onRender3D(renderEvent: RenderEvent) {
        if (mc.player == null || mc.world == null || collector!!.currentEvent == null) {
            return
        }
        for (event in collector!!.queue) {
            if (mc.world.getBlockState(event.pos).block === Blocks.AIR) {
                continue
            }
            val pos = event.pos
            val bb = AxisAlignedBB(
                pos.getX() - mc.getRenderManager().viewerPosX,
                pos.getY() - mc.getRenderManager().viewerPosY,
                pos.getZ() - mc.getRenderManager().viewerPosZ,
                pos.getX() + 1 - mc.getRenderManager().viewerPosX,
                pos.getY() + 1 - mc.getRenderManager().viewerPosY,
                pos.getZ() + 1 - mc.getRenderManager().viewerPosZ
            )
            if (event === collector!!.currentEvent) {
                if (RenderUtil.isInViewFrustrum(
                        AxisAlignedBB(
                            bb.minX + mc.getRenderManager().viewerPosX,
                            bb.minY + mc.getRenderManager().viewerPosY,
                            bb.minZ + mc.getRenderManager().viewerPosZ,
                            bb.maxX + mc.getRenderManager().viewerPosX,
                            bb.maxY + mc.getRenderManager().viewerPosY,
                            bb.maxZ + mc.getRenderManager().viewerPosZ
                        )
                    )
                ) {
                    RenderUtil.drawESP(
                        bb,
                        redb.value.toFloat(),
                        greenb.value.toFloat(),
                        blueb.value.toFloat(),
                        alphab.value.toFloat()
                    )
                }
            } else {
                if (RenderUtil.isInViewFrustrum(
                        AxisAlignedBB(
                            bb.minX + mc.getRenderManager().viewerPosX,
                            bb.minY + mc.getRenderManager().viewerPosY,
                            bb.minZ + mc.getRenderManager().viewerPosZ,
                            bb.maxX + mc.getRenderManager().viewerPosX,
                            bb.maxY + mc.getRenderManager().viewerPosY,
                            bb.maxZ + mc.getRenderManager().viewerPosZ
                        )
                    )
                ) {
                    RenderUtil.drawESP(
                        bb,
                        redq.value.toFloat(),
                        greenq.value.toFloat(),
                        blueq.value.toFloat(),
                        alphaq.value.toFloat()
                    )
                }
            }
        }
    }

    override fun onUpdate() {
        if (mc.player == null || mc.world == null) {
            return
        }
        if (collector!!.currentEvent != null && (processor!!.breakingBlockPosition == null || !collector!!.contains(
                processor!!.breakingBlockPosition!!
            ))
        ) {
            processor!!.setBreaking(
                collector!!.currentEvent!!.pos, mc.world.getBlockState(
                    collector!!.currentEvent!!.pos
                ).block, collector!!.currentEvent!!.direction
            )
        }
        processor!!.onUpdate()
        collector!!.onUpdate()
    }
}