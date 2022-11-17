// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.halqq.aurora.client.api.event.events.PacketEvent;
import me.halqq.aurora.client.api.friend.FriendManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.util.utils.MessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketEntityStatus;


import java.util.HashMap;


public class TotemPop extends Module {

    public TotemPop() {
        super("TotemPop", Category.COMBAT);
    }

    HashMap<String, Integer> poplist = new HashMap<>();

    SettingBoolean message = create("AnnouncePop", true);


    @Override
    public void onEnable() {
        poplist.clear();
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent event) {

        if (event.getPacket() instanceof SPacketEntityStatus) {

            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);

                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;

                    if (player.getHealth() > 0) {
                        if (poplist.containsKey(player.getName())) {
                            int pops = poplist.get(player.getName());
                            poplist.put(player.getName(), pops + 1);

                        } else {
                            poplist.put(player.getName(), 1);

                        }
                        if(!message.getValue()) {
                            MessageUtil.sendClientMessage(ChatFormatting.RED + "[TotemPop]" + player.getName() + ChatFormatting.WHITE + " has popped " + ChatFormatting.RED + poplist.get(player.getName()) + ChatFormatting.WHITE + " totems!");
                        } else {
                            MessageUtil.sendSilentMessage(player.getName() + " has popped " + poplist.get(player.getName()) + " totems!");
                        }

                        if(player.getHealth() <= 0) {
                            poplist.remove(player.getName());
                            MessageUtil.sendClientMessage(ChatFormatting.RED + "[TotemPop]" + player.getName() + ChatFormatting.WHITE + " has died" + "After popping " + ChatFormatting.RED + poplist.get(player.getName()) + ChatFormatting.WHITE + " totems!");

                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        poplist.clear();
    }
}
