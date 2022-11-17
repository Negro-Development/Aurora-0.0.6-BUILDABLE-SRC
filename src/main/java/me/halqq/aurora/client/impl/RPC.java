// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class RPC extends Thread {

    public final DiscordRPC discord_rpc;
    public DiscordRichPresence discord_presence;

    public final Minecraft mc = Minecraft.getMinecraft();

    public String detail_option_1;
    public String detail_option_2;
    public String detail_option_3;
    public String detail_option_4;

    public String state_option_1;
    public String state_option_2;
    public String state_option_3;
    public String state_option_4;

    public RPC() {
        this.discord_rpc      = DiscordRPC.INSTANCE;
        this.discord_presence = new DiscordRichPresence();

        this.detail_option_1 = "";
        this.detail_option_2 = "";
        this.detail_option_3 = "";
        this.detail_option_4 = "";

        this.state_option_1 = "";
        this.state_option_2 = "";
        this.state_option_3 = "";
        this.state_option_4 = "";
    }
    public void stopRPC() {
        this.discord_rpc.Discord_Shutdown();
    }

    public void startRPC() {
        this.discord_presence = new DiscordRichPresence();

        final DiscordEventHandlers handler_ = new DiscordEventHandlers();

        this.discord_rpc.Discord_Initialize("1042562650695008337", handler_, true, "");

        this.discord_presence.largeImageText = "Aurora " + Aurora.VERSION;

        this.discord_presence.largeImageKey  = "shiozo";

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (mc.world == null) {
                        this.detail_option_1 = "";
                        this.detail_option_2 = "Afk in main menu";
                        this.state_option_1 = "";
                    } else {
                        this.detail_option_1 = "";

                        if (mc.isIntegratedServerRunning()) {
                            this.detail_option_2 = "Playing in " + Objects.requireNonNull(mc.getCurrentServerData()).serverIP;
                            this.state_option_1 = mc.player.getName() + " like furry porn";
                        }
                    }

                    String detail = this.detail_option_1 + this.detail_option_2 + this.detail_option_3 + this.detail_option_4;
                    String state  = this.state_option_1  + this.state_option_2  + this.state_option_3  + this.state_option_4;

                    this.discord_rpc.Discord_RunCallbacks();

                    this.discord_presence.details = detail;
                    this.discord_presence.state = state;

                    this.discord_rpc.Discord_UpdatePresence(this.discord_presence);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }

                try {
                    Thread.sleep(4000L);
                }

                catch (InterruptedException exc_) {
                    exc_.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public String set(String presume) {
        return " " + presume;
    }
}
