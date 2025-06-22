package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class AResist {
    @Inject(at = @At("HEAD"), method = "setRemoved", cancellable = true)
    private void setRemoved(Entity.RemovalReason p_146876_, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        Entity entityiterator = (Entity)(Object)this;
        if (entityiterator instanceof TerminalEntity || entity instanceof net.mcreator.highcmdforge.entity.TerminalEntity) {
            ci.cancel();
        }
        else {

        }
    }
}

