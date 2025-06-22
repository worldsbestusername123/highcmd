package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class AResist2 {
    @Inject(at = @At("HEAD"), method = "removePassenger", cancellable = true)
    private void removePassenger(Entity p_20352_, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof TerminalEntity || entity instanceof Player) {
            ci.cancel();
        }
    }
}
