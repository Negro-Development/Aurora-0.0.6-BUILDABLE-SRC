// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.event.events.Render2DEvent;
import me.halqq.aurora.client.api.event.events.RenderEvent;
import me.halqq.aurora.client.api.gui.util.Render2DUtil;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import me.halqq.aurora.client.api.util.utils.BlockUtil;
import me.halqq.aurora.client.api.util.utils.ColorUtil;
import me.halqq.aurora.client.api.util.utils.RenderUtil;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.awt.*;


public class HoleHud extends Module {

    public HoleHud() {
        super("HoleHud", Category.HUD);
    }

    SettingInteger xx = create("X", 229, 0, 1000);
    SettingInteger yy = create("Y", 145, 0, 1000);
    SettingBoolean outline = create("Outline", true);
    SettingInteger outlineWidth = create("Outline Width", 1, 1, 5);
    SettingInteger outlineRed = create("Outline Red", 0, 0, 255);
    SettingInteger outlineGreen = create("Outline Green", 255, 0, 255);
    SettingInteger outlineBlue = create("Outline Blue", 255, 0, 255);
    SettingInteger outlineAlpha = create("Outline Alpha", 255, 0, 255);
    SettingBoolean rainbow = create("Rainbow", false);
    SettingBoolean background = create("Background", true);

    @Override
    public void onRender2D() {

        if (background.getValue()) {
            Render2DUtil.drawBorderedRect(xx.getValue(), yy.getValue(), 60, 54, 1, 0x80000000, 0x80000000);
        }

        if (outline.getValue()) {
            RenderUtil.drawLine(xx.getValue(), yy.getValue(), xx.getValue() + 60, yy.getValue(), outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());
            RenderUtil.drawLine(xx.getValue(), yy.getValue(), xx.getValue(), yy.getValue() + 54, outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());
            RenderUtil.drawLine(xx.getValue() + 60, yy.getValue(), xx.getValue() + 60, yy.getValue() + 54, outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());
            RenderUtil.drawLine(xx.getValue(), yy.getValue() + 54, xx.getValue() + 60, yy.getValue() + 54, outlineWidth.getValue(), new Color(outlineRed.getValue(), outlineGreen.getValue(), outlineBlue.getValue(), outlineAlpha.getValue()).getRGB());

            if (rainbow.getValue()) {
                Color color = new Color(ColorUtil.INSTANCE.getRainbow(400, 0, 0.5f, 1));

                outlineRed.setValue(color.getRed());
                outlineGreen.setValue(color.getGreen());
                outlineBlue.setValue(color.getBlue());
            }
        }

        BlockPos playerPos = new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));

        if (mc.world.getBlockState(new BlockPos(playerPos.getX() + 1, playerPos.getY(), playerPos.getZ())).getBlock() != Blocks.AIR) {
            RenderUtil.drawitem(mc.world.getBlockState(new BlockPos(playerPos.getX() + 1, playerPos.getY(), playerPos.getZ())).getBlock(), xx.getValue() + 22, yy.getValue() - 4);
        }

        if (mc.world.getBlockState(new BlockPos(playerPos.getX() - 1, playerPos.getY(), playerPos.getZ())).getBlock() != Blocks.AIR) {
            RenderUtil.drawitem(mc.world.getBlockState(new BlockPos(playerPos.getX() - 1, playerPos.getY(), playerPos.getZ())).getBlock(), xx.getValue() + 22, yy.getValue() + 40);
        }

        if (mc.world.getBlockState(new BlockPos(playerPos.getX(), playerPos.getY(), playerPos.getZ() - 1)).getBlock() != Blocks.AIR) {
            RenderUtil.drawitem(mc.world.getBlockState(new BlockPos(playerPos.getX(), playerPos.getY(), playerPos.getZ() - 1)).getBlock(), xx.getValue() + 2, yy.getValue() + 20);
        }

        if (mc.world.getBlockState(new BlockPos(playerPos.getX(), playerPos.getY(), playerPos.getZ() + 1)).getBlock() != Blocks.AIR) {
            RenderUtil.drawitem(mc.world.getBlockState(new BlockPos(playerPos.getX(), playerPos.getY(), playerPos.getZ() + 1)).getBlock(), xx.getValue() + 42, yy.getValue() + 20);
        }
    }
}