// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.injection.mixins;


import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.impl.modules.render.ItemEsp;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEntityItem.class)
public class MixinRenderEntityItem {
    @Inject(method = "doRender", at = @At("HEAD"))
    private void injectChamsPre(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (ModuleManager.INSTANCE.getModule(ItemEsp.class).isEnabled()) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1000000.0f);
        }
    }

    @Inject(method = "doRender", at = @At("RETURN"))
    private <S extends EntityLivingBase> void injectChamsPost(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (ModuleManager.INSTANCE.getModule(ItemEsp.class).isEnabled()) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }

}
