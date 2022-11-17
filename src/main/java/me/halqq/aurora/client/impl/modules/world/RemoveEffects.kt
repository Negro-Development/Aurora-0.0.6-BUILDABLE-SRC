// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.init.MobEffects

class RemoveEffects : Module("RemoveEffects", Category.WORLD) {

    override fun onUpdate() {

        mc.player.isPotionActive(MobEffects.LEVITATION)
        mc.player.removePotionEffect(MobEffects.LEVITATION)
        mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE)
        mc.player.removePotionEffect(MobEffects.FIRE_RESISTANCE)
        mc.player.isPotionActive(MobEffects.LUCK)
        mc.player.removePotionEffect(MobEffects.LUCK)
        mc.player.isPotionActive(MobEffects.SLOWNESS)
        mc.player.removePotionEffect(MobEffects.SLOWNESS)
        mc.player.isPotionActive(MobEffects.WITHER)
        mc.player.removePotionEffect(MobEffects.WITHER)
        mc.player.isPotionActive(MobEffects.REGENERATION)
        mc.player.removePotionEffect(MobEffects.REGENERATION)
        mc.player.isPotionActive(MobEffects.INSTANT_HEALTH)
        mc.player.removePotionEffect(MobEffects.INSTANT_HEALTH)
        mc.player.isPotionActive(MobEffects.INSTANT_DAMAGE)
        mc.player.removePotionEffect(MobEffects.INSTANT_DAMAGE)
        mc.player.isPotionActive(MobEffects.JUMP_BOOST)
        mc.player.removePotionEffect(MobEffects.JUMP_BOOST)
        mc.player.isPotionActive(MobEffects.SPEED)
        mc.player.removePotionEffect(MobEffects.SPEED)
        mc.player.isPotionActive(MobEffects.NIGHT_VISION)
        mc.player.removePotionEffect(MobEffects.NIGHT_VISION)
        mc.player.isPotionActive(MobEffects.ABSORPTION)
        mc.player.removePotionEffect(MobEffects.ABSORPTION)
        mc.player.isPotionActive(MobEffects.BLINDNESS)
        mc.player.removePotionEffect(MobEffects.BLINDNESS)
        mc.player.isPotionActive(MobEffects.GLOWING)
        mc.player.removePotionEffect(MobEffects.GLOWING)
        mc.player.isPotionActive(MobEffects.INVISIBILITY)
        mc.player.removePotionEffect(MobEffects.INVISIBILITY)
        mc.player.isPotionActive(MobEffects.HASTE)
        mc.player.removePotionEffect(MobEffects.HASTE)
        mc.player.isPotionActive(MobEffects.HUNGER)
        mc.player.removePotionEffect(MobEffects.HUNGER)
        mc.player.isPotionActive(MobEffects.POISON)
        mc.player.removePotionEffect(MobEffects.POISON)
        mc.player.isPotionActive(MobEffects.UNLUCK)
        mc.player.removePotionEffect(MobEffects.UNLUCK)
        mc.player.isPotionActive(MobEffects.WEAKNESS)
        mc.player.removePotionEffect(MobEffects.WEAKNESS)
    }
}