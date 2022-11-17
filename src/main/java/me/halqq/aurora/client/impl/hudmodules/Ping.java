// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;

import java.util.Objects;


public class Ping extends Module {

    public Ping() {
        super("Ping", Category.HUD);
    }

    SettingInteger xx = create("X", 0, 0, 1000);
    SettingInteger yy = create("Y", 50, 0, 1000);
    SettingBoolean shadow = create("Shadow", false);
    SettingBoolean custom = create("CustomFont", true);

    @Override
    public void onRender2D() {
        int pinger = -1;
        try {pinger = mc.player.connection.getPlayerInfo(mc.player.getUniqueID()).getResponseTime();} catch (NullPointerException ignored) {}
        TextManager.INSTANCE.drawString("Ping: " + pinger, xx.getValue(), yy.getValue(), -1, shadow.getValue(), custom.getValue());
    }

}
