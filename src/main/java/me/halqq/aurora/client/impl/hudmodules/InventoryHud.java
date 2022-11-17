// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.gui.util.Render2DUtil;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import me.halqq.aurora.client.api.util.utils.ColorUtil;
import me.halqq.aurora.client.api.util.utils.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class InventoryHud extends Module {

    public InventoryHud() {
        super("InventoryHud", Category.HUD);
    }

    SettingInteger xx = create("X", 277, 0, 1000);
    SettingInteger yy = create("Y", 0, 0, 1000);
    SettingBoolean background = create("Background", true);
    SettingBoolean outline = create("Outline", true);
    SettingInteger outlineWidth = create("Outline Width", 1, 1, 10);
    SettingInteger outlineRed = create("Outline Red", 0, 0, 255);
    SettingInteger outlineGreen = create("Outline Green", 255, 0, 255);
    SettingInteger outlineBlue = create("Outline Blue", 0, 0, 255);
    SettingInteger outlineAlpha = create("Outline Alpha", 255, 0, 255);
    SettingBoolean rainbow = create("Rainbow", false);

    @Override
    public void onRender2D() {
        GL11.glPushMatrix();

        RenderHelper.enableGUIStandardItemLighting();
        if(background.getValue()) {
            Render2DUtil.drawBorderedRect(xx.getValue(), yy.getValue(), 162, 54, 1, 0x80000000, 0x80000000);
        }

        if(outline.getValue()){
            RenderUtil.drawLine(xx.getValue(), yy.getValue(), xx.getValue() + 162, yy.getValue(), outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());
            RenderUtil.drawLine(xx.getValue(), yy.getValue(), xx.getValue(), yy.getValue() + 54, outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());
            RenderUtil.drawLine(xx.getValue() + 162, yy.getValue(), xx.getValue() + 162, yy.getValue() + 54, outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());
            RenderUtil.drawLine(xx.getValue(), yy.getValue() + 54, xx.getValue() + 162, yy.getValue() + 54, outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());

            if(rainbow.getValue()){
                Color color = new Color(ColorUtil.INSTANCE.getRainbow(400, 0, 0.5f, 1));

                outlineRed.setValue(color.getRed());
                outlineGreen.setValue(color.getGreen());
                outlineBlue.setValue(color.getBlue());
            }

        }

        for (int i = 9; i < 36; i++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            int x = xx.getValue() + (i - 9) % 9 * 18 + 1;
            int y = yy.getValue() + (i - 9) / 9 * 18 + 1;
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, x, y, null);
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }
}
