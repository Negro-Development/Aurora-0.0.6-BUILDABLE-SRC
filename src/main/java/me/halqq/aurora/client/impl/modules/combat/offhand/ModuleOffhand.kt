// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat.offhand

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import java.util.*

open class ModuleOffhand : Module("Offhand", Category.COMBAT) {
    public var offhandSettingPacketMode = create("Packet", "Safe", Arrays.asList("Safe", "Unsafe"))
    var offhandSettingItemMode = create("Item", "Totem", Arrays.asList("Totem", "GoldenApple", "EndCrystal", "Bed", "Bow", "Obsidian"))
    var offhandSettingHealthInteger = create("Health", 18, 0, 20)

    protected var handler: OffhandHandler? = null

    protected var map: HashMap<String, Item>? = null

    init {
        onInit()
    }

    fun onInit() {
        handler = OffhandHandler(this)
        map = HashMap()
        map!!["Totem"] = Items.TOTEM_OF_UNDYING
        map!!["Bow"] = Items.BOW
        map!!["EndCrystal"] = Items.END_CRYSTAL
        map!!["GoldenApple"] = Items.GOLDEN_APPLE
        map!!["Bed"] = Items.BED
        map!!["Obsidian"] = Item.getItemFromBlock(Blocks.OBSIDIAN)
    }

    override fun onUpdate() {
        if (mc.player == null) {
            return
        }
        if (mc.player.health + mc.player.absorptionAmount < offhandSettingHealthInteger.value) {
            handler!!.currentItemItem = Items.TOTEM_OF_UNDYING
        } else {
            handler!!.currentItemItem = map!![offhandSettingItemMode.value]
        }
        handler!!.onUpdate()
    }
}