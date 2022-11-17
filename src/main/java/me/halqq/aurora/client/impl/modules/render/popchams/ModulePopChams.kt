// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render.popchams

import me.halqq.aurora.client.api.event.events.PacketEvent.PacketReceiveEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.play.server.SPacketEntityStatus
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

class ModulePopChams : Module("PopChams", Category.RENDER) {
    var settingStaticBoolean = create("Static", false)
    var settingRangeInt = create("Range", 8, 4, 16)
    var settingStampDouble = create("Stamp", 2.0, 0.1, 5.0)
    var settingNoLimitBoolean = create("NoLimit", true)
    var red = create("Red", 255, 0, 225)
    var green = create("Green", 0, 0, 225)
    var blue = create("Blue", 255, 0, 225)
    var alpha = create("Alpha", 170, 0, 225)
    var settingRenderMode = create("Mode", "Smooth", Arrays.asList("Smooth", "Wireframe", "Texture", "Solid"))

    protected var renderer: PopChamsRenderer? = null

    init {
        INSTANCE = this
        onInit()
    }

    fun onInit() {
        renderer = PopChamsRenderer(this)
    }

    protected val isSafeFromNull: Boolean
        protected get() = mc.player == null || mc.world == null

    @SubscribeEvent
    fun onPacketReceive(event: PacketReceiveEvent) {
        if (event.packet is SPacketEntityStatus) {
            val packet = event.packet as SPacketEntityStatus
            if (packet.opCode.toInt() != 35) {
                return
            }
            val entity = packet.getEntity(mc.world)
            if (entity == null || entity !is EntityPlayer || entity === mc.player || mc.player.getDistance(entity) > settingRangeInt.value) {
                return
            }
            var tracker = renderer!!.getTracker(entity.getName())
            val Flag = tracker == null
            if (Flag) {
                tracker = ChamsTracker(entity, 0)
                renderer!!.registryTracker(tracker)
            }
            tracker!!.addTotemUsage()
            if (settingNoLimitBoolean.value || Flag) {
                renderer!!.addRenderEntity(tracker)
            }
        }
    }

    override fun onWorldRender(partialTicks: Float) {
        if (isSafeFromNull) {
            return
        }
        renderer!!.onWorldRender(partialTicks)
    }

    override fun onUpdate() {
        if (isSafeFromNull) {
            return
        }

        renderer!!.onUpdate()
    }

    companion object {
        var INSTANCE: ModulePopChams = ModulePopChams()
    }
}