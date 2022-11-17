// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.world

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect

class AddEfects : Module("AddEfects", Category.WORLD) {
    var nausea = create("nausea", false)
    var speed = create("Speed", false)
    var jump = create("LongJump", false)
    var invisiblity = create("invisiblity", false)
    var damage = create("Damage", false)
    var health = create("Health", false)
    var regeneration = create("regeneration", false)
    var fireResistence = create("FireResistence", false)
    var whiter = create("Wither", false)
    var slow = create("Slow", false)
    var luck = create("Luck", false)

    override fun onUpdate() {
        if (nausea.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.NAUSEA, 69, 1, true, true))
        }
        if (speed.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.SPEED, 69, 1, true, true))
        }
        if (jump.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.JUMP_BOOST, 69, 1, true, true))
        }
        if (invisiblity.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.INVISIBILITY, 69, 1, true, true))
        }
        if (damage.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.INSTANT_DAMAGE, 69, 1, true, true))
        }
        if (health.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.INSTANT_HEALTH, 69, 1, true, true))
        }
        if (regeneration.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.REGENERATION, 69, 1, true, true))
        }
        if (fireResistence.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.FIRE_RESISTANCE, 69, 1, true, true))
        }
        if (whiter.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.WITHER, 69, 1, true, true))
        }
        if (slow.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.SLOWNESS, 69, 1, true, true))
        }
        if (luck.value) {
            mc.player.addPotionEffect(PotionEffect(MobEffects.LUCK, 69, 1, true, true))
        }
    }

    override fun onDisable() {
        mc.player.removePotionEffect(MobEffects.NAUSEA)
        mc.player.removePotionEffect(MobEffects.SPEED)
        mc.player.removePotionEffect(MobEffects.JUMP_BOOST)
        mc.player.removePotionEffect(MobEffects.INVISIBILITY)
        mc.player.removePotionEffect(MobEffects.INSTANT_DAMAGE)
        mc.player.removePotionEffect(MobEffects.INSTANT_HEALTH)
        mc.player.removePotionEffect(MobEffects.REGENERATION)
        mc.player.removePotionEffect(MobEffects.FIRE_RESISTANCE)
        mc.player.removePotionEffect(MobEffects.WITHER)
        mc.player.removePotionEffect(MobEffects.SLOWNESS)
        mc.player.removePotionEffect(MobEffects.LUCK)
    }
}