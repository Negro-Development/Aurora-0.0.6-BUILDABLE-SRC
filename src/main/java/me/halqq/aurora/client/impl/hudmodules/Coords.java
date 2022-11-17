package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;


public class Coords extends Module {

    public Coords() {
        super("Coords", Category.HUD);
    }

    SettingInteger xx = create("X", 0, 0, 1000);
    SettingInteger yy = create("Y", 337, 0, 1000);
    SettingBoolean shadow = create("Shadow", false);
    SettingBoolean custom = create("CustomFont", true);


    @Override
    public void onRender2D() {
        String position = "null";
        double x = mc.player.posX, y = mc.player.posY, z = mc.player.posZ;
        if (mc.player.dimension == -1) {
            position = String.format("%.2f, %.2f, %.2f [%.2f, %.2f]", x, y, z, x * 8, z * 8);
        } else if (mc.player.dimension == 0) {
            position = String.format("%.2f, %.2f, %.2f [%.2f, %.2f]", x, y, z, x / 8, z / 8);
        } else {
            position = String.format("%.2f, %.2f, %.2f", x, y, z);
        }

        TextManager.INSTANCE.drawString("XYZ: " + position, xx.getValue(), yy.getValue(), -1, shadow.getValue(), custom.getValue());
    }
}
