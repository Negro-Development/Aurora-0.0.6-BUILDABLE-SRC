// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous;

import me.halqq.aurora.client.api.event.events.PacketEvent;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingString;
import me.halqq.aurora.client.api.util.utils.MessageUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.ConcurrentHashMap;


public class AutoEz extends Module {

    public AutoEz() {
        super("AutoEz", Category.MISCELLANEOUS);
    }

    SettingString message = create("Message", "ez noob, i use aurora client!");
    SettingBoolean all = create("All", false);

    ConcurrentHashMap targets = new ConcurrentHashMap();


    @SubscribeEvent
    public void onPacketSend(PacketEvent.PacketSendEvent event){
        if (event.getPacket() instanceof CPacketUseEntity) {

            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();

            if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (packet.getEntityFromWorld(mc.world) instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) packet.getEntityFromWorld(mc.world);

                    if (player.getHealth() <= 0) {
                        targets.put(player.getName(), player.getName());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void lightEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();

            if (targets.containsKey(player.getName())) {
                if (player.getHealth() <= 0) {
                    MessageUtil.sendSilentMessage(message.getValue());
                    targets.remove(player.getName());

                }
            }

            if (all.getValue()) {
                if (player.getHealth() <= 0) {
                    MessageUtil.sendSilentMessage(message.getValue());
                }
            }
        }
    }

}
