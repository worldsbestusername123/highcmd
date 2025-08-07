package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.CMDProtectedEntities;
import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ServerLevel.class, priority = Integer.MAX_VALUE)
public abstract class AResistandBoolean {
    @Shadow public abstract void unload(LevelChunk p_8713_);
    ServerLevel world = (ServerLevel)(Object)this;
    @Inject(method = "addEntity", at = @At("TAIL"), cancellable = true)
    private void addentity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (CMDProtectedEntities.isProtected(entity) && HighCmdforgeModVariables.MapVariables.get(world).DEATH == true) {
            cir.setReturnValue(true);
        }
    }

}
