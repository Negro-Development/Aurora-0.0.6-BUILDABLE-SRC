// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.injection.mixins;

import me.halqq.aurora.client.api.event.events.AuroraEvent;
import me.halqq.aurora.client.api.event.events.PlayerDamageBlockEvent;
import me.halqq.aurora.client.impl.Aurora;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    public void onOnPlayerDamageBlock(BlockPos position, EnumFacing facing, CallbackInfoReturnable<Boolean> cir) {
        final AuroraEvent event = new PlayerDamageBlockEvent(position, facing, AuroraEvent.Stage.PRE);

        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCancelable()) {
            cir.cancel();
        }
    }
}