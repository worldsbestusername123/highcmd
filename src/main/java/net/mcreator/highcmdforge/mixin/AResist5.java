package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class AResist5 {
    @Inject(at = @At("HEAD"), method = "isRemoved", cancellable = true)
    private void isRemoved(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof TerminalEntity) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = {"isAlive"}, at = {@At("RETURN")}, cancellable = true)
    private void isAlive(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof TerminalEntity)
            cir.setReturnValue(true);
    }


    @Inject(method = {"save"}, at = {@At("HEAD")}, cancellable = true)
    public void save(CompoundTag p_20224_, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (((entity instanceof TerminalEntity)) || ((entity instanceof Player))) {
            cir.setReturnValue(true);
            entity.revive();
            entity.reviveCaps();
        }
    }
}




