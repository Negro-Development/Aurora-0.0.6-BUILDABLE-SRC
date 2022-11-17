// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.rina.turok.render.font;

import me.rina.turok.render.font.hal.CFont;
import me.rina.turok.render.font.hal.CFontRenderer;

import java.awt.*;

public class TurokFont extends CFontRenderer {
    private Font font;

    private boolean isRenderingCustomFont;

    public TurokFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);

        this.font = font;

        this.isRenderingCustomFont = true;
    }

    public void setRenderingCustomFont(boolean renderingCustomFont) {
        this.isRenderingCustomFont = renderingCustomFont;
    }

    public boolean isRenderingCustomFont() {
        return isRenderingCustomFont;
    }
}
