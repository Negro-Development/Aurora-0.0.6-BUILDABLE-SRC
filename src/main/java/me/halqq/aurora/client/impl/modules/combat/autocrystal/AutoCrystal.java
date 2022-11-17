// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.impl.modules.combat.autocrystal;

import me.halqq.aurora.client.api.event.events.PacketEvent;
import me.halqq.aurora.client.api.event.events.RenderEvent;
import me.halqq.aurora.client.api.module.Category;
import me.halqq.aurora.client.api.module.Module;
import me.halqq.aurora.client.api.setting.settings.SettingBoolean;
import me.halqq.aurora.client.api.setting.settings.SettingDouble;
import me.halqq.aurora.client.api.setting.settings.SettingInteger;
import me.halqq.aurora.client.api.setting.settings.SettingMode;
import me.halqq.aurora.client.api.util.utils.*;
import me.halqq.aurora.client.api.util.utils.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.*;



public class AutoCrystal extends Module {


    SettingDouble rangeBreak = create("RangeBreak", 4.0, 0.5, 6.0);
    SettingDouble rangePlace = create("RangePlace", 4.0, 0.5, 6.0);
    SettingBoolean sBreak = create("Break", true);
    SettingInteger targetRange = create("TargetRange", 10, 0, 15);
    SettingBoolean pauseOnGapple = create("Gapple", false, false);
    SettingBoolean pauseOnSword = create("PauseOnSword", false, false);
    SettingBoolean pauseOnHealth = create("PauseOnHealth", false, false);
    SettingInteger pauseHealth = create("PauseHealth", 15, 0, 36);
    SettingBoolean pauseOnExp = create("PauseOnExp", false, false);
    SettingBoolean silentSwitch = create("SilentSwitch", false, false);
    SettingBoolean boost = create("BreakBoost", false);
    SettingInteger minDmg = create("MinDamage", 10, 0, 80);
    SettingInteger maxSelfDmg = create("MaxSelfDamage", 10, 0, 80);
    SettingBoolean updatedPlacements = create("1.13+Placements", false, false);
    SettingMode facePlaceMode = create("FacePlaceMode", "Never", Arrays.asList("Never", "Health", "Bind", "Always"));
    SettingInteger facePlaceHp = create("FacePlaceDelay", 15, 0, 36);
    SettingBoolean antiSuicide = create("AntiSuicide", true);
    SettingInteger breakDelay = create("BreakDelay", 0, 0, 600);

    SettingBoolean calc = create("CalcDamage", true);
    SettingInteger red = create("Red", 255, 0, 225);
    SettingInteger green = create("Green", 0, 0, 225);
    SettingInteger blue = create("Blue", 255, 0, 225);
    SettingInteger alpha = create("Alpha", 170, 0, 225);
    SettingInteger width = create("Width", 2, 1, 6);


    SettingBoolean allowCollision = create("AllowCollision", false, false);

    SettingBoolean placeRaytrace = create("PlaceRaytrace", false, false);
    SettingInteger placeRaytraceRange = create("PlaceRaytraceRange", 5, 0, 6);

    SettingMode hand = create("Hand", "Offhand", Arrays.asList("MainHand", "Offhand"));

    BestBlockPos bestCrystalPos = new BestBlockPos(BlockPos.ORIGIN, 0);
    EntityPlayer targetPlayer;
    EntityPlayer targeplayer2;
    BlockPos finalPos;
    float mainTargetDamage;
    float mainTargetHealth;
    float mainMinimumDamageValue;
    float mainSelfHealth;
    float mainSelfDamage;
    int mainSlot;
    int mainOldSlot;
    TimerUtil breakTimer = new TimerUtil();


    static class BestBlockPos {
        BlockPos blockPos;
        float targetDamage;

        public BestBlockPos(BlockPos blockPos, float targetDamage) {
            this.blockPos = blockPos;
            this.targetDamage = targetDamage;
        }

        public float getTargetDamage() {
            return targetDamage;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }
    }

    public BestBlockPos getBestPlacePos() {
        TreeMap<Float, BestBlockPos> posList = new TreeMap<>();
        for (BlockPos pos : BlockUtil.getSphere(rangePlace.getValue())) {
            float targetDamage = EntityUtil.calculatePosDamage(pos, targetPlayer);
            float selfHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            float selfDamage = EntityUtil.calculatePosDamage(pos, mc.player);
            float targetHealth = targetPlayer.getHealth() + targetPlayer.getAbsorptionAmount();
            float minimumDamageValue = minDmg.getValue();
            mainTargetDamage = targetDamage;
            mainTargetHealth = targetHealth;
            mainSelfDamage = selfDamage;
            mainSelfHealth = selfHealth;
            mainMinimumDamageValue = minimumDamageValue;
            if (BlockUtil.isPosValidForCrystal(pos, updatedPlacements.getValue())) {
                if (mc.player.getDistance(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f) > me.halqq.aurora.client.api.util.utils.MathUtil.square(rangePlace.getValue()))
                    continue;

                if (!allowCollision.getValue() && !mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).isEmpty() && mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos).setMaxY(1)).isEmpty())
                    continue;

                if (BlockUtil.isPlayerSafe(targetPlayer) && (facePlaceMode.getValue().equalsIgnoreCase("Always") || (facePlaceMode.getValue().equalsIgnoreCase("Health") && targetHealth < facePlaceHp.getValue())))
                    minimumDamageValue = 2;

                if (antiSuicide.getValue() && selfDamage > selfHealth)
                    continue;

                if (selfDamage > maxSelfDmg.getValue())
                    continue;

                if (targetDamage < minimumDamageValue)
                    continue;

                if (placeRaytrace.getValue() && !rayTraceCheckPos(new BlockPos(pos.getX(), pos.getY(), pos.getZ())) && mc.player.getDistance(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f) > MathUtil.square(placeRaytraceRange.getValue()))
                    continue;

                posList.put(targetDamage, new BestBlockPos(pos, targetDamage));
            }
        }
        if (!posList.isEmpty()) {
            return posList.lastEntry().getValue();
        }
        return null;
    }

    private boolean rayTraceCheckPos(BlockPos pos) {
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double) mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX(), pos.getY(), pos.getZ()), false, true, false) == null;
    }


    public AutoCrystal() {
        super("AutoCrystal", Category.COMBAT);
    }


    @Override
    public void onUpdate() {
        targetPlayer = EntityUtil.getTarget(targetRange.getValue());


        if (targetPlayer == null)
            return;

        if ((pauseOnGapple.getValue() && mc.player.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE) && mc.gameSettings.keyBindUseItem.isKeyDown())
                || (pauseOnSword.getValue() && mc.player.getHeldItemMainhand().equals(Items.DIAMOND_SWORD))
                || (pauseOnExp.getValue() && mc.player.getHeldItemMainhand().equals(Items.EXPERIENCE_BOTTLE) && mc.gameSettings.keyBindUseItem.isKeyDown()
                || (pauseOnHealth.getValue() && mc.player.getHealth() + mc.player.getAbsorptionAmount() < pauseHealth.getValue())))
            return;

        doBreak();
        doPlace();

    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal) {
                        if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                            e.setDead();
                        }
                    }
                }
            }
        }
    }


    public void doBreak() {
        int sword = InventoryUtil.getItemSlot(Items.DIAMOND_SWORD);
        int oldSlot = mc.player.inventory.currentItem;
        if (silentSwitch.getValue() && (mc.player.getHeldItemMainhand().getItem() != Items.DIAMOND_SWORD) && mc.player.getActivePotionEffects().equals(Potion.getPotionById(18)))
            InventoryUtil.switchToSlot(sword);
        final EntityEnderCrystal crystal = (EntityEnderCrystal) AutoCrystal.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).min(Comparator.comparing(c -> mc.player.getDistance(c))).orElse(null);
        if (crystal != null && AutoCrystal.mc.player.getDistance((Entity) crystal) <= rangeBreak.getValue()) {
            if (boost.getValue()) {
                crystal.setDead();
                mc.world.removeAllEntities();
                mc.world.getLoadedEntityList();
                mc.playerController.updateController();
                crystal.ticksExisted = 100;
            }
            if (sBreak.getValue()) {
                if(breakTimer.passedMS(breakDelay.getValue())) {
                    mc.playerController.attackEntity((EntityPlayer) mc.player, (Entity) crystal);
                    breakTimer.reset();
                }
            }
            if (hand.getValue().equalsIgnoreCase("MainHand")) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
            } else if (hand.getValue().equalsIgnoreCase("OffHand")) {
                mc.player.swingArm(EnumHand.OFF_HAND);
            }
        }
        if (silentSwitch.getValue() && (mc.player.getHeldItemMainhand().getItem() != Items.DIAMOND_SWORD) && mc.player.getActivePotionEffects().equals(Potion.getPotionById(18))) {
            mc.player.inventory.currentItem = oldSlot;
            mc.playerController.updateController();
        }
    }


    public void doPlace() {

        final EntityEnderCrystal crystal = (EntityEnderCrystal) AutoCrystal.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).min(Comparator.comparing(c -> mc.player.getDistance(c))).orElse(null);

        mc.world.getLoadedEntityList();
        int slot = InventoryUtil.getItemSlot(Items.END_CRYSTAL);
        int oldSlot = mc.player.inventory.currentItem;
        mainSlot = slot;
        mainOldSlot = oldSlot;
        if (silentSwitch.getValue() && (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL))
            InventoryUtil.switchToSlot(slot);
        bestCrystalPos = getBestPlacePos();
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal || mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {

            if (bestCrystalPos == null)
                return;

            if (silentSwitch.getValue() && (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL)) {
                InventoryUtil.switchToSlot(slot);
            }

            mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(bestCrystalPos.getBlockPos(), EnumFacing.UP, mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));

            finalPos = bestCrystalPos.getBlockPos();

            if (silentSwitch.getValue() && (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL || mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL)) {
                mc.player.inventory.currentItem = oldSlot;
                mc.playerController.updateController();
            }
        }
    }

    public void doHandActive(final EnumHand hand) {
        if (hand != null) {
            AutoCrystal.mc.player.setActiveHand(hand);
        }
    }

    @Override
    public void onRender3D(RenderEvent event) {
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            if (finalPos != null && targetPlayer != null && bestCrystalPos != null) {
                Color color = new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
                Color color2 = new Color(red.getValue(), green.getValue(), blue.getValue(), 75);

                AxisAlignedBB bb = new AxisAlignedBB(finalPos.getX() - mc.getRenderManager().viewerPosX, finalPos.getY() - mc.getRenderManager().viewerPosY, finalPos.getZ() - mc.getRenderManager().viewerPosZ, finalPos.getX() + 1 - mc.getRenderManager().viewerPosX, finalPos.getY() + 1 - mc.getRenderManager().viewerPosY, finalPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
                RenderUtil.drawGradientFilledBox(bb, color, color2);

            }

            if(calc.getValue()){
                RenderUtil.drawText(bestCrystalPos.blockPos, "DMG : " + bestCrystalPos.targetDamage, -1);
            }
        }
    }
}
