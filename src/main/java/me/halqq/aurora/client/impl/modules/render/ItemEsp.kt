// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.entity.item.EntityItem
import net.minecraft.util.math.BlockPos

class ItemEsp : Module("ItemEsp", Category.RENDER) {

    var item: EntityItem? = null
    private val itemPosition: Set<BlockPos> = HashSet()

    override fun onRender3D(event: RenderEvent) {}
}