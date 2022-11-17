// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render.playerchams

import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import java.awt.Color
import java.util.*

class PlayerChams : Module("PlayerChams", Category.RENDER) {

    var settingRenderMode = this.create("Mode", "Fill", Arrays.asList("Solid", "Fill"))
    var width = this.create("Widht", 1, 0, 10)
    var r = this.create("Red", 255, 0, 225)
    var g = this.create("Green", 0, 0, 225)
    var bl = this.create("Blue", 255, 0, 225)
    var a = this.create("Alpha", 170, 0, 225)
    var color = Color(r.value / 255, g.value / 255, bl.value / 255, a.value / 255)


    companion object{
        lateinit var INSTANCE : PlayerChams
    }

}