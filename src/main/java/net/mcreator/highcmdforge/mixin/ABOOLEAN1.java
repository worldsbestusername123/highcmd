package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class ABOOLEAN1 {
    @Inject(at = @At("HEAD"), method = "shouldRender", cancellable = true)
    private void shouldRender(double p_20296_, double p_20297_, double p_20298_, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;
        Level world = entity.level();
        if (!(entity instanceof TerminalEntity) && (!(entity instanceof Player)) && HighCmdforgeModVariables.MapVariables.get(world).DEATH == true) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        else {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
