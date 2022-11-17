// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.commands;

import me.halqq.aurora.client.api.command.Command;
import me.halqq.aurora.client.api.config.ConfigManager;
import me.halqq.aurora.client.api.util.utils.MessageUtil;


public class Save extends Command {

    public Save() {
        super("save");
    }

    @Override
    public void execute(String[] commands) {
        ConfigManager.INSTANCE.saveConfigs();
        MessageUtil.sendMessage("Saved config.");
    }
}
