// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package dev.joaoshiozo.loader

import me.halqq.aurora.client.impl.Aurora

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = LoaderMod.MOD_ID, name = LoaderMod.MOD_NAME, version = LoaderMod.VERSION)
class LoaderMod {
    companion object {
        const val MOD_ID = "loader"
        const val MOD_NAME = "Aurora-Loader"
        const val VERSION = "0.1"

        val log: Logger = LogManager.getLogger(MOD_NAME)
    }

    private val clientMod: Aurora by lazy {
        Aurora()
    }

    @Mod.EventHandler
    private fun init(event: FMLInitializationEvent) {
        clientMod.init(event)
    }

    @Mod.EventHandler
    private fun postinit(event: FMLPostInitializationEvent) {
        clientMod.postInit(event)
    }
}