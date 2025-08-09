package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.CMDProtectedEntities;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClassInstanceMultiMap.class)
public class ClassInstanceMultiMapMixin {
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void injAdd(Object pValue, CallbackInfoReturnable<Boolean> cir)
    {
        if (pValue instanceof Entity entity && !CMDProtectedEntities.isProtected(entity) && HighCmdforgeModVariables.MapVariables.get(entity.level()).DEATH)
        {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
    private void injRemove(Object pValue, CallbackInfoReturnable<Boolean> cir)
    {
        if (pValue instanceof Entity entity && CMDProtectedEntities.isProtected(entity) && HighCmdforgeModVariables.MapVariables.get(entity.level()).DEATH)
        {
            cir.setReturnValue(false);
        }
    }
}
