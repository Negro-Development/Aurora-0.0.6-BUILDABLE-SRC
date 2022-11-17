// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.util.render.objects;

import me.halqq.aurora.client.api.util.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SideOnly(Side.CLIENT)
public class Particles {

    private static final List<Particle> particles = new ArrayList<>();
    private static int amount;

    private static int prevWidth;
    private static int prevHeight;

    public Particles(final int amount) {
        this.amount = amount;
    }

    public static void draw(final int mouseX, final int mouseY) {
        if(particles.isEmpty() || prevWidth != Minecraft.getMinecraft().displayWidth || prevHeight != Minecraft.getMinecraft().displayHeight) {
            particles.clear();
            create();
        }

        prevWidth = Minecraft.getMinecraft().displayWidth;
        prevHeight = Minecraft.getMinecraft().displayHeight;

        for(final Particle particle : particles) {
            particle.fall();
            particle.interpolation();

            int range = 50;
            final boolean mouseOver = (mouseX >= particle.x - range) && (mouseY >= particle.y - range) && (mouseX <= particle.x + range) && (mouseY <= particle.y + range);

            if(mouseOver) {
                particles.stream()
                        .filter(part -> (part.getX() > particle.getX() && part.getX() - particle.getX() < range
                                && particle.getX() - part.getX() < range)
                                && (part.getY() > particle.getY() && part.getY() - particle.getY() < range
                                || particle.getY() > part.getY() && particle.getY() - part.getY() < range))
                        .forEach(connectable -> particle.connect(connectable.getX(), connectable.getY()));
            }

            RenderUtil.drawCircle(particle.getX(), particle.getY(), particle.size, 0xffFFFFFF);
        }
    }

    private static void create() {
        final Random random = new Random();

        for(int i = 0; i < amount; i++)
            particles.add(new Particle(random.nextInt(Minecraft.getMinecraft().displayWidth), random.nextInt(Minecraft.getMinecraft().displayHeight)));
    }
}