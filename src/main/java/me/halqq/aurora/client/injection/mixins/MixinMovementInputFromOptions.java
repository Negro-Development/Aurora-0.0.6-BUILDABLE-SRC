// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.injection.mixins;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.*;


@Mixin(value = MovementInputFromOptions.class, priority = 10000)
public abstract class MixinMovementInputFromOptions extends MovementInput {
    @Mutable
    @Shadow
    @Final
    private final GameSettings gameSettings;

    protected MixinMovementInputFromOptions(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }
}

