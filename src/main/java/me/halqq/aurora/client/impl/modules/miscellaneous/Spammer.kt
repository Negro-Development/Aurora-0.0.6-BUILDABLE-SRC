// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.miscellaneous

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.util.utils.TimerUtil

class Spammer : Module("Spammer", Category.MISCELLANEOUS) {
    var message = create("Message", "Aurora spammer by like")
    var delay = create("Delay", 2, 1, 100)
    var catboss = create("CatBossMode", false)
    var timer = TimerUtil()
    override fun onUpdate() {
        if (mc.player == null || mc.world == null) {
            return
        }
        if (timer.passedMs((delay.value * 1000).toLong())) {
            if (catboss.getValue()) {
                mc.player.sendChatMessage("  ██████████████████████████████████████▒▒▒█▒▒▒█▒▒▒██▒▒███▒███▒▒██▒▒███████▒███▒█▒██▒███▒█▒█▒█▒█▒███▒█████████▒███▒▒▒██▒███▒▒██▒█▒██▒▒██▒▒███████▒███▒█▒██▒███▒█▒█▒█▒████▒███▒██████▒▒▒█▒█▒██▒███▒▒███▒███▒▒██▒▒███████████████████████████████████████")
                timer.reset()
            } else {
                mc.player.sendChatMessage(message.value)
                timer.reset()
            }
        }
    }
}