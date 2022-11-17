// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.impl.Aurora;


public class WaterMark extends Module {

    public WaterMark() {
        super("WaterMark", Category.HUD);
    }

    SettingBoolean shadow = create("Shadow", false);
    SettingBoolean custom = create("CustomFont", true);

    @Override
    public void onRender2D() {
        TextManager.INSTANCE.drawString("Aurora " + Aurora.VERSION, 2, 2, -1, shadow.getValue(), custom.getValue());
    }
}
