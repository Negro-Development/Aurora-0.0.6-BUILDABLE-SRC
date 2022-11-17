// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting;

import me.halqq.aurora.client.api.module.Module;


abstract public class Setting<T> {

    private final String name;
    private final Module module;
    private boolean vvisible;

    public Setting(String name, Module module, boolean visible) {
        this.name = name;
        this.module = module;
        this.vvisible = visible;
    }

    public String getName() {
        return name;
    }

    public Module getModule() {
        return module;
    }

    public boolean getVisible() {
        return vvisible;
    }

    public void setVisible(boolean visible) {
        this.vvisible = visible;
    }

    abstract public T getValue();

    abstract public void setValue(T value);
}
