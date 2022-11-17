// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.injection.mixins;

import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.impl.modules.render.playerchams.ChamsUtil;
import me.halqq.aurora.client.impl.modules.render.playerchams.PlayerChams;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(value = {RenderLivingBase.class}, priority = 999)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
    @Shadow
    protected ModelBase mainModel;

    protected MixinRendererLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = {"renderModel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V")}, cancellable = true)
    private void renderModel(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo info) {
        if (ModuleManager.INSTANCE.getModule(PlayerChams.class).isEnabled()) {
            entityLivingBase.hurtTime = 0;
            if (entityLivingBase instanceof EntityPlayer) {
                if (PlayerChams.INSTANCE.getSettingRenderMode().getValue().equals("Fill")) {
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(1048575);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    GL11.glDisable(3553);
                    ChamsUtil.co(new Color((float) PlayerChams.INSTANCE.getR().getValue() / 255.0f, (float) PlayerChams.INSTANCE.getG().getValue() / 255.0f, (float) PlayerChams.INSTANCE.getBl().getValue().intValue() / 255.0f, (float) PlayerChams.INSTANCE.getA().getValue().intValue() / 255.0f));
                    this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(2896);
                    GL11.glEnable(2884);
                    GL11.glEnable(32823);
                    GL11.glPolygonMode(1032, 6914);
                    GL11.glPolygonOffset(1.0f, -1100000.0f);
                    GL11.glDisable(3553);
                    ChamsUtil.co(new Color((float) PlayerChams.INSTANCE.getR().getValue() / 255.0f, (float) PlayerChams.INSTANCE.getG().getValue() / 255.0f, (float) PlayerChams.INSTANCE.getBl().getValue().intValue() / 255.0f, (float) PlayerChams.INSTANCE.getA().getValue().intValue() / 255.0f));
                    this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();

                } else if (PlayerChams.INSTANCE.getSettingRenderMode().getValue().equals("Solid")) {
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(1048575);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    GL11.glDisable(3553);
                    ChamsUtil.co(new Color((float) PlayerChams.INSTANCE.getR().getValue() / 255.0f, (float) PlayerChams.INSTANCE.getG().getValue().intValue() / 255.0f, (float) PlayerChams.INSTANCE.getBl().getValue().intValue() / 255.0f, (float) PlayerChams.INSTANCE.getA().getValue().intValue() / 255.0f));
                    this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                }
            }
        }
    }
}