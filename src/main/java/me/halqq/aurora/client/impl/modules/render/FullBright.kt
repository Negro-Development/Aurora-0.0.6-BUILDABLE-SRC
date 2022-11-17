// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import java.util.*

class FullBright : Module("FullBright", Category.RENDER) {

    var mode = create("Mode", "Setting", Arrays.asList("Setting", "Potion"))
    private var save = 0f
    override fun onEnable() {
        save = mc.gameSettings.gammaSetting
    }

    override fun onUpdate() {
        if (mode.value.equals("Setting", ignoreCase = true)) {
            mc.gameSettings.gammaSetting = 1000f
        } else if (mode.value.equals("Potion", ignoreCase = true)) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.NIGHT_VISION))
        }
    }

    override fun onDisable() {
        mc.gameSettings.gammaSetting = save
        mc.player.removePotionEffect(MobEffects.NIGHT_VISION)
    }
}