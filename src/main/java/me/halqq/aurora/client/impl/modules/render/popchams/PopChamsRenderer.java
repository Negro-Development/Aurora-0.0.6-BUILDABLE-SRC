// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.render.popchams;

import me.halqq.aurora.client.api.util.Minecraftable;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopChamsRenderer implements Minecraftable {
    private final ModulePopChams master;

    private final List<PopChamsRender> renderList;
    private final HashMap<String, ChamsTracker> map;

    public PopChamsRenderer(ModulePopChams master) {
        this.master = master;
        this.map = new HashMap<>();
        this.renderList = new ArrayList<>();
    }

    public ModulePopChams getMaster() {
        return master;
    }

    public HashMap<String, ChamsTracker> getMap() {
        return map;
    }

    public List<PopChamsRender> getRenderList() {
        return renderList;
    }

    public void registryTracker(ChamsTracker tracker) {
        this.getMap().put(tracker.getEntityPlayer().getName(), tracker);
    }

    public void addRenderEntity(ChamsTracker tracker) {
        this.getRenderList().add(tracker.getPopChamsRender());
    }

    public void add(PopChamsRender render) {
        this.renderList.add(render);
    }

    public boolean contains(String name) {
        return this.getTracker(name) != null;
    }

    public ChamsTracker getTracker(String name) {
        return this.getMap().get(name);
    }

    public void onWorldRender(float partialTicks) {
        for (PopChamsRender render : this.renderList) {
            render.onRender(partialTicks);
        }
    }

    public void onUpdate() {

        String invalidRegisteredTracker = null;

        for (Map.Entry<String, ChamsTracker> entry : this.getMap().entrySet()) {
            final ChamsTracker tracker = entry.getValue();
            final EntityPlayer entityPlayer =  tracker.getEntityPlayer();

            tracker.onUpdate();

            if (entityPlayer.isDead || mc.player.getDistance(entityPlayer) > this.master.getSettingRangeInt().getValue() || tracker.isFinished()) {
                invalidRegisteredTracker = entry.getKey();
            }
        }

        if (invalidRegisteredTracker != null) {
            ChamsTracker tracker = this.getMap().get(invalidRegisteredTracker);

            if (tracker != null) {
                this.map.remove(invalidRegisteredTracker);
            }
        }

        PopChamsRender renderToDelete = null;

        for (PopChamsRender render : this.renderList) {
            if (render.isTimeReach()) {
                renderToDelete = render;
            }
        }

        if (renderToDelete != null) {
            this.renderList.remove(renderToDelete);
        }
    }
}