// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.halqq.aurora.client.api.event.events.PacketEvent;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.util.utils.BlockUtil;
import me.halqq.aurora.client.api.util.utils.MessageUtil;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class HoleBreakAlert extends Module {

    public HoleBreakAlert() {
        super("HoleBreakAlert", Category.MISCELLANEOUS);
    }

   @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent event){
        if(event.getPacket() instanceof SPacketBlockBreakAnim){
            SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim) event.getPacket();
            if(BlockUtil.isHole(packet.getPosition())){
                MessageUtil.sendMessage(ChatFormatting.RED + "[HoleAlert]" + ChatFormatting.GRAY + " Someone is breaking your hole");
            }
        }
   }
}
