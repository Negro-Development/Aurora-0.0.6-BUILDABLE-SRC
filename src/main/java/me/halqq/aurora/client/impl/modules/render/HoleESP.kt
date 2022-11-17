// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import com.google.common.collect.Sets
import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.BlockUtil
import me.halqq.aurora.client.api.util.utils.Colour
import me.halqq.aurora.client.api.util.utils.GeometryMasks
import me.halqq.aurora.client.api.util.utils.HoleUtil.*
import me.halqq.aurora.client.api.util.utils.RenderUtil
import net.minecraft.init.Blocks
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

class HoleESP : Module("HoleESP", Category.RENDER) {

    var radius = create("Radius", 8.0, 0.1, 20.0)
    var ignoreOwnHole = create("IgnoreOwn", true)
    var flatOwn = create("FlatOwn", false)
    var height = create("Height", 0.2, 0.01, 1.0)
    var width = create("Widht", 1.0, 1.0, 10.0)
    var type = create("Type", "Outline", Arrays.asList("Fill", "Outline", "Both"))
    var mode = create("Mode", "Double", Arrays.asList("Air", "Ground", "Flat", "Slab", "Double"))
    var redo = create("RedO", 255, 0, 225)
    var greeno = create("GreenO", 0, 0, 225)
    var blueo = create("BlueO", 0, 0, 225)
    var redb = create("RedB", 0, 0, 225)
    var greenb = create("GreenB", 0, 0, 225)
    var blueb = create("BlueB", 0, 0, 225)
    var alpha = create("Alpha", 180, 0, 255)
    var custom = create("CustomHoles", true)
    var customMode = create("CustomMode", "Double", Arrays.asList("Single", "Double", "Custom"))
    private var holes: ConcurrentHashMap<AxisAlignedBB, Colour>? = null

    override fun onUpdate() {
        if (mc.player == null || mc.world == null) return
        if (holes == null) {
            holes = ConcurrentHashMap()
        } else {
            holes!!.clear()
        }
        val range = Math.ceil(radius.value).toInt()
        val possibleHoles = Sets.newHashSet<BlockPos>()
        val blockPosList =
            ArrayList(BlockUtil.getSphere(BlockUtil.getPlayerPos(), range.toFloat(), range, false, true, 0))
        for (pos in blockPosList) {
            if (mc.world.getBlockState(pos).block != Blocks.AIR) {
                continue
            }
            if (mc.world.getBlockState(pos.add(0, -1, 0)).block == Blocks.AIR) {
                continue
            }
            if (mc.world.getBlockState(pos.add(0, 1, 0)).block != Blocks.AIR) {
                continue
            }
            if (mc.world.getBlockState(pos.add(0, 2, 0)).block == Blocks.AIR) {
                possibleHoles.add(pos)
            }
        }
        possibleHoles.forEach(Consumer { pos: BlockPos? ->
            val holeInfo = isHole(pos, false, false)
            val holeType = holeInfo.type
            if (holeType != HoleType.NONE) {
                val holeSafety = holeInfo.safety
                val centerBlock = holeInfo.centre
                var colour: Colour
                colour = if (holeSafety == BlockSafety.UNBREAKABLE) {
                    Colour(
                        redb.value,
                        greenb.value,
                        blueb.value,
                        alpha.value
                    )
                } else {
                    Colour(
                        redo.value,
                        greeno.value,
                        blueo.value,
                        alpha.value
                    )
                }
                if (holeType == HoleType.CUSTOM) {
                    colour = Colour(255, 255, 255, 255)
                }
                val mode = customMode.value
                if (mode.equals(
                        "Custom",
                        ignoreCase = true
                    ) && (holeType == HoleType.CUSTOM || holeType == HoleType.DOUBLE)
                ) {
                    holes!![centerBlock] = colour
                } else if (mode.equals("Double", ignoreCase = true) && holeType == HoleType.DOUBLE) {
                    holes!![centerBlock] = colour
                } else if (holeType == HoleType.SINGLE) {
                    holes!![centerBlock] = colour
                }
            }
        })
    }

    override fun onRender3D(event: RenderEvent) {
        if (mc.player == null || mc.world == null || holes == null || holes!!.isEmpty()) {
            return
        }
        holes!!.forEach { (hole: AxisAlignedBB, color: Colour) ->
            renderHoles(
                hole,
                color
            )
        }
    }

    private fun renderHoles(hole: AxisAlignedBB, color: Colour) {
        when (type.value) {
            "Outline" -> {
                renderOutline(hole, color)
            }
            "Fill" -> {
                renderFill(hole, color)
            }
            "Both" -> {
                renderOutline(hole, color)
                renderFill(hole, color)
            }
        }
    }

    private fun renderFill(hole: AxisAlignedBB, color: Colour) {
        val fillColor = Colour(color, 50)
        val ufoAlpha = alpha.value * 50 / 255
        if (ignoreOwnHole.value && hole.intersects(mc.player.entityBoundingBox)) return
        when (mode.value) {
            "Air" -> {
                if (flatOwn.value && hole.intersects(mc.player.entityBoundingBox)) {
                    RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, GeometryMasks.Quad.DOWN)
                } else {
                    RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, GeometryMasks.Quad.ALL)
                }
            }
            "Ground" -> {
                RenderUtil.drawBox(
                    hole.offset(0.0, -1.0, 0.0),
                    true,
                    1.0,
                    Colour(fillColor, ufoAlpha),
                    fillColor.alpha,
                    GeometryMasks.Quad.ALL
                )
            }
            "Flat" -> {
                RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, GeometryMasks.Quad.DOWN)
            }
            "Slab" -> {
                if (flatOwn.value && hole.intersects(mc.player.entityBoundingBox)) {
                    RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, GeometryMasks.Quad.DOWN)
                } else {
                    RenderUtil.drawBox(hole, false, height.value, fillColor, ufoAlpha, GeometryMasks.Quad.ALL)
                }
            }
            "Double" -> {
                if (flatOwn.value && hole.intersects(mc.player.entityBoundingBox)) {
                    RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, GeometryMasks.Quad.DOWN)
                } else {
                    RenderUtil.drawBox(
                        hole.setMaxY(hole.maxY + 1),
                        true,
                        2.0,
                        fillColor,
                        ufoAlpha,
                        GeometryMasks.Quad.ALL
                    )
                }
            }
        }
    }

    private fun renderOutline(hole: AxisAlignedBB, color: Colour) {
        val outlineColor = Colour(color, 255)
        if (ignoreOwnHole.value && hole.intersects(mc.player.entityBoundingBox)) return
        when (mode.value) {
            "Air" -> {
                if (flatOwn.value && hole.intersects(mc.player.entityBoundingBox)) {
                    RenderUtil.drawBoundingBoxWithSides(
                        hole,
                        width.value,
                        outlineColor,
                        alpha.value,
                        GeometryMasks.Quad.DOWN
                    )
                } else {
                    RenderUtil.drawBoundingBox(hole, width.value.toFloat(), outlineColor, alpha.value)
                }
            }
            "Ground" -> {
                RenderUtil.drawBoundingBox(
                    hole.offset(0.0, -1.0, 0.0),
                    width.value.toFloat(),
                    Colour(outlineColor, alpha.value),
                    outlineColor.alpha
                )
            }
            "Flat" -> {
                RenderUtil.drawBoundingBoxWithSides(
                    hole,
                    width.value,
                    outlineColor,
                    alpha.value,
                    GeometryMasks.Quad.DOWN
                )
            }
            "Slab" -> {
                if (flatOwn.value && hole.intersects(mc.player.entityBoundingBox)) {
                    RenderUtil.drawBoundingBoxWithSides(
                        hole,
                        width.value,
                        outlineColor,
                        alpha.value,
                        GeometryMasks.Quad.DOWN
                    )
                } else {
                    RenderUtil.drawBoundingBox(
                        hole.setMaxY(hole.minY + height.value),
                        alpha.value.toFloat(),
                        outlineColor,
                        alpha.value
                    )
                }
            }
            "Double" -> {
                if (flatOwn.value && hole.intersects(mc.player.entityBoundingBox)) {
                    RenderUtil.drawBoundingBoxWithSides(
                        hole,
                        width.value,
                        outlineColor,
                        alpha.value,
                        GeometryMasks.Quad.DOWN
                    )
                } else {
                    RenderUtil.drawBoundingBox(
                        hole.setMaxY(hole.maxY + 1),
                        width.value.toFloat(),
                        outlineColor,
                        alpha.value / 255
                    )
                }
            }
        }
    }
}