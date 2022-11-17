// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.injection.mixins;

import java.awt.Color;

import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.impl.modules.render.CrystalChams;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.*;

@Mixin(value={RenderEnderCrystal.class})
public abstract class MixinRenderEnderCrystal {
    @Shadow
    public ModelBase modelEnderCrystal;
    @Shadow
    public ModelBase modelEnderCrystalNoBase;

    @Final

    @Shadow
    public abstract void doRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9);

    @Redirect(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void render1(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
        if (!ModuleManager.INSTANCE.getModule(CrystalChams.class).isEnabled()) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }

    @Redirect(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal = 1))
    private void render2(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
        if (!ModuleManager.INSTANCE.getModule(CrystalChams.class).isEnabled()) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }

    @Inject(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = {@At(value = "RETURN")}, cancellable = true)
    public void IdoRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
        if (ModuleManager.INSTANCE.getModule(CrystalChams.class).isEnabled()) {
            Minecraft mc = Minecraft.getMinecraft();
            GL11.glPushMatrix();
            GlStateManager.translate((double) var2, (double) var4, (double) var6);

            float var13 = (float) var1.innerRotation + var9;

            float var14 = MathHelper.sin((float) (var13 * 0.2f)) / 2.0f + 0.5f;

            var14 += var14 * var14;

            GL11.glDisable(GL_LIGHTING);

            GL11.glScaled(CrystalChams.INSTANCE.getScale().getValue(), CrystalChams.INSTANCE.getScale().getValue(), CrystalChams.INSTANCE.getScale().getValue());

            switch (CrystalChams.INSTANCE.getRenderMode().getValue()) {

                case "Fill":


                    GL11.glEnable(GL11.GL_CULL_FACE);
                    GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);


                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                    GL11.glPolygonOffset(1.0f, -1100000.0f);

                    break;

                case "None":

                    GL11.glEnable(32823);
                    GL11.glPolygonOffset(1.0f, -1000000.0f);

                    GL11.glPolygonOffset(1.0f, 1000000.0f);
                    GL11.glDisable(32823);

                    break;

            }

            if (CrystalChams.INSTANCE.getOutline().getValue()) {

                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6913);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(3);
                GL11.glEnable(2960);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glEnable(10754);

                GL11.glColor4f(255, 0, 255, 255);

                if (var1.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity) var1, 0.0f, var13 * CrystalChams.INSTANCE.getSpeed().getValue().floatValue(), var14 * CrystalChams.INSTANCE.getBounce().getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.modelEnderCrystalNoBase.render((Entity) var1, 0.0f, var13 * CrystalChams.INSTANCE.getSpeed().getValue().floatValue(), var14 * CrystalChams.INSTANCE.getBounce().getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                }

                GL11.glEnable(2929);
                GL11.glDepthMask(true);

                if (var1.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity) var1, 0.0f, var13 * CrystalChams.INSTANCE.getSpeed().getValue().floatValue(), var14 * CrystalChams.INSTANCE.getBounce().getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.modelEnderCrystalNoBase.render((Entity) var1, 0.0f, var13 * CrystalChams.INSTANCE.getSpeed().getValue().floatValue(), var14 * CrystalChams.INSTANCE.getBounce().getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                }

                GL11.glEnable(3042);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glPopAttrib();

            }

            GL11.glColor4f(CrystalChams.INSTANCE.getR().getValue(), CrystalChams.INSTANCE.getG().getValue(), CrystalChams.INSTANCE.getBl().getValue(), CrystalChams.INSTANCE.getA().getValue());

            if (var1.shouldShowBottom()) {

                this.modelEnderCrystal.render((Entity) var1, 0.0f, var13 * CrystalChams.INSTANCE.getSpeed().getValue().floatValue(), var14 * CrystalChams.INSTANCE.getBounce().getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
            } else {

                this.modelEnderCrystalNoBase.render((Entity) var1, 0.0f, var13 * CrystalChams.INSTANCE.getSpeed().getValue().floatValue(), var14 * CrystalChams.INSTANCE.getBounce().getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
            }


            GL11.glEnable(GL_TEXTURE_2D);
            GL11.glEnable(GL_LIGHTING);

            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);

            GL11.glPopMatrix();
        }
    }
}