// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.setting;

import me.halqq.aurora.client.api.module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings("rawtypes")
public class SettingManager {

    public static SettingManager INSTANCE;
    private final List<Setting> settings;

    public SettingManager() {
        settings = new ArrayList<>();
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public List<Setting> getSettingsInModule(Module module) {
        return settings.stream().filter(setting -> setting.getModule().equals(module)).collect(Collectors.toList());
    }
}