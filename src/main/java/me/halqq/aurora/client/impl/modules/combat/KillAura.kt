// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.friend.FriendManager
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.settings.SettingBoolean
import me.halqq.aurora.client.api.setting.settings.SettingInteger
import me.halqq.aurora.client.api.util.Minecraftable
import me.halqq.aurora.client.api.util.utils.InventoryUtil
import me.halqq.aurora.client.api.util.utils.ItemUtil
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemSword
import net.minecraft.util.EnumHand

class KillAura : Module("KillAura", Category.COMBAT) {
    var enemy: EntityPlayer? = null
    var mob: Criticals? = null
    var range = create("Range", 2, 0, 4)
    var sword = create("OnlySword", true)
    var sswitch = create("AutoSwitch", true)
    var criticals = create("Criticals", true)
    var srotate = false
    var oldSlot = 0
    override fun onEnable() {
        if (criticals.value) {
            ModuleManager.INSTANCE.getModule(Criticals::class.java).setEnabled()
        }
        oldSlot = mc.player.inventory.currentItem
    }

    override fun onUpdate() {
        killauras()
    }

    fun killauras() {
        for (player in mc.world.playerEntities) if (player !== mc.player) {
            if (!FriendManager.isFriend(player.name)) {
                if (mc.player.getDistance(player) < range.value) {
                    if (mc.player.getCooledAttackStrength(0f) >= 1) {
                        mc.playerController.attackEntity(mc.player, player)
                        mc.player.swingArm(EnumHand.MAIN_HAND)
                        enemy = player
                    }
                }
            }
        }
        if (sswitch.value) {
            ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemSword::class.java), false)
        }
    }

    override fun onDisable() {
        srotate = false
        if (criticals.value) {
            ModuleManager.INSTANCE.getModule(Criticals::class.java).setDisabled()
        }
        if (mc.player.inventory.currentItem != oldSlot) {
            InventoryUtil.swapToHotbarSlot(oldSlot, false)
        }
    }
}