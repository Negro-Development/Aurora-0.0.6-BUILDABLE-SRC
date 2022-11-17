// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.TimerUtil
import java.util.*

class AntiAfk

    : Module("AntiAfk", Category.MISCELLANEOUS) {
    var delay = create("Delay", 10, 3, 100)
    var chatDelay = create("ChatDelay", 10, 3, 100)
    var jump = create("Jump", true)
    var walk = create("Walk", true)
    var chat = create("Chat", false)
    var attack = create("Attack", true)
    var rotate = create("Rotate", false)
    var timer = TimerUtil()
    var random = Random()
    override fun onUpdate() {
        if (jump.value && mc.player.onGround && delay()) {
            mc.player.jump()
        }
        if (chat.value && timer.passedMs((chatDelay.value * 1000).toLong())) {
            mc.player.sendChatMessage("Im using Aurora Anti Afk!!!!")
            timer.reset()
        }
        if (attack.value && delay()) {
            mc.gameSettings.keyBindAttack.pressTime = delay.value
        }
        if (walk.value && delay()) {
            mc.gameSettings.keyBindBack.pressed = mc.player.ticksExisted % 15 == 0
            mc.gameSettings.keyBindLeft.pressed = mc.player.ticksExisted % 10 == 0
            mc.gameSettings.keyBindRight.pressed = mc.player.ticksExisted % 7 == 0
            mc.gameSettings.keyBindForward.pressed = mc.player.ticksExisted % 13 == 0
        }
        if (rotate.value && delay()) {
            mc.player.rotationYaw = (random.nextInt(90) - 90).toFloat()
            mc.player.rotationYaw = (random.nextInt(30) - 30).toFloat()
            mc.player.rotationYaw = (random.nextInt(360) - 360).toFloat()
        }
    }

    fun delay(): Boolean {
        return mc.player.ticksExisted % delay.value == 0
    }
}