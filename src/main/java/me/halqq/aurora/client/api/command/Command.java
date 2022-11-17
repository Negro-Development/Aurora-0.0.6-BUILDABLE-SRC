// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.command;

import me.halqq.aurora.client.api.util.Minecraftable;


abstract public class Command implements Minecraftable {

    private final String name;
    private final String[] commands;

    public Command(String name) {
        this.name = name;
        commands = new String[]{""};
    }

    public Command(String name, String[] commands) {
        this.name = name;
        this.commands = commands;
    }

    public static String getCommandPrefix() {
        return CommandManager.INSTANCE.getPrefix();
    }

    public abstract void execute(String[] var1);

    public String getName() {
        return name;
    }

    public String[] getCommands() {
        return commands;
    }

}