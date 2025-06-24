package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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

        if (CMDProtectedEntities.isEntityDefended(entity)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
