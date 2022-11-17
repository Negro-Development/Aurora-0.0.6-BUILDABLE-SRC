// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.client

import com.mojang.realmsclient.gui.ChatFormatting
import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.gui.util.Render2DUtil
import me.halqq.aurora.client.api.module.Category
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.impl.Aurora
import me.halqq.aurora.client.impl.modules.other.CustomFont
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.resources.I18n
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.text.DecimalFormat


class HUD : Module("Hud", Category.OTHER) {

    var mark = create("Watermark", true)
    var welcomer = create("Welcomer", true)
    var fps = create("FPS", true)
    var ping = create("Ping", true)
    var tps = create("TPS", true)
    var arraylist = create("List", true)
    var coords = create("Coordinates", true)
    var armor = create("Armor", true)
    var offset = create("Offset", 1, 0, 5)
    var potion = create("Potion", true)
    var array = create("ArrayList", true)
    var invetoryPreview = create("Inventory", true)
    var intevetoryX = create("InventoryX", 0, 0, 1000)
    var intevetoryY = create("InventoryY", 0, 0, 1000)

    override fun onRender2D() {

        var ofs = 1
        var offsets = 1
        var offsetsP = 1
        val sr = ScaledResolution(mc)

        if (coords.value) {
            var position = "null"
            val x = mc.player.posX
            val y = mc.player.posY
            val z = mc.player.posZ
            position = if (mc.player.dimension == -1) {
                String.format("%.2f, %.2f, %.2f [%.2f, %.2f]", x, y, z, x * 8, z * 8)
            } else if (mc.player.dimension == 0) {
                String.format("%.2f, %.2f, %.2f [%.2f, %.2f]", x, y, z, x / 8, z / 8)
            } else {
                String.format("%.2f, %.2f, %.2f", x, y, z)
            }
            TextManager.INSTANCE.drawStringWithShadow(
                position,
                1f,
                ((if (mc.ingameGUI.chatGUI.chatOpen) sr.scaledHeight - 23 else sr.scaledHeight - 11) + ofs).toFloat(),
                -1,
                ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
            ofs -= TextManager.INSTANCE.fontHeight + offset.value
        }

        if(potion.value){
            var i = 0
            val format2 = DecimalFormat("00")

            mc.player.getActivePotionEffects().forEach {

               var name = I18n.format(it.getPotion().getName());
               var duration = it.getDuration() / 19.99f;
               var amplifier = it.getAmplifier() + 1;
               var p1 = duration % 60f;
               var seconds = format2.format(p1);

               val s =  name + " " + amplifier + ChatFormatting.GRAY + " " + duration.toInt() / 60 + ":" + seconds

               TextManager.INSTANCE.drawStringWithShadow(s, 1f, 277 + (i * TextManager.INSTANCE.fontHeight + 1).toFloat(), -1, ModuleManager.INSTANCE.getModule(CustomFont::class.java).isEnabled)

                i++
                }
           }

        if (array.value) {
           var resolution = ScaledResolution(mc)
            var i = 0
            for (module in ModuleManager.INSTANCE.modules) {
                if (module.isEnabled) {
                    TextManager.INSTANCE.drawStringWithShadow(
                        module.name,
                        (resolution.scaledWidth - TextManager.INSTANCE.getStringWidth(module.name) - 1).toFloat(),
                        (i * TextManager.INSTANCE.fontHeight + 1).toFloat(),
                        -1,
                        ModuleManager.INSTANCE.getModule(
                            CustomFont::class.java
                        ).isEnabled
                    )
                    i++
                }
            }
        }

        if(invetoryPreview.value){
            GL11.glPushMatrix()
            RenderHelper.enableGUIStandardItemLighting()
            Render2DUtil.drawBorderedRect(intevetoryX.value.toDouble(), intevetoryY.value.toDouble(),  145.0,  48.0, 1F,0x75101010, 0x90000000.toInt())
            for (i in 0..26) {
                val itemStack = mc.player.inventory.mainInventory[i + 9]
                val offsetX: Int = intevetoryX.getValue() + i % 9 * 16
                val offsetY: Int = intevetoryY.getValue() + i / 9 * 16
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY)
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null)
            }
            RenderHelper.disableStandardItemLighting()
            GL11.glPopMatrix()

        }
        if (armor.value) {
            GlStateManager.enableTexture2D()
            val i = sr.scaledWidth / 2
            var iteration = 0
            val y =
                sr.scaledHeight - 55 - if (mc.player.isInWater && mc.playerController.gameIsSurvivalOrAdventure()) 10 else 0
            for (`is` in mc.player.inventory.armorInventory) {
                ++iteration
                if (`is`.isEmpty()) continue
                val x = i - 90 + (9 - iteration) * 20 + 2
                GlStateManager.enableDepth()
                itemRender.zLevel = 200.0f
                itemRender.renderItemAndEffectIntoGUI(`is`, x, y)
                itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, `is`, x, y, "")
                itemRender.zLevel = 0.0f
                GlStateManager.enableTexture2D()
                GlStateManager.disableLighting()
                GlStateManager.disableDepth()
                val s = if (`is`.count > 1) `is`.count.toString() + "" else ""
                TextManager.INSTANCE.drawStringWithShadow(
                    s,
                    (x + 19 - 2 - TextManager.INSTANCE.getStringWidth(s)).toFloat(),
                    (y + 9).toFloat(),
                    0xFFFFFF,
                    ModuleManager.INSTANCE.getModule(
                        CustomFont::class.java
                    ).isEnabled
                )
                var dmg = 0
                val itemDurability = `is`.maxDamage - `is`.getItemDamage()
                val green = (`is`.maxDamage.toFloat() - `is`.getItemDamage().toFloat()) / `is`.maxDamage.toFloat()
                val red = 1.0f - green
                dmg = 100 - (red * 100.0f).toInt()
                TextManager.INSTANCE.drawStringWithShadow(
                    dmg.toString() + "",
                    (x + 8 - TextManager.INSTANCE.getStringWidth(dmg.toString() + "") / 2).toFloat(),
                    (y - 11).toFloat(),
                    Color((red * 255.0f).toInt(), (green * 255.0f).toInt(), 0).rgb,
                    ModuleManager.INSTANCE.getModule(
                        CustomFont::class.java
                    ).isEnabled
                )
            }
            GlStateManager.enableDepth()
            GlStateManager.disableLighting()
        }
        if (mark.value) {
            TextManager.INSTANCE.drawStringWithShadow(
                Aurora.MOD_NAME + "" + Aurora.VERSION, 1f, 1f, -1, ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
        }
        if (fps.value) {
            val fps = "Fps " + fpsLine
            TextManager.INSTANCE.drawStringWithShadow(
                fps,
                (sr.scaledWidth - TextManager.INSTANCE.getStringWidth(fps) - 2).toFloat(),
                (sr.scaledHeight - 11 + offsets).toFloat(),
                -1,
                ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
            offsets -= TextManager.INSTANCE.fontHeight + offset.value
        }
        if (ping.value) {
            val ping = "Ping " + playerPing
            TextManager.INSTANCE.drawStringWithShadow(
                ping,
                (sr.scaledWidth - TextManager.INSTANCE.getStringWidth(ping) - 2).toFloat(),
                (sr.scaledHeight - 11 + offsets).toFloat(),
                -1,
                ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
        }
        if (welcomer.value) {
            val text = "Welcome to " + Aurora.MOD_NAME + "!"
            TextManager.INSTANCE.drawStringWithShadow(
                text,
                (sr.scaledWidth / 2f - TextManager.INSTANCE.getStringWidth(text) / 2f).toInt().toFloat(),
                1f,
                -1,
                ModuleManager.INSTANCE.getModule(
                    CustomFont::class.java
                ).isEnabled
            )
        }
    }

    companion object {
        var itemRender = mc.getRenderItem()
        val playerPing: Int
            get() = try {
                mc.connection!!.getPlayerInfo(mc.connection!!.gameProfile.id).responseTime
            } catch (e: Exception) {
                0
            }
        val fpsLine: String
            get() {
                var line = ""
                val fps = Minecraft.getDebugFPS()
                line += if (fps > 120) {
                    ChatFormatting.GREEN
                } else if (fps > 60) {
                    ChatFormatting.YELLOW
                } else {
                    ChatFormatting.RED
                }
                return "$line $fps"
            }
    }
}