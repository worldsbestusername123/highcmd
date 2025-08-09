package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.CMDProtectedEntities;
import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityLookup;
import net.minecraft.world.level.entity.LevelEntityGetterAdapter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LevelEntityGetterAdapter.class)
public class LevelEntityGetterAdapterMixin {
    @Shadow @Final private EntityLookup<? extends EntityAccess> visibleEntities;

    @Inject(method = "get(I)Lnet/minecraft/world/level/entity/EntityAccess;", at = @At("HEAD"), cancellable = true)
    private void injGet(int pId, CallbackInfoReturnable<? extends EntityAccess> cir)
    {
        if (visibleEntities.getEntity(pId) instanceof Entity entity && HighCmdforgeModVariables.MapVariables.get(entity.level()).DEATH && !CMDProtectedEntities.isProtected(entity))
        {
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "get(Ljava/util/UUID;)Lnet/minecraft/world/level/entity/EntityAccess;", at = @At("HEAD"), cancellable = true)
    private void injGet(UUID pUuid, CallbackInfoReturnable<? extends EntityAccess> cir)
    {
        if (visibleEntities.getEntity(pUuid) instanceof Entity entity && HighCmdforgeModVariables.MapVariables.get(entity.level()).DEATH && !CMDProtectedEntities.isProtected(entity))
        {
            cir.setReturnValue(null);
        }
    }
}
