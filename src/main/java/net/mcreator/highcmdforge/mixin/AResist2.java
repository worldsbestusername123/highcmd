package net.mcreator.highcmdforge.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class AResist2 {
    @Inject(at = @At("HEAD"), method = "removePassenger", cancellable = true)
    private void removePassenger(Entity p_20352_, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (net.mcreator.highcmdforge.CMDProtectedEntities.isProtected(entity)) {
            ci.cancel();
        }

    }
}
