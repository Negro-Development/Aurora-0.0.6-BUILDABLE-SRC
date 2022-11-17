// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.rina.turok.render.image;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class TurokImage {
    private String path;

    private BufferedImage bufferedImage;
    private ResourceLocation resourceLocation;

    private DynamicTexture dynamicTexture;

    public TurokImage(String path) {
        this.path = path;

        try {
            this.bufferedImage = ImageIO.read(TurokImage.class.getResourceAsStream(this.path));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        this.dynamicTexture = new DynamicTexture(this.bufferedImage);

        this.resourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("", this.dynamicTexture);
    }

    public int getWidth() {
        return this.bufferedImage.getWidth();
    }

    public int getHeight() {
        return this.bufferedImage.getHeight();
    }

    public String getPath() {
        return path;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public DynamicTexture getDynamicTexture() {
        return dynamicTexture;
    }
}