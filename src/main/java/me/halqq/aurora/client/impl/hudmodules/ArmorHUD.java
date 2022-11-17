// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.impl.modules.other.CustomFont;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import java.awt.*;


public class ArmorHUD extends Module {

    public ArmorHUD() {
        super("Armor", Category.HUD);
    }

    public static RenderItem itemRender = mc.getRenderItem();

    @Override
    public void onRender2D() {
        ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.enableTexture2D();
        int i = sr.getScaledWidth() / 2;
        int iteration = 0;
        int y = sr.getScaledHeight() - 55 - (mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure() ? 10 : 0);
        for (ItemStack is : mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
            itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            TextManager.INSTANCE.drawStringWithShadow(s, x + 19 - 2 -  TextManager.INSTANCE.getStringWidth(s), y + 9, 0xFFFFFF,  ModuleManager.INSTANCE.getModule(CustomFont.class).isEnabled());
            int dmg = 0;
            int itemDurability = is.getMaxDamage() - is.getItemDamage();
            float green = ((float)is.getMaxDamage() - (float)is.getItemDamage()) / (float)is.getMaxDamage();
            float red = 1.0f - green;
            dmg = 100 - (int)(red * 100.0f);
            TextManager.INSTANCE.drawStringWithShadow(dmg + "", x + 8 -  TextManager.INSTANCE.getStringWidth(dmg + "") / 2, y - 11, new Color((int)(red * 255.0f), (int)(green * 255.0f), 0).getRGB(),  ModuleManager.INSTANCE.getModule(CustomFont.class).isEnabled());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
}
