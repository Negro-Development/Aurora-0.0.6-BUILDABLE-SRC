// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;



public class ExpCount extends Module {

    public ExpCount() {
        super("BottleExpCounter", Category.HUD);
    }

    SettingInteger xx = create("X", 0, 0, 1000);
    SettingInteger yy = create("Y", 96, 0, 1000);
    SettingBoolean shadow = create("Shadow", false);
    SettingBoolean custom = create("CustomFont", true);


    @Override
    public void onRender2D() {
        int xp = mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.EXPERIENCE_BOTTLE).mapToInt(ItemStack::getCount).sum();
        TextManager.INSTANCE.drawString("BottleXP: " + xp, xx.getValue(), yy.getValue(), -1, shadow.getValue(), custom.getValue());
    }
}
