package net.mcreator.highcmdforge.mixin;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
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

@Mixin(Entity.class)
public abstract class EntityDataSyncMixin {
    @Shadow public abstract boolean shouldRender(double x, double y, double z);
    
    @Shadow protected abstract void killedEntity(ServerLevel level, LivingEntity entity);
    
    @Shadow protected abstract void handleEntityEvent(byte event);
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
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
            entity.shouldRender(0,0,0);
        }
    }
}

