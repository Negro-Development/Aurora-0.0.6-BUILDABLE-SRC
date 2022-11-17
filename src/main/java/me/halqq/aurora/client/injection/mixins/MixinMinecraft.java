// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.injection.mixins;

import me.halqq.aurora.client.api.config.ConfigManager;
import me.halqq.aurora.client.impl.GuiCustomMainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;


@Mixin(value = {Minecraft.class})
public abstract class MixinMinecraft {

    @Shadow
    public abstract void displayGuiScreen(@Nullable GuiScreen var1);

    @Inject(method = "crashed", at = @At("HEAD"))
    public void crashed(CrashReport crash, CallbackInfo callbackInfo) {
        ConfigManager.INSTANCE.saveConfigs();
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo callbackInfo) {
        ConfigManager.INSTANCE.saveConfigs();
    }

    @Inject(method={"runTick()V"}, at={@At(value="RETURN")})
    private void runTick(CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiCustomMainScreen());
        }
    }

        @Inject(method={"displayGuiScreen"}, at={@At(value="HEAD")})
        private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
            if (screen instanceof GuiMainMenu) {
                this.displayGuiScreen(new GuiCustomMainScreen());
            }
        }
}