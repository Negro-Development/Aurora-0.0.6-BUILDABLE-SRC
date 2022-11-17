// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.commands;

import me.halqq.aurora.client.api.command.Command;
import me.halqq.aurora.client.api.config.ConfigManager;
import me.halqq.aurora.client.api.util.utils.MessageUtil;


public class Reload extends Command {

    public Reload() {
        super("reload");
    }

    @Override
    public void execute(String[] commands) {
        ConfigManager.INSTANCE.loadConfigs();
        MessageUtil.sendMessage("Reloaded config.");
    }

}
