// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.halqq.aurora.client.api.command.Command;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.api.util.utils.MessageUtil;


public class Toggle extends Command {

    public Toggle() {
        super("toggle", new String[]{"[module]"});
    }

    @Override
    public void execute(String[] commands) {

        if (commands.length == 1) {
            MessageUtil.sendMessage(Command.getCommandPrefix() + "toggle " + ChatFormatting.AQUA + "[module]");
            return;
        }

        Module module = ModuleManager.INSTANCE.getModule(commands[0]);

        if (module == null) {
            MessageUtil.sendMessage("That module does not exist.");
            return;
        }
        module.toggle();
    }
}