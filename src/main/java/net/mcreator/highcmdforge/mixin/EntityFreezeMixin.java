package net.mcreator.highcmdforge.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.apache.commons.lang3.StringUtils.startsWith;

@Mixin(LivingEntity.class)
public abstract class EntityFreezeMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void freezeOthers(CallbackInfo ci) {
        Object self = this;
        String className = self.getClass().getName();
        if (!className.startsWith("net.minecraft.") && !className.startsWith("net.mcreator.highcmdforge")) {
            System.out.println("[Terminality-AI] Freezing non-vanilla entity: " + className);
            ci.cancel();
        }
    }
}

