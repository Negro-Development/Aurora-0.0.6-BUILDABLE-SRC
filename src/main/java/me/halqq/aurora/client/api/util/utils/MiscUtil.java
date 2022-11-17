// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.util.utils;

import me.halqq.aurora.client.api.util.Minecraftable;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class MiscUtil implements Minecraftable {

    public static KeyBinding[] keys = {
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindJump,
            mc.gameSettings.keyBindSprint
    };

    public static void loadClientIcon() {
        try {
            InputStream bigIcon = mc.getResourceManager().getResource(new ResourceLocation("aurora/icon_32.png")).getInputStream();
            InputStream smallIcon = mc.getResourceManager().getResource(new ResourceLocation("aurora/icon_16.png")).getInputStream();
            Display.setIcon(new ByteBuffer[]{
                    loadIcon(bigIcon),
                    loadIcon(smallIcon)
            });
        } catch (IOException ignored) {
        }
    }

    private static ByteBuffer loadIcon(final InputStream iconFile) throws IOException {
        final BufferedImage icon = ImageIO.read(iconFile);

        final int[] rgb = icon.getRGB(0, 0, icon.getWidth(), icon.getHeight(), null, 0, icon.getWidth());

        final ByteBuffer buffer = ByteBuffer.allocate(4 * rgb.length);
        for (int color : rgb) {
            buffer.putInt(color << 8 | ((color >> 24) & 0xFF));
        }
        buffer.flip();
        return buffer;
    }

    public boolean setTick(){return mc.player.ticksExisted % 2 == 0;}

}
