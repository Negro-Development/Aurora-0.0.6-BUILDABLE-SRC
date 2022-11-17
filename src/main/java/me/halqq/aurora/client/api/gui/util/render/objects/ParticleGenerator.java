// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.gui.util.render.objects;

import me.halqq.aurora.client.api.module.ModuleManager;
import me.halqq.aurora.client.impl.modules.client.ClickGui;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ParticleGenerator {

    private static final Particles particles = new Particles(ModuleManager.INSTANCE.getModule(ClickGui.class).getParticleamount().getValue());

    public static boolean draw(int mouseX, int mouseY) {
        particles.draw(mouseX, mouseY);
        return false;
    }
}
