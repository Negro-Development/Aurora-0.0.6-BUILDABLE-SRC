// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.module


enum class Category(name: String?) {
    COMBAT("Combat"),
    EXPLOITS("Exploits"),
    MISCELLANEOUS("Miscellaneous"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    WORLD("World"),
    OTHER("Client"),
    HUD("HUD");

    var namee = name

        fun getName() : String ? {
            return namee
    }

}