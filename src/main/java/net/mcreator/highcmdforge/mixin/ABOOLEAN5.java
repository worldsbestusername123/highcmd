package net.mcreator.highcmdforge.mixin;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public abstract class ABOOLEAN5 {
    @Shadow public abstract boolean shouldRender(double p_20296_, double p_20297_, double p_20298_);

    @Shadow public abstract boolean killedEntity(ServerLevel p_216988_, LivingEntity p_216989_);

    @Shadow @Final protected SynchedEntityData entityData;

    @Shadow public abstract void handleEntityEvent(byte p_19882_);

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick2(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        Level world = entity.level();
        if (((!(entity instanceof TerminalEntity)) && (!(entity instanceof Player)) && HighCmdforgeModVariables.MapVariables.get(world).DEATH == true)) {
            entity.setInvulnerable(false);
            entity.setNoGravity(true);
            entity.setSilent(true);
            entity.onRemovedFromWorld();
            entity.stopRiding();
            entity.ejectPassengers();
            entity.invalidateCaps();
            entity.setRemoved(Entity.RemovalReason.KILLED);
            ServerLevel serverWorld = (ServerLevel) world;
            serverWorld.getChunkSource().removeEntity(entity);
            entity.kill();
            entity.removeVehicle();
            entity.remove(Entity.RemovalReason.KILLED);
            entity.setInvisible(true);
            entity.setOnGround(false);
            entity.onRemovedFromWorld();
            entity.getPassengers().remove(entity);
            entity.invulnerableTime = 0;
            entity.canUpdate(false);
            entity.getPersistentData().getAllKeys().clear();
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.deathTime = Integer.MAX_VALUE;
            livingEntity.hurtTime = Integer.MAX_VALUE;
            livingEntity.kill();
            livingEntity.discard();
            livingEntity.removeStingerTime = Integer.MAX_VALUE;
            livingEntity.setHealth(0.0F);
            entity.onClientRemoval();
            entity.noPhysics = true;
            ((LivingEntity) entity).isDeadOrDying();
            entity.isFullyFrozen();
            entity.shouldRender(0, 0, 0);
            AABB boundingBox = new AABB(0, 0, 0, 0, 0, 0);
            entity.setBoundingBox(boundingBox);
            entityData.isEmpty();
            entity.shouldRender(0,0,0);

        }
    }
}

