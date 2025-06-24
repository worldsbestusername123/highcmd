package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.CMDProtectedEntities;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class AResist {
    @Inject(at = @At("HEAD"), method = "setRemoved", cancellable = true)
    private void setRemoved(Entity.RemovalReason p_146876_, CallbackInfo ci) {
        Entity entityiterator = (Entity) (Object) this;
        if (CMDProtectedEntities.isProtected(entityiterator)) {
            ci.cancel();
        }
    }
}

