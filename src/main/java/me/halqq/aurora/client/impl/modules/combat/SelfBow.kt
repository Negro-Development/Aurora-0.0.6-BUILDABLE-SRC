// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.item.ItemBow
import net.minecraft.network.play.client.CPacketPlayer

class SelfBow : Module("SelfBow", Category.COMBAT) {
    var at = create("AutoDisble", true)
    override fun onUpdate() {
        if (mc.player.inventory.getCurrentItem().getItem() is ItemBow && mc.player.isHandActive && mc.player.itemInUseMaxCount >= 3) {
            mc.player.connection.sendPacket(CPacketPlayer.Rotation(mc.player.cameraYaw, -90F, mc.player.onGround))
            if (at.value) {
                toggle()
            }
        }
    }
}