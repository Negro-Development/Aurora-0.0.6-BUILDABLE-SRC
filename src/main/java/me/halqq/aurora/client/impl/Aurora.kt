// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl

import me.halqq.aurora.client.api.command.CommandManager
import me.halqq.aurora.client.api.config.ConfigManager
import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.gui.ClientGuiScreen
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.SettingManager
import me.halqq.aurora.client.api.util.utils.MiscUtil
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import org.lwjgl.opengl.Display


@Mod(modid = Aurora.MOD_ID, name = Aurora.MOD_NAME, version = Aurora.VERSION)

class Aurora {
    var customMainScreen: GuiCustomMainScreen? = null
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        customMainScreen = GuiCustomMainScreen()
    }

    lateinit var discord_rpc: RPC
    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent?) {
        Display.setTitle(MOD_NAME + " " + VERSION)
        MiscUtil.loadClientIcon()
        SettingManager.INSTANCE = SettingManager()
        ConfigManager.INSTANCE = ConfigManager()
        TextManager.INSTANCE = TextManager()
        ModuleManager.INSTANCE = ModuleManager()
        CommandManager.INSTANCE = CommandManager()
        ClientGuiScreen.INSTANCE = ClientGuiScreen()
        ConfigManager.INSTANCE.loadConfigs()
        discord_rpc = RPC()
    }

    fun rpcGet(): RPC {
        return discord_rpc
    }

    companion object {
        const val MOD_ID = "aurora"
        const val MOD_NAME = "Aurora"
        const val VERSION = "0.0.6"

        @Mod.Instance
        lateinit var INSTANCE: Aurora
    }
}