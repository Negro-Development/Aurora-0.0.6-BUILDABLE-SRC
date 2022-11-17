// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.rina.turok.util;

public class TurokGeneric <S> {
    private S value;

    public TurokGeneric(final S value) {
        this.value = value;
    }

    public void setValue(S value) {
        this.value = value;
    }

    public S getValue() {
        return value;
    }
}
