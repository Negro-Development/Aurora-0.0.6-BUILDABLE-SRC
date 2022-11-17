// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous;

import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingColor;
import me.halqq.aurora.client.impl.modules.other.CustomFont;

import java.awt.*;

public class BetterChat extends Module {
    public BetterChat(){super("BetterChat",  Category.MISCELLANEOUS);}

    public SettingBoolean font = create("Font", true);
    public SettingBoolean back = create("BackGround", true);


    @Override
    public void onUpdate(){
if (Boolean.TRUE.equals(font.getValue())){
    ModuleManager.INSTANCE.getModule(CustomFont.class).setEnabled();
}
    }

}
