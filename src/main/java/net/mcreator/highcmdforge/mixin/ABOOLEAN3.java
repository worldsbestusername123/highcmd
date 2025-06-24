package net.mcreator.highcmdforge.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.mcreator.highcmdforge.CMDProtectedEntities;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class ABOOLEAN3 {
    @Inject(at = @At("HEAD"), method = "isAlive", cancellable = true)
    private void isAlive(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;

        if (CMDProtectedEntities.isProtected(entity)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
