// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render;

import me.halqq.aurora.client.api.event.events.RenderEvent;
import me.halqq.aurora.client.api.font.CFont;
import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.gui.util.Render2DUtil;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingDouble;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import me.halqq.aurora.client.api.util.utils.RenderUtil;
import me.halqq.aurora.client.impl.modules.other.CustomFont;
import me.rina.turok.render.opengl.TurokRenderGL;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class Nametags extends Module {

    public Nametags() {
        super("Nametags", Category.RENDER);
    }

    SettingDouble y = create("Y", 2.8, 0.5, 5.0);
    SettingBoolean name = create("Name", true);
    SettingBoolean ping = create("Ping", true);
    SettingBoolean health = create("Health", true);
    SettingBoolean item = create("Item", true);
    SettingBoolean armor = create("Armor", true);
    SettingBoolean background = create("Background", true);
    SettingBoolean outline = create("Outline", true);
    SettingInteger outlineWidth = create("Outline Width", 2, 1, 6);
    SettingInteger outlinered = create("Outline Red", 0, 0, 255);
    SettingInteger outlinegreen = create("Outline Green", 255, 0, 255);
    SettingInteger outlineblue = create("Outline Blue", 0, 0, 255);
    SettingInteger outlinealpha = create("Outline Alpha", 255, 0, 255);


    @Override
    public void onRender3D(RenderEvent event) {
        for (EntityPlayer p : mc.world.playerEntities) {

            final double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * mc.timer.renderPartialTicks;
            final double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * mc.timer.renderPartialTicks;
            final double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * mc.timer.renderPartialTicks;

            Entity renderEntity = mc.getRenderManager().renderViewEntity;
            if (renderEntity == null) renderEntity = mc.player;
            if (renderEntity == null) return;

            final double rX = renderEntity.lastTickPosX + (renderEntity.posX - renderEntity.lastTickPosX) * mc.timer.renderPartialTicks;
            final double rY = renderEntity.lastTickPosY + (renderEntity.posY - renderEntity.lastTickPosY) * mc.timer.renderPartialTicks;
            final double rZ = renderEntity.lastTickPosZ + (renderEntity.posZ - renderEntity.lastTickPosZ) * mc.timer.renderPartialTicks;

            this.renderNametag(p, pX - rX, pY - rY + y.getValue(), pZ - rZ);
        }
    }

    public void renderNametag(EntityPlayer p, final double x, final double y, final double z) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-0.025F, -0.025F, 0.025F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        final int widthname = TextManager.INSTANCE.getStringWidth(p.getName()) / 2;

        final int width = TextManager.INSTANCE.getStringWidth(p.getName() + "        ") / 2;

        if (background.getValue()) {
            RenderUtil.drawSmoothRect((float)(-width - 8), 9.0f, (float)(width + 9), 30.0f, new Color(0, 0, 0, 128).getRGB());
        }

        if(outline.getValue()) {
            RenderUtil.drawLine((float)(-width - 8), 9.0f, (float)(width + 9), 9.0f, outlineWidth.getValue().floatValue(), new Color(outlinered.getValue(), outlinegreen.getValue(), outlineblue.getValue(), outlinealpha.getValue()).getRGB());
            RenderUtil.drawLine((float)(-width - 8), 30.0f, (float)(width + 9), 30.0f, outlineWidth.getValue().floatValue(), new Color(outlinered.getValue(), outlinegreen.getValue(), outlineblue.getValue(), outlinealpha.getValue()).getRGB());
        }

        if (name.getValue()) {
            TextManager.INSTANCE.drawStringsize(p.getName(), -width, 7 + 7 - (TextManager.INSTANCE.getFontHeight() - 8), -1, false, true);
        }

        if (health.getValue()) {
            TextManager.INSTANCE.drawStringsize("Hp: " + MathHelper.ceil(p.getHealth() + p.getAbsorptionAmount()), -widthname + 20, 7 + 7 - (TextManager.INSTANCE.getFontHeight() - 8), -1, false, true);
        }

        if(ping.getValue()){
            int healthwidth = TextManager.INSTANCE.getStringWidth("Hp: " + MathHelper.ceil(p.getHealth() + p.getAbsorptionAmount()) / 2);
            int pinger = -1;
            try {pinger = mc.player.connection.getPlayerInfo(p.getUniqueID()).getResponseTime();} catch (NullPointerException ignored) {}
            TextManager.INSTANCE.drawStringsize("Ms: " + pinger, -healthwidth + 35, 7 + 7 - (TextManager.INSTANCE.getFontHeight() - 8), -1, false, true);
        }

        if (item.getValue()){
            ItemStack mainhand = p.getHeldItemMainhand();
            ItemStack offhand = p.getHeldItemOffhand();

            drawitem(mainhand, -width, 10 + 7 - (TextManager.INSTANCE.getFontHeight() - 8));
            if(mainhand == null){
                TextManager.INSTANCE.drawStringsize(mainhand.getCount() + "", -width + 1, 12 + 7 - (TextManager.INSTANCE.getFontHeight() - 8), -1, false, true);
            }
            drawitem(offhand, -width + 20, 10 + 7 - (TextManager.INSTANCE.getFontHeight() - 8));
            if(offhand == null){
                TextManager.INSTANCE.drawStringsize(offhand.getCount() + "", -width + 12, 12 + 7 - (TextManager.INSTANCE.getFontHeight() - 8), -1, false, true);
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 255.0F);
        GL11.glPopMatrix();
    }

    public void drawitem(ItemStack item, int x, int y) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().zLevel = -150.0F;
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, item, x, y);
        mc.getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

}
