// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.commands;

import me.halqq.aurora.client.api.command.Command;
import me.halqq.aurora.client.api.command.CommandManager;
import me.halqq.aurora.client.api.util.utils.MessageUtil;


public class Help extends Command {

    public Help() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {

        MessageUtil.sendMessage("Available commands:");

        for (Command command : CommandManager.INSTANCE.getCommands()) {
            MessageUtil.sendMessage(Command.getCommandPrefix() + command.getName());
        }

    }
}
