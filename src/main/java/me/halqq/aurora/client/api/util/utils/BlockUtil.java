// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.util.utils;

import me.halqq.aurora.client.api.util.Minecraftable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BlockUtil implements Minecraftable {
    private static boolean nonshift = false;
    private static final BlockUtil instance = new BlockUtil();

    public static List<BlockPos> getSphere(BlockPos pos, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = pos.getX();
        int cy = pos.getY();
        int cz = pos.getZ();
        int x = cx - (int) r;
        while ((float) x <= (float) cx + r) {
            int z = cz - (int) r;
            while ((float) z <= (float) cz + r) {
                int y = sphere ? cy - (int) r : cy;
                while (true) {
                    float f = y;
                    float f2 = sphere ? (float) cy + r : (float) (cy + h);
                    if (!(f < f2)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double) (r * r)) || hollow && dist < (double) ((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static void faceVectorPacketInstant(BlockPos pos) {
        float[] rotations = getLegitRotations(new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
     }

        public static float[] getLegitRotations(Vec3d vec) {
            Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
            double diffX = vec.x - eyesPos.x;
            double diffY = vec.y - eyesPos.y;
            double diffZ = vec.z - eyesPos.z;
            double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
            float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
            return new float[]{mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)};
        }


    public static boolean isPlayerSafe(EntityPlayer target) {
        BlockPos playerPos = new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
        return (mc.world.getBlockState(playerPos.down()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(playerPos.down()).getBlock() == Blocks.BEDROCK) &&
                (mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.BEDROCK) &&
                (mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.BEDROCK) &&
                (mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.BEDROCK) &&
                (mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.BEDROCK);
    }

    public static boolean isPosValidForCrystal(BlockPos pos, boolean onepointthirteen) {
        if (mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN)
            return false;

        if (mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR || (!onepointthirteen && mc.world.getBlockState(pos.up().up()).getBlock() != Blocks.AIR))
            return false;

        for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up()))) {

            if (entity.isDead || entity instanceof EntityEnderCrystal)
                continue;

            return false;
        }

        for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up().up()))) {

            if (entity.isDead || entity instanceof EntityEnderCrystal)

                continue;

            return false;
        }

        return true;
    }

    public static List<BlockPos> getSphere(double radius) {
        ArrayList<BlockPos> posList = new ArrayList<>();
        BlockPos pos = new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
        for (int x = pos.getX() - (int) radius; x <= pos.getX() + radius; ++x) {
            for (int y = pos.getY() - (int) radius; y < pos.getY() + radius; ++y) {
                for (int z = pos.getZ() - (int) radius; z <= pos.getZ() + radius; ++z) {
                    double distance = (pos.getX() - x) * (pos.getX() - x) + (pos.getZ() - z) * (pos.getZ() - z) + (pos.getY() - y) * (pos.getY() - y);
                    BlockPos position = new BlockPos(x, y, z);
                    if (distance < radius * radius && !mc.world.getBlockState(position).getBlock().equals(Blocks.AIR)) {
                        posList.add(position);
                    }
                }
            }
        }
        return posList;
    }


    public static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer) entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float) k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(Objects.requireNonNull(Potion.getPotionById(11)))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public static BlockUtil getInstance() {
        return instance;
    }

    public int isPlaceable(BlockPos bp) {
        if (mc.world == null || bp == null) return 1;
        for (Entity e : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(bp))) {
            if (e instanceof EntityXPOrb || e instanceof EntityItem) continue;
            if (e instanceof EntityPlayer) return 1;
            return -1;
        }
        return 0;
    }

    public List<BlockPos> getUnsafePositions(Vec3d vector, int level) {
        return getLevels(level).stream().map(vec -> new BlockPos(vector).add(vec.x, vec.y, vec.z)).filter(bp -> mc.world.getBlockState(bp).getMaterial().isReplaceable()).collect(Collectors.toList());
    }

    public List<Vec3d> getLevels(int y) {
        return Arrays.asList(new Vec3d(-1, y, 0), new Vec3d(1, y, 0), new Vec3d(0, y, 1), new Vec3d(0, y, -1));
    }

    public static boolean placeBlock(BlockPos pos, boolean rotate, boolean packet, boolean isSneaking) {
        EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();
        if (!mc.player.isSneaking()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            mc.player.setSneaking(true);
        }
        if (rotate) {
            BlockUtil.faceVectorPacketInstant(hitVec);
        }
        if (packet) {
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(neighbour, opposite, mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND, 0, 0, 0));
        } else {
            mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        }
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.rightClickDelayTimer = 4;
        return mc.player.isSneaking();
    }

    public static boolean isHole(BlockPos pos) {
        double x = Math.floor(mc.player.posX);
        double z = Math.floor(mc.player.posZ);

        Block block = mc.world.getBlockState(pos).getBlock();

        if(block == Blocks.OBSIDIAN || block == Blocks.BEDROCK){
            if(pos.getX() == (x + 1) && pos.getY() == mc.player.getPosition().getY()){
                return true;
            } else if(pos.getX() == (x - 1) && pos.getY() == mc.player.getPosition().getY()){
                return true;
            } else if(pos.getZ() == (z + 1) && pos.getY() == mc.player.getPosition().getY()){
                return true;
            } else if(pos.getZ() == (z - 1) && pos.getY() == mc.player.getPosition().getY()){
                return true;
            }
        }

        return false;
    }

    public static EnumFacing getFirstFacing(BlockPos pos) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = pos.offset(side);
            if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
                IBlockState blockState = mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = getLegitRotations(vec);
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
    }

    public static boolean placeBlock(BlockPos pos) {
        Block block = mc.world.getBlockState(pos).getBlock();
        EnumFacing direction = calcSide(pos);
        if (direction == null) {
            return false;
        }
        boolean activated = block.onBlockActivated(mc.world, pos, mc.world.getBlockState(pos), mc.player, EnumHand.MAIN_HAND, direction, 0.0f, 0.0f, 0.0f);
        if (activated) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos.offset(direction), direction.getOpposite(), EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
        if (activated || nonshift) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            nonshift = false;
        }
        mc.playerController.updateController();
        return true;
    }

    public static EnumFacing calcSide(BlockPos pos) {
        for (EnumFacing side : EnumFacing.values()) {
            IBlockState offsetState = mc.world.getBlockState(pos.offset(side));
            boolean activated = offsetState.getBlock().onBlockActivated(mc.world, pos, offsetState, mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (activated) {
                mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                nonshift = true;
            }
            if (!offsetState.getBlock().canCollideCheck(offsetState, false) || offsetState.getMaterial().isReplaceable())
                continue;
            return side;
        }
        return null;
    }



    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(mc.world, damage), new Explosion((World)mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }

    public static float getDamageMultiplied(final World p_World, float damage) {
        int diff = p_World.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }






    public static class CrystalPosition {
        private BlockPos position;
        private double targetDamage;
        private double selfDamage;

        public CrystalPosition(BlockPos position, double targetDamage, double selfDamage) {
            this.position = position;
            this.selfDamage = selfDamage;
            this.targetDamage = targetDamage;
        }

        public BlockPos getPosition() {
            return this.position;
        }

        public double getTargetDamage() {
            return this.targetDamage;
        }

        public double getSelfDamage() {
            return this.selfDamage;
        }
    }
}
