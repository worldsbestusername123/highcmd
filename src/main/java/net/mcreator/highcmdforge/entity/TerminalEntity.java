
package net.mcreator.highcmdforge.entity;

import net.mcreator.highcmdforge.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;

import net.mcreator.highcmdforge.init.HighCmdforgeModEntities;
import org.jetbrains.annotations.NotNull;

public final class TerminalEntity extends Monster {
    private boolean broadcasted = false;

    public TerminalEntity(PlayMessages.SpawnEntity ignoredPacket, Level world) {
        this(HighCmdforgeModEntities.TERMINAL.get(), world);
    }

    public TerminalEntity(EntityType<TerminalEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 0;
        setNoAi(false);
    }

    public static void spawn(ServerLevel level, BlockPos pos) {
        Entity entityToSpawn = HighCmdforgeModEntities.TERMINAL.get().spawn(level, pos, MobSpawnType.MOB_SUMMONED);
        if (entityToSpawn != null) {
            entityToSpawn.setDeltaMovement(0, 0, 0);
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource ds) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public @NotNull SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damagesource, float amount) {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    public static void init() {
    }

    @Override
    public void tick() {
        super.tick();
        Utils.updatePos(this);
        if (!broadcasted) {
            if (!level().isClientSide() && level().getServer() != null) {
                PlayerList playerList = level().getServer().getPlayerList();
                playerList.broadcastSystemMessage(Component.literal("<TERMINAL> EVENTBUS SHUTDOWN."), false);
                playerList.broadcastSystemMessage(Component.literal("EVENTBUS §k SHUTDOWN"), false);
            }
            MinecraftForge.EVENT_BUS.shutdown();
            broadcasted = true;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 10);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void remove(Entity.@NotNull RemovalReason reason) {

    }

    @Override
    public float getHealth() {
        return Float.POSITIVE_INFINITY;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }

    @Override
    public boolean shouldBeSaved() {
        return true;
    }
}
