// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.rina.turok.util;

public class TurokClass {
    public static Enum getEnumByName(Enum _enum, String name) {
        for (Enum enums : _enum.getClass().getEnumConstants()) {
            if (_enum.name().equals(name)) {
                return _enum;
            }
        }

        return null;
    }
}
