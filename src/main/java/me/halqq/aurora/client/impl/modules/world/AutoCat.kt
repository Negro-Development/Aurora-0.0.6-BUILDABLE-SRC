// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.entity.passive.EntityOcelot
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent

class AutoCat : Module("AutoCat", Category.WORLD) {
    private var cat: EntityOcelot? = null
    override fun onEnable() {
        if (mc.player == null || mc.player.isDead) {
            onDisable()
            return
        }
        cat = EntityOcelot(mc.world)
        cat!!.customNameTag = "AuroraCat"
        cat!!.alwaysRenderNameTag = true
        cat!!.copyLocationAndAnglesFrom(mc.player)
        cat!!.health = 36f
        mc.world.addEntityToWorld(-12345, cat)
        cat!!.onLivingUpdate()
    }

    override fun onDisable() {
        if (mc.world != null) {
            mc.world.removeEntityFromWorld(-12345)
        }
    }

    @SubscribeEvent
    fun onClientDisconnect(event: ClientDisconnectionFromServerEvent?) {
        if (isEnabled) {
            onDisable()
        }
    }
}