package net.mcreator.highcmdforge.mixin;

import net.minecraft.server.level.ServerLevel;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.mcreator.highcmdforge.CMDProtectedEntities;

@Mixin(Entity.class)
public abstract class EntityDataSyncMixin {
    @Shadow public abstract boolean shouldRender(double x, double y, double z);
    
    @Shadow protected abstract void killedEntity(ServerLevel level, LivingEntity entity);
    
    @Shadow protected abstract void handleEntityEvent(byte event);
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        Level world = entity.level();
        if (HighCmdforgeModVariables.MapVariables.get(world).DEATH && !CMDProtectedEntities.isProtected(entity)) {
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

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.deathTime = Integer.MAX_VALUE;
                livingEntity.hurtTime = Integer.MAX_VALUE;
                livingEntity.kill();
                livingEntity.discard();
                livingEntity.removeStingerTime = Integer.MAX_VALUE;
                livingEntity.setHealth(0.0F);
                ((LivingEntity) entity).isDeadOrDying();
            }

            entity.onClientRemoval();
            entity.noPhysics = true;
            entity.isFullyFrozen();
            entity.shouldRender(0, 0, 0);
            AABB boundingBox = new AABB(0, 0, 0, 0, 0, 0);
            entity.setBoundingBox(boundingBox);
            entity.shouldRender(0,0,0);
        }
    }
}

