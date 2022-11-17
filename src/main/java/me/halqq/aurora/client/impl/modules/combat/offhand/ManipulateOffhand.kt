// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat.offhand

import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraft.inventory.ClickType

abstract class ManipulateOffhand : MinecraftInstance() {
    fun onAction(slot: Int, action: String) {
        if (action.equals("unsafe", ignoreCase = true)) {
            val unsafeThread: Thread = OffhandThread(slot)
            unsafeThread.start()
        } else if (action.equals("safe", ignoreCase = true)) {
            Minecraftable.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, Minecraftable.mc.player)
            Minecraftable.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, Minecraftable.mc.player)
            Minecraftable.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, Minecraftable.mc.player)
            Minecraftable.mc.playerController.updateController()
        }
    }
}