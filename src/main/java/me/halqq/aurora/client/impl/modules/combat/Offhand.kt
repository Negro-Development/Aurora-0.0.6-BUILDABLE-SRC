// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.ItemUtil
import me.halqq.aurora.client.api.util.utils.TimerUtil
import net.minecraft.init.Items
import java.util.*

class Offhand : Module("OldOffhand", Category.COMBAT) {

    var moving = false
    var returnI = false
    var mode = create("Mode", "Gap", Arrays.asList("Crystal", "Gap", "Totem"))
    var health = create("Health", 16, 0, 36)
    private val timer: TimerUtil

    init {
        timer = TimerUtil()
    }

    override fun onUpdate() {
        offhandMode()
    }

    fun offhandMode() {
        if (mc.player.health + mc.player.absorptionAmount <= health.value) {
            ItemUtil.swapItemsOffhand(ItemUtil.findItem(Items.TOTEM_OF_UNDYING))
        } else if (mode.value.equals("Crystal", ignoreCase = true)) {
            setCrystals()
        } else if (mc.player.health + mc.player.absorptionAmount <= health.value) {
            ItemUtil.swapItemsOffhand(ItemUtil.findItem(Items.TOTEM_OF_UNDYING))
        } else if (mode.value.equals("Gap", ignoreCase = true)) {
            setGap()
        } else if (mc.player.health + mc.player.absorptionAmount <= health.value) {
            ItemUtil.swapItemsOffhand(ItemUtil.findItem(Items.TOTEM_OF_UNDYING))
        } else if (mode.value.equals("Totem", ignoreCase = true)) {
            ItemUtil.swapItemsOffhand(ItemUtil.findItem(Items.TOTEM_OF_UNDYING))
        } else if (mc.player.health + mc.player.absorptionAmount <= health.value) {
            ItemUtil.swapItemsOffhand(ItemUtil.findItem(Items.TOTEM_OF_UNDYING))
        }
    }

    fun setCrystals(): Boolean {
        ItemUtil.swapItemsOffhand(ItemUtil.findItem(Items.END_CRYSTAL))
        return false
    }

    fun setGap(): Boolean {
        ItemUtil.swapItemsOffhand(ItemUtil.findItem(Items.GOLDEN_APPLE))
        return false
    }
}