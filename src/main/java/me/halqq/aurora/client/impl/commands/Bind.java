// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.halqq.aurora.client.api.command.Command;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.api.util.utils.MessageUtil;
import org.lwjgl.input.Keyboard;


public class Bind extends Command {

    public Bind() {
        super("bind", new String[]{"[module]", "[key]"});
    }

    @Override
    public void execute(String[] commands) {

        if (commands.length == 1) {
            MessageUtil.sendMessage(getCommandPrefix() + "bind " + ChatFormatting.AQUA + "[module] [key]");
            return;
        }

        String rkey = commands[1];
        String moduleName = commands[0];
        Module module = ModuleManager.INSTANCE.getModule(moduleName);

        if (module == null) {
            MessageUtil.sendMessage("That module does not exist.");
            return;
        }

        if (rkey == null) {
            MessageUtil.sendMessage(getCommandPrefix() + "bind " + ChatFormatting.AQUA + "[module] [key]");
            return;
        }

        int key = Keyboard.getKeyIndex(rkey.toUpperCase());

        if (rkey.equalsIgnoreCase("none")) {
            key = 0;
        }

        if (key == 0) {
            module.setKey(key);
            MessageUtil.sendMessage(module.getName() + " keybind has been set to NONE.");
            return;
        }

        module.setKey(key);
        MessageUtil.sendMessage(module.getName() + " keybind has been set to " + rkey.toUpperCase() + ".");
    }

}
