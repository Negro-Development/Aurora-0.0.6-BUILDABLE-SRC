// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.halqq.aurora.client.api.command.Command;
import me.halqq.aurora.client.api.command.CommandManager;
import me.halqq.aurora.client.api.util.utils.MessageUtil;


public class Prefix extends Command {

    public Prefix() {
        super("prefix", new String[]{"[prefix]"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            MessageUtil.sendMessage(Command.getCommandPrefix() + "prefix " + ChatFormatting.AQUA + "[prefix]");
            return;
        }

        CommandManager.INSTANCE.setPrefix(commands[0]);
        MessageUtil.sendMessage("Set command prefix to " + ChatFormatting.AQUA + commands[0]);
    }

}
