// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous;

import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.impl.Aurora;
import me.halqq.aurora.client.impl.RPC;

public class RPCModule extends Module {

    public RPCModule() {
        super("RPC", Category.MISCELLANEOUS);
    }

    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;

    @Override
    public void onEnable() {
        Aurora.Companion.getINSTANCE().rpcGet().startRPC();
        discordRPC.Discord_UpdatePresence(discordRichPresence);
    }

    @Override
    public void onDisable() {

        Aurora.Companion.getINSTANCE().rpcGet().stopRPC();
    }

}

