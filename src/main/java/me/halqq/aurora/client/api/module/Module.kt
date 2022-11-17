// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.module

import me.halqq.aurora.client.api.event.events.RenderEvent
import me.halqq.aurora.client.api.setting.SettingManager
import me.halqq.aurora.client.api.setting.settings.*
import me.halqq.aurora.client.api.util.MinecraftInstance
import me.halqq.aurora.client.api.util.Minecraftable
import net.minecraftforge.common.MinecraftForge
import org.lwjgl.input.Keyboard
import java.awt.Color

abstract class Module (val name: String, val category: Category) : MinecraftInstance() {

    var key = 0
    var isEnabled = false
        private set

    fun create(name: String, value: Boolean, visible: Boolean): SettingBoolean {
        val setting = SettingBoolean(name, this, value, visible)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String, value: Boolean): SettingBoolean {
        val setting = SettingBoolean(name, this, value, true)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, color: Color?, visible: Boolean): SettingColor {
        val setting = SettingColor(name, this, color!!, visible)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, color: Color?): SettingColor {
        val setting = SettingColor(name, this, color!!, true)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: Int, min: Int, max: Int, visible: Boolean): SettingInteger {
        val setting = SettingInteger(name, this, value, min, max, visible)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: Double, min: Double, max: Double, visible: Boolean): SettingDouble {
        val setting = SettingDouble(name, this, value, min, max, visible)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: Double, min: Double, max: Double): SettingDouble {
        val setting = SettingDouble(name, this, value, min, max, true)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: Int, min: Int, max: Int): SettingInteger {
        val setting = SettingInteger(name, this, value, min, max, true)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: String, modes: List<String>, visible: Boolean): SettingMode {
        val setting = SettingMode(name, this, value, modes, visible)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: String, modes: List<String>): SettingMode {
        val setting = SettingMode(name, this, value, modes, true)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: String, visible: Boolean): SettingString {
        val setting = SettingString(name, this, value, visible)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun create(name: String?, value: String): SettingString {
        val setting = SettingString(name, this, value, true)
        SettingManager.INSTANCE.settings.add(setting)
        return setting
    }

    fun fullNullCheck(): Boolean {
        return Minecraftable.mc.player == null || Minecraftable.mc.world == null
    }

    val keyName: String
        get() = if (key < -1) {
            "NONE"
        } else Keyboard.getKeyName(key)

    fun setEnabled() {
        if (!isEnabled) {
            isEnabled = true
            enable()
        }
    }

    fun setDisabled() {
        if (isEnabled) {
            isEnabled = false
            disable()
        }
    }

    fun toggle() {
        isEnabled = !isEnabled
        if (isEnabled) {
            enable()
        } else {
            disable()
        }
    }

    open fun onEnable() {}
    open fun onDisable() {}
    open fun onUpdate() {}
    fun onMotionUpdate() {}
    fun onPacketRecieve() {}
    fun onTick() {}
    open fun onRender3D(event: RenderEvent) {}
    open fun onRender2D() {}

    private fun enable() {
        MinecraftForge.EVENT_BUS.register(this)
        onEnable()
    }

    private fun disable() {
        MinecraftForge.EVENT_BUS.unregister(this)
        onDisable()
    }

    open fun onWorldRender(partialTicks: Float) {}
}