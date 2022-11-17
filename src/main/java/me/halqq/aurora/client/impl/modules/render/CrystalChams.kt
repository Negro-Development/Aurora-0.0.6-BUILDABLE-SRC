// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.impl.modules.render.popchams.ModulePopChams
import java.util.*

 class CrystalChams : Module("CrystalChams", Category.RENDER) {

        var renderMode = create("RenderMode", "Fill", Arrays.asList("Fill", "None"))
        var r = this.create("Red", 0, 0, 225)
        var g = this.create("Green", 255, 0, 225)
        var bl = this.create("Blue", 255, 0, 225)
        var a = this.create("Alpha", 170, 0, 225)
        var speed = create("Speed", 3, 0, 50)
        var bounce = create("Bounce", 1, 0, 10)
        var scale = create("Scale", 1.0, 0.1, 3.0)
        var outline = create("Outline", true)

        init {
                INSTANCE = this
        }

        companion object {
                lateinit var INSTANCE: CrystalChams
        }

}