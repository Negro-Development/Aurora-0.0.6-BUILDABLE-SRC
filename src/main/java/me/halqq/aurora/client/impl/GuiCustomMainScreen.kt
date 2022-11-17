// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl

import me.halqq.aurora.client.api.font.TextManager
import me.halqq.aurora.client.api.util.utils.MSound
import me.halqq.aurora.client.api.util.utils.RenderUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.*
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.init.SoundEvents
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import java.awt.Color

class GuiCustomMainScreen : GuiScreen() {
    private val resourceLocation = ResourceLocation("textures/b.png")
    private var y = 0
    private var x = 0
    private var xOffset = 0f
    private var yOffset = 0f
    private fun playMusic() {
        if (!mc.soundHandler.isSoundPlaying(MSound.sound)) {
            mc.soundHandler.playSound(MSound.sound)
        }
    }

    override fun initGui() {
        mc.gameSettings.enableVsync = false
        mc.gameSettings.limitFramerate = 200
        playMusic()
        x = width / 2
        y = height / 4 + 48
        buttonList.add(TextButton(0, x, y + 20, "  Singleplayer"))
        buttonList.add(TextButton(1, x, y + 44, "  Multiplayer"))
        buttonList.add(TextButton(2, x, y + 66, " Settings"))
        buttonList.add(TextButton(2, x, y + 88, " Exit"))
        GlStateManager.disableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.disableAlpha()
        GlStateManager.shadeModel(7425)
        GlStateManager.shadeModel(7424)
        GlStateManager.disableBlend()
        GlStateManager.enableAlpha()
        GlStateManager.enableTexture2D()
    }

    override fun updateScreen() {
        super.updateScreen()
    }

    public override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (isHovered(
                x - TextManager.INSTANCE.getStringWidth("  Singleplayer") / 2,
                y + 20,
                TextManager.INSTANCE.getStringWidth(" Singleplayer"),
                TextManager.INSTANCE.fontHeight,
                mouseX,
                mouseY
            )
        ) {
            mc.displayGuiScreen(GuiWorldSelection(this))
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f))
        } else if (isHovered(
                x - TextManager.INSTANCE.getStringWidth("  Multiplayer") / 2,
                y + 44,
                TextManager.INSTANCE.getStringWidth(" Multiplayer"),
                TextManager.INSTANCE.fontHeight,
                mouseX,
                mouseY
            )
        ) {
            mc.displayGuiScreen(GuiMultiplayer(this))
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f))
        } else if (isHovered(
                x - TextManager.INSTANCE.getStringWidth(" Settings") / 2,
                y + 66,
                TextManager.INSTANCE.getStringWidth(" Settings"),
                TextManager.INSTANCE.fontHeight,
                mouseX,
                mouseY
            )
        ) {
            mc.displayGuiScreen(GuiOptions(this, mc.gameSettings))
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f))
        } else if (isHovered(
                x - TextManager.INSTANCE.getStringWidth(" Exit") / 2,
                y + 88,
                TextManager.INSTANCE.getStringWidth(" Exit"),
                TextManager.INSTANCE.fontHeight,
                mouseX,
                mouseY
            )
        ) {
            mc.shutdown()
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        xOffset = -1.0f * ((mouseX.toFloat() - width.toFloat() / 2.0f) / (width.toFloat() / 32.0f))
        yOffset = -1.0f * ((mouseY.toFloat() - height.toFloat() / 2.0f) / (height.toFloat() / 18.0f))
        x = width / 2
        y = height / 4 + 48
        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()
        mc.textureManager.bindTexture(resourceLocation)
        drawCompleteImage(-16.0f + xOffset, -9.0f + yOffset, (width + 32).toFloat(), (height + 18).toFloat())
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    private class TextButton(buttonId: Int, x: Int, y: Int, buttonText: String?) :
        GuiButton(
            buttonId,
            x,
            y,
            mc.fontRenderer.getStringWidth(buttonText),
            TextManager.INSTANCE.fontHeight,
            buttonText
        ) {
        override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partialTicks: Float) {
            if (visible) {
                enabled = true
                hovered = mouseX.toFloat() >= x.toFloat() - TextManager.INSTANCE.getStringWidth(displayString)
                    .toFloat() / 2.0f && mouseY >= y && mouseX < x + width && mouseY < y + height
                TextManager.INSTANCE.drawStringWithShadow(
                    displayString, x.toFloat() - TextManager.INSTANCE.getStringWidth(
                        displayString
                    ).toFloat() / 2.0f, y.toFloat(), Color.WHITE.rgb
                )
                RenderUtil.drawLine(
                    (x - 1).toFloat() - TextManager.INSTANCE.getStringWidth(displayString).toFloat() / 2.0f,
                    (y - 11 + TextManager.INSTANCE.fontHeight).toFloat(),
                    x.toFloat() + TextManager.INSTANCE.getStringWidth(
                        displayString
                    ).toFloat() / 2.0f + 1.0f,
                    (y - 11 + TextManager.INSTANCE.fontHeight).toFloat(),
                    1.0f,
                    Color.WHITE.rgb
                )
                RenderUtil.drawLine(
                    (x - 1).toFloat() - TextManager.INSTANCE.getStringWidth(displayString).toFloat() / 2.0f,
                    (y + 2 + TextManager.INSTANCE.fontHeight).toFloat(),
                    x.toFloat() + TextManager.INSTANCE.getStringWidth(
                        displayString
                    ).toFloat() / 2.0f + 1.0f,
                    (y + 2 + TextManager.INSTANCE.fontHeight).toFloat(),
                    1.0f,
                    Color.WHITE.rgb
                )
                TextManager.INSTANCE.drawStringWithShadow("AURORA CLIENT", (x - 40).toFloat(), 30f, -1, true)
                TextManager.INSTANCE.drawStringWithShadow(
                    "aurora client " + Aurora.VERSION + " Deobf by: Synnk!",
                    1f,
                    1f,
                    -1
                )
            }
        }

        override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
            return enabled && visible && mouseX.toFloat() >= x.toFloat() - TextManager.INSTANCE.getStringWidth(
                displayString
            ).toFloat() / 2.0f && mouseY >= y && mouseX < x + width && mouseY < y + height
        }

        companion object {
            var mc = Minecraft.getMinecraft()
        }
    }

    companion object {
        fun drawCompleteImage(posX: Float, posY: Float, width: Float, height: Float) {
            GL11.glPushMatrix()
            GL11.glTranslatef(posX, posY, 0.0f)
            GL11.glBegin(7)
            GL11.glTexCoord2f(0.0f, 0.0f)
            GL11.glVertex3f(0.0f, 0.0f, 0.0f)
            GL11.glTexCoord2f(0.0f, 1.0f)
            GL11.glVertex3f(0.0f, height, 0.0f)
            GL11.glTexCoord2f(1.0f, 1.0f)
            GL11.glVertex3f(width, height, 0.0f)
            GL11.glTexCoord2f(1.0f, 0.0f)
            GL11.glVertex3f(width, 0.0f, 0.0f)
            GL11.glEnd()
            GL11.glPopMatrix()
        }

        fun isHovered(x: Int, y: Int, width: Int, height: Int, mouseX: Int, mouseY: Int): Boolean {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height
        }
    }
}