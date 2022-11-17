// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat.offhand

import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraft.inventory.ClickType

class OffhandThread(protected var slot: Int) : Thread() {
    override fun run() {
        if (Minecraftable.mc.player == null || slot == -1) {
            return
        }
        Minecraftable.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, Minecraftable.mc.player)
        Minecraftable.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, Minecraftable.mc.player)
        Minecraftable.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, Minecraftable.mc.player)
        Minecraftable.mc.playerController.updateController()
    }
}