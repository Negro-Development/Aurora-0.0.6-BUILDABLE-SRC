// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import net.minecraft.client.resources.I18n;

import java.text.DecimalFormat;


public class PotionHud extends Module {

    public PotionHud() {
        super("PotionHud", Category.HUD);
    }

    int ix;

    SettingInteger xx = create("X", 0, 0, 1000);
    SettingInteger yy = create("Y", 253, 0, 1000);
    SettingBoolean shadow = create("Shadow", false);
    SettingBoolean custom = create("CustomFont", true);

    @Override
    public void onRender2D() {
        ix = -1;
        DecimalFormat format2 = new DecimalFormat("00");

        mc.player.getActivePotionEffects().forEach(effect -> {
            String name = I18n.format(effect.getPotion().getName());
            double duration = effect.getDuration() / 19.99f;
            int amplifier = effect.getAmplifier() + 1;
            double p1 = duration % 60f;
            String seconds = format2.format(p1);
            String s = name + " " + amplifier + ChatFormatting.GRAY + " " + (int) duration / 60 + ":" + seconds;


            TextManager.INSTANCE.drawStringWithShadow(s, xx.getValue(), yy.getValue() + (ix * 10), -1);
                ix++;
            });
        }
    }
