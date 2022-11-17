// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.loader.classloader;

import java.util.HashMap;
import java.util.Map;


public class DynamicClassLoader {

    private final Map<String, byte[]> classes = new HashMap<>();

    public void addClass(String name, byte[] bytes) {
        classes.put(name, bytes);
    }

}
