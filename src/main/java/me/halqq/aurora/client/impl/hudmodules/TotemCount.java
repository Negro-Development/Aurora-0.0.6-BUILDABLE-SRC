// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class TotemCount extends Module {

    public TotemCount() {
        super("TotemCount", Category.HUD);
    }

    SettingInteger xx = create("X", 0, 0, 1000);
    SettingInteger yy = create("Y", 36, 0, 1000);

    @Override
    public void onRender2D() {
        int totems = mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();

        TextManager.INSTANCE.drawString("Totems: " + totems, xx.getValue(), yy.getValue(), -1, false, true);
    }
}
