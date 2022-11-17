// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat.offhand

import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraft.item.Item

class OffhandHandler(private val master: ModuleOffhand) : ManipulateOffhand() {
    private var currentItemInHandState: EnumOffhandState? = null
    private var currentItemContextState: EnumOffhandState? = null
    var currentItemItem: Item? = null
    private var currentItemSlot = 0

    private val isPreventNullable: Boolean
        get() = Minecraftable.mc.player == null || Minecraftable.mc.world == null || currentItemItem == null

     fun onUpdate() {
        if (isPreventNullable) {
            return
        }
        currentItemInHandState = getState("Hand")
        currentItemContextState = getState("Find")
        if (currentItemInHandState === EnumOffhandState.EMPTY && currentItemContextState === EnumOffhandState.FOUND && currentItemSlot != -1) {
            onAction(currentItemSlot, master.offhandSettingPacketMode.value)
        }
    }

    fun getState(action: String): EnumOffhandState {
        var state = EnumOffhandState.EMPTY
        if (isPreventNullable) {
            state = EnumOffhandState.NULL
            return state
        }
        if (action.equals("find", ignoreCase = true)) {
            currentItemSlot = findSlot()
            if (currentItemSlot != -1) {
                state = EnumOffhandState.FOUND
            }
        } else if (action.equals("hand", ignoreCase = true)) {
            state =
                if (Minecraftable.mc.player.heldItemOffhand.getItem() === currentItemItem) EnumOffhandState.EQUIPPED else state
        }
        return state
    }

    private fun findSlot(): Int {
        var slot = -1
        if (isPreventNullable) {
            return slot
        }
        var i = 0
        while (i < 36) {
            val item = Minecraftable.mc.player.inventory.getStackInSlot(i).getItem()
            if (item === currentItemItem) {
                if (i < 9) {
                    i += 36
                }
                slot = i
                break
            }
            i++
        }
        return slot
    }
}