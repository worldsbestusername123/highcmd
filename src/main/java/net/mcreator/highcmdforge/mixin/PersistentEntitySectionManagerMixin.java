package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.CMDProtectedEntities;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin {
    @Inject(method = "addEntityUuid", at = @At("HEAD"), cancellable = true)
    private void injAddEntityUUID(EntityAccess pEntity, CallbackInfoReturnable<Boolean> cir)
    {
        if (pEntity instanceof Entity entity && !CMDProtectedEntities.isProtected(entity) && HighCmdforgeModVariables.MapVariables.get(entity.level()).DEATH)
        {
            cir.setReturnValue(false);
        }
    }
}
