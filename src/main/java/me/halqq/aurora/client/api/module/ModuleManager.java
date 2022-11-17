// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.module;

import me.halqq.aurora.client.api.event.events.RenderEvent;
import me.halqq.aurora.client.api.util.Minecraftable;
import me.halqq.aurora.client.api.util.utils.InventoryUtil;
import me.halqq.aurora.client.api.util.utils.MessageUtil;
import me.halqq.aurora.client.api.util.utils.RenderUtil;
import me.halqq.aurora.client.impl.hudmodules.*;
import me.halqq.aurora.client.impl.modules.client.ClickGui;
import me.halqq.aurora.client.impl.modules.client.HUD;
import me.halqq.aurora.client.impl.modules.client.HUDEditor;
import me.halqq.aurora.client.impl.modules.combat.*;
import me.halqq.aurora.client.impl.modules.combat.autocrystal.AutoCrystal;
import me.halqq.aurora.client.impl.modules.combat.offhand.ModuleOffhand;
import me.halqq.aurora.client.impl.modules.exploits.*;
import me.halqq.aurora.client.impl.modules.miscellaneous.*;
import me.halqq.aurora.client.impl.modules.movement.*;
import me.halqq.aurora.client.impl.modules.other.CustomFont;
import me.halqq.aurora.client.impl.modules.render.*;
import me.halqq.aurora.client.impl.modules.render.playerchams.PlayerChams;
import me.halqq.aurora.client.impl.modules.render.popchams.ModulePopChams;
import me.halqq.aurora.client.impl.modules.world.*;
import me.halqq.aurora.client.impl.modules.world.packetmine.ModulePacketMine;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ModuleManager implements Minecraftable {

    public static ModuleManager INSTANCE;
    private final List<Module> modules;
    public int delay=45;
    private int tmpdelay=0;
    private final LinkedHashMap<Class<? extends Module>, Module> modules2;

    public ModuleManager() {
        MinecraftForge.EVENT_BUS.register(this);
        modules = new ArrayList<>();
        modules2 = new LinkedHashMap<>();

        addModule(new HUDEditor());
        addModule(new AddEfects());
        addModule(new Freecam());
        addModule(new BoatFly());
        addModule(new Breadcrumbs());
        addModule(new AimBot());
        addModule(new LongJump());
        addModule(new Spider());
        addModule(new AirJump());
        addModule(new AntiAfk());
        addModule(new AntiLevitation());
        addModule(new AutoCat());
        addModule(new AutoClicker());
        addModule(new AutoCrystal());
        addModule(new AutoGG());
        addModule(new AutoJump());
        addModule(new AutoWalk());
        addModule(new AutoWeb());
        addModule(new AutoXp());
        addModule(new BetterChat());
        addModule(new ChatSuffix());
        addModule(new ClickGui());
        addModule(new Criticals());
        addModule(new CrystalChams());
        addModule(new CustomFont());
        addModule(new CustomFov());
        addModule(new FakePlayer());
        addModule(new FastFall());
        addModule(new FeetHighlight());
        addModule(new Flight());
        addModule(new FootXp());
        addModule(new FullBright());
        addModule(new HoleESP());
        addModule(new ItemEsp());
        addModule(new KillAura());
        addModule(new ModuleOffhand());
        addModule(new ModulePacketMine());
        addModule(new ModulePopChams());
        addModule(new NoSlow());
        addModule(new NoWeb());
        addModule(new Offhand());
        addModule(new PacketEat());
        addModule(new PlayerChams());
        addModule(new PrevFallPos());
        addModule(new RangeCircle());
        addModule(new RemoveEffects());
        addModule(new SelfBow());
        addModule(new SelfWeb());
        addModule(new ShutDown());
        addModule(new SilentPearl());
        addModule(new SkyColor());
        addModule(new SoundLogger());
        addModule(new Spammer());
        addModule(new Sprint());
        addModule(new TotemPop());
        addModule(new AutoEz());
        addModule(new RPCModule());
        addModule(new Step());
        addModule(new Surround());
        addModule(new TickShift());
        addModule(new Timer());
        addModule(new TimerChanger());
        addModule(new Tracers());
        addModule(new Velocity());
        addModule(new HoleBreakAlert());

        addModule(new me.halqq.aurora.client.impl.hudmodules.ArrayList());
        addModule(new ArmorHUD());
        addModule(new WaterMark());
        addModule(new TotemCount());
        addModule(new PotionHud());
        addModule(new InventoryHud());
        addModule(new Fps());
        addModule(new Ping());
        addModule(new Server());
        addModule(new Coords());
        addModule(new ExpCount());
        addModule(new HoleHud());
    }

    private void addModule(Module module) {
        modules.add(module);
        modules2.put(module.getClass(), module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesInCategory(Category category) {
        List<Module> mods = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                mods.add(module);
            }
        }
        return mods;
    }

    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) modules2.get(clazz);
    }

    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (Module module : modules) {
                if (module.getKey() == Keyboard.getEventKey()) {
                    module.toggle();
                    MessageUtil.toggleMessage(module);
                }
            }
        }
    }

    public static void onUpdate() {
        for (Module m : INSTANCE.modules) {
            try {
                if (m.isEnabled())
                    m.onUpdate();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    public ArrayList<Module> get(Predicate<Module> predicate) {
        return modules.stream().filter(predicate).collect(Collectors.toCollection(ArrayList::new));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (ModuleManager.mc.player != null) {
            if (ModuleManager.mc.world != null) {
                modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate);
            }
        }

    }
    @SubscribeEvent
    public final void onRender3D(RenderWorldLastEvent event) {
        for (Module modules : modules) {
            if (modules.isEnabled()) {
                modules.onWorldRender(event.getPartialTicks());
            }
        }

        mc.profiler.startSection("aurora");
        mc.profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1f);
        Vec3d pos = getInterpolatedPos(mc.player, event.getPartialTicks());
        RenderEvent eventRender = new RenderEvent(RenderUtil.INSTANCE, pos, event.getPartialTicks());
        eventRender.resetTranslation();
        mc.profiler.endSection();
        for (Module modules : modules) {
            if (modules.isEnabled()) {
                mc.profiler.startSection(modules.getName());
                modules.onRender3D(eventRender);
                mc.profiler.endSection();
            }
        }
        mc.profiler.startSection("release");
        GlStateManager.glLineWidth(1f);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        RenderUtil.releaseGL();
        mc.profiler.endSection();
        mc.profiler.endSection();
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Text text) {
        ModuleManager.INSTANCE.get(Module::isEnabled).forEach(Module::onRender2D);
    }


    public Vec3d getInterpolatedPos(Entity entity, double ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(process(entity, ticks, ticks, ticks));
    }

    public Vec3d process(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.posX - entity.lastTickPosX) * x,
                (entity.posY - entity.lastTickPosY) * y,
                (entity.posZ - entity.lastTickPosZ) * z);
    }

}