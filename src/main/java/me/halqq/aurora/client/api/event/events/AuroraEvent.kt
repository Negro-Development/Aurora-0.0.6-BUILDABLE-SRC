// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.event.events

import net.minecraftforge.fml.common.eventhandler.Event

open class AuroraEvent : Event {
    var stage: Stage? = null

    constructor() {}
    constructor(stage: Stage?) {
        this.stage = stage
    }

    @JvmName("getStage1")
    fun getStage(): Stage? {
        return stage
    }

    @JvmName("setStage1")
    fun setStage(stage: Stage?) {
        this.stage = stage
        isCanceled = false
    }

    enum class Stage {
        PRE, POST
    }
}