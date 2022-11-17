// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.rina.turok.hardware.mouse;

import org.lwjgl.input.Mouse;

public class TurokMouse {
    private int scroll;

    private int x;
    private int y;

    public TurokMouse(int mx, int my) {
        this.x = mx;
        this.y = my;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScroll() {
        return -(Mouse.getDWheel() / 10);
    }

    public boolean hasWheel() {
        return Mouse.hasWheel();
    }
}
