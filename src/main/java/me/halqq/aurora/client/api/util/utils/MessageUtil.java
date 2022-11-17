// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.util.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.halqq.aurora.client.api.command.CommandManager;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.api.util.Minecraftable;
import me.halqq.aurora.client.impl.modules.other.Colors;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageUtil implements Minecraftable {

    private String name;
    private String[] commands;

    public static String getWatermark() {
        return "\u00a7+" + CommandManager.INSTANCE.getClientMessage();
    }

    public static void toggleMessage(Module module) {
        if (module.getName().equals("ClickGui") || module.getName().equals("HUD")) return;

        if (module.isEnabled()) {
            sendOverwriteMessage("<" + ChatFormatting.DARK_PURPLE + "Aurora" + ChatFormatting.GRAY + "> " + ChatFormatting.GRAY + module.getName() + " toggled " + ChatFormatting.GREEN + "on" + ChatFormatting.GRAY + ".", 7183);
        }
        else{
            sendOverwriteMessage("<" +  ChatFormatting.DARK_PURPLE + "Aurora" + ChatFormatting.GRAY + "> " +  ChatFormatting.GRAY + module.getName() + " toggled " + ChatFormatting.RED + "off" + ChatFormatting.GRAY + ".", 7183);
        }

    }

    public static void sendClientMessage(String message) {
        if (mc.player != null) {
            final ITextComponent itc = new TextComponentString(message).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Easter egg"))));
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 6937);
        }
    }

    public static void sendMessage(String message) {
        sendSilentMessage("<" + getWatermark() + "> " + ChatFormatting.RESET + ChatFormatting.GRAY + message);
    }

    public static void sendSilentMessage(String message) {
        if (mc.player == null) return;
        mc.player.sendMessage(new ChatMessage(message));
    }

    public static void sendOverwriteMessage(String message, int id) {
        TextComponentString component = new TextComponentString(message);
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(component, id);
    }

    public static void sendRainbowMessage(String message) {
        StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.insert(0, "\u00a7+");
        mc.player.sendMessage(new ChatMessage(stringBuilder.toString()));
    }

    public String getName() {
        return name;
    }

    public String[] getCommands() {
        return commands;
    }

    public static class ChatMessage extends TextComponentBase {
        private final String text;

        public ChatMessage(String text) {
            Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
            Matcher matcher = pattern.matcher(text);
            StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                String replacement = "\u00a7" + matcher.group().substring(1);
                matcher.appendReplacement(stringBuffer, replacement);
            }
            matcher.appendTail(stringBuffer);
            this.text = stringBuffer.toString();
        }

        public String getUnformattedComponentText() {
            return text;
        }

        public ITextComponent createCopy() {
            return new ChatMessage(text);
        }
    }

}
