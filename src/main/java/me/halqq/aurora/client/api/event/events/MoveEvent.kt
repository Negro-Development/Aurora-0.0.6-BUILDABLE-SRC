// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.event.events

import net.minecraft.entity.MoverType
import net.minecraftforge.fml.common.eventhandler.Cancelable

@Cancelable
class MoveEvent(var type: MoverType, var x: Double, var y: Double, var z: Double) : AuroraEvent()