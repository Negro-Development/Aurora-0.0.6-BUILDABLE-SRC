// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.CustomFont;
import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.gui.util.Render2DUtil;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import me.halqq.aurora.client.api.util.utils.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;


public class ArrayList extends Module {

    public ArrayList() {
        super("ArrayList", Category.HUD);
    }

    SettingInteger xx = create("X", 684, 0, 1000);
    SettingInteger yy = create("Y", 3, 0, 1000);
    SettingBoolean shadow = create("Shadow", false);
    SettingBoolean custom = create("CustomFont", true);
    SettingBoolean background = create("Background", true);
    SettingBoolean outline = create("Outline", true);


    @Override
    public void onRender2D(){
        int modCount = 0;
        int idk = 1;
        for (Module module : ModuleManager.INSTANCE.getModules()) {
            if (!module.isEnabled()) continue;

            if(background.getValue()){
                Render2DUtil.drawBorderedRect(xx.getValue(), yy.getValue() + (modCount * 10 ) + 2, - (TextManager.INSTANCE.getStringWidth(module.getName()) + 4), TextManager.INSTANCE.getFontHeight() + 4, 1, 0x80000000, 0x80000000);
            }

            if(outline.getValue()){
                RenderUtil.drawLine(xx.getValue() - TextManager.INSTANCE.getStringWidth(module.getName()) + 4, yy.getValue() + (modCount * 10 ) + 2, xx.getValue() - TextManager.INSTANCE.getStringWidth(module.getName()) - 4, TextManager.INSTANCE.getFontHeight() + 4, 3, new Color(191, 0, 255, 255).getRGB());
            }

            TextManager.INSTANCE.drawString(module.getName(), xx.getValue() - TextManager.INSTANCE.getStringWidth(module.getName()) - 2, yy.getValue() + (modCount * 10 ) + 2, -1, shadow.getValue(), custom.getValue());
            idk -= TextManager.INSTANCE.getFontHeight() + 1;
            ++modCount;
        }
    }
}
