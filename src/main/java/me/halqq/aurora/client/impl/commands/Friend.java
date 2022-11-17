// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.commands;

import me.halqq.aurora.client.api.command.Command;
import me.halqq.aurora.client.api.friend.FriendManager;
import me.halqq.aurora.client.api.util.utils.MessageUtil;

public class Friend extends Command {

    public Friend() {
        super("friend");
    }

    @Override
    public void execute(String[] commands) {

        String key = commands[0];
        String friende = commands[1];

            if (key.contains("add")) {
                FriendManager.addfriend(friende);
                MessageUtil.sendMessage("Added friend " + friende);

            } else if (key.contains("remove")) {
                FriendManager.removeFriend(friende);
                MessageUtil.sendMessage("Removed friend " + friende);

            } else if (key.contains("list")) {
                MessageUtil.sendMessage("Friends: " + FriendManager.INSTANCE.getFriendname());

        }

        if(!(key.contains("list") || key.contains("add") || key.contains("remove") || key.contains("abvvs"))){
            MessageUtil.sendMessage("Invalid command, use : friend < add | remove | list > < friend >");
        }
    }
}