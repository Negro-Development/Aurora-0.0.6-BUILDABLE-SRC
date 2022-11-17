// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.injection.mixins;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.api.util.Minecraftable;
import me.halqq.aurora.client.impl.modules.miscellaneous.BetterChat;
import me.halqq.aurora.client.impl.modules.other.Colors;
import me.halqq.aurora.client.impl.modules.other.CustomFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(value = {GuiNewChat.class})
public class MixinGuiNewChat extends Gui {

    @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
        final Minecraft minecraft = Minecraft.getMinecraft();

        if (ModuleManager.INSTANCE.getModule(BetterChat.class).isEnabled() && ModuleManager.INSTANCE.getModule(BetterChat.class).font.getValue()) {
            TextManager.INSTANCE.drawStringWithShadow(text, x, y, -1, ModuleManager.INSTANCE.getModule(CustomFont.class).isEnabled());
        }
        else {
            minecraft.fontRenderer.drawStringWithShadow(text, x, y, color);
            }
            return 0;
        }
    @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void drawBackground(int left, int top, int right, int bottom, int color) {
        if (ModuleManager.INSTANCE.getModule(BetterChat.class).isEnabled() && ModuleManager.INSTANCE.getModule(BetterChat.class).back.getValue()) {
            return;
        }
        Gui.drawRect(left, top, right, bottom, color);
    }
    }