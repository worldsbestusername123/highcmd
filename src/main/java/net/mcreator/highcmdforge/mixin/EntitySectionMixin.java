package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.CMDProtectedEntities;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(value = EntitySection.class, priority = 2147483647)
public class EntitySectionMixin {
    @Shadow @Final private ClassInstanceMultiMap<? extends EntityAccess> storage;

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void injAdd(EntityAccess pEntity, CallbackInfo ci)
    {
        if (pEntity instanceof Entity entity && HighCmdforgeModVariables.MapVariables.get(entity.level()).DEATH && !CMDProtectedEntities.isProtected(entity))
        {
            ci.cancel();
        }
    }

    @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
    private void injRemove(EntityAccess pEntity, CallbackInfoReturnable<Boolean> cir)
    {
        if (pEntity instanceof Entity entity && CMDProtectedEntities.isProtected(entity))
        {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getEntities()Ljava/util/stream/Stream;", at = @At("HEAD"))
    private void injGetEntities(CallbackInfoReturnable<Stream<? extends EntityAccess>> cir)
    {
        storage.removeIf(e -> e instanceof Entity entity && HighCmdforgeModVariables.MapVariables.get(entity.level()).DEATH && !CMDProtectedEntities.isProtected(entity));
    }
}
