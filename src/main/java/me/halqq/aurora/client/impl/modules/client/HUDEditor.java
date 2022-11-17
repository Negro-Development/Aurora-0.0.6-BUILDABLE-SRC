// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.client;

import me.halqq.aurora.client.api.hud.HudEditorScreen;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;


public class HUDEditor extends Module {

        public HUDEditor() {
            super("HUDEditor", Category.OTHER);
        }

        @Override
        public void onEnable() {
            mc.displayGuiScreen(new HudEditorScreen());
        }

        @Override
        public void onDisable() {
            mc.displayGuiScreen(null);
        }
}
