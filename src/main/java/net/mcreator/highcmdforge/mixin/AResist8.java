package net.mcreator.highcmdforge.mixin;

import com.mojang.datafixers.types.templates.List;
import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;

@Mixin(Entity.class)
public class AResist8 {


        @Inject(method = "kill", at = @At("HEAD"), cancellable = true)
        private void kill(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                ci.cancel();
            }
        }

        @Inject(method = "removeVehicle", at = @At("HEAD"), cancellable = true)
        private void removeVehicle(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                ci.cancel();
            }
        }

        @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
        private void remove(Entity.RemovalReason reason, CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                ci.cancel();
            }
        }

        @Inject(method = "setInvisible", at = @At("HEAD"), cancellable = true)
        private void setInvisible(boolean invisible, CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                ci.cancel();
            }
        }

        @Inject(method = "onClientRemoval", at = @At("HEAD"), cancellable = true)
        private void onClientRemoval(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                ci.cancel();
            }
        }

        @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
        private void shouldRender(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                cir.setReturnValue(true);
            }
        }


        @Inject(method = "moveTo*", at = @At("HEAD"), cancellable = true)
        private void moveTo(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                ci.cancel();
            }
        }

        @Inject(method = "moveTo*", at = @At("TAIL"), cancellable = true)
        private void moveTo2(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof TerminalEntity) {
                ci.cancel();
            }
        }
    }







