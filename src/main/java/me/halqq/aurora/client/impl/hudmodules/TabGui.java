// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.hudmodules;

import me.halqq.aurora.client.api.font.TextManager;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.module.ModuleManager;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.List;


public class TabGui extends Module {

    public TabGui() {
        super("TabGui", Category.HUD);
    }

    public static int x = 2;
    public static int y = 2;
    public static int width = 100;
    public static int height = 14;

    @Override
    public void onRender2D() {

    }
}
