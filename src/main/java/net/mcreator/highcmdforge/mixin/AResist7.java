package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class AResist7 {
    @Inject(method = "moveTo(DDD)V", at = @At("HEAD"), cancellable = true)
    private void moveTo(double x, double y, double z, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
            ci.cancel();
        }
    }
        @Inject(method = "kill", at = @At("HEAD"), cancellable = true)
        private void kill(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
                ci.cancel();
            }
    }
        @Inject(method = "killedEntity", at = @At("HEAD"), cancellable = true)
        private void killedEntity(ServerLevel p_216988_, LivingEntity p_216989_, CallbackInfoReturnable<Boolean> cir) {
            Entity entity = (Entity) (Object) this;
            if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
                cir.setReturnValue(false);
            }
        }

        @Inject(method = "ejectPassengers", at = @At("HEAD"), cancellable = true)
        private void ejectPassengers(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
                ci.cancel();
            }
        }
        @Inject(method = "setNoGravity", at = @At("HEAD"), cancellable = true)
        private void setNoGravity(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
                ci.cancel();
            }
        }
        @Inject(method = "setSilent", at = @At("HEAD"), cancellable = true)
        private void setSilent(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
                ci.cancel();
            }
        }
        @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
        private void remove(CallbackInfo ci) {
            Entity entity = (Entity) (Object) this;
            if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
                ci.cancel();
            }
        }
}




