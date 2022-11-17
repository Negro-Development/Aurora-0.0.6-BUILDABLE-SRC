// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat;

import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import me.halqq.aurora.client.api.util.utils.TimerUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;


public class AutoArmor extends Module {

    public AutoArmor() {
        super("AutoArmor", Category.COMBAT);
    }

    SettingInteger delay = create("Delay", 1, 1, 10);
    TimerUtil timer = new TimerUtil();


    @Override
    public void onUpdate() {
        if (mc.player == null) return;

        if (timer.passedMs(delay.getValue())) {
            int slot = -1;
            int last = 0;

            for (int i = 0; i < 36; i++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(i);

                if (stack == ItemStack.EMPTY) {
                    slot = i;
                    break;
                }

                if (stack.getItem() instanceof net.minecraft.item.ItemArmor) {
                    int current = ((net.minecraft.item.ItemArmor) stack.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack);

                    if (current > last) {
                        last = current;
                        slot = i;
                    }
                }
            }

            if (slot != -1) {
                mc.playerController.windowClick(0, slot < 9 ? slot + 36 : slot, 0, ClickType.QUICK_MOVE, mc.player);
                timer.reset();
            }
        }

    }
}
