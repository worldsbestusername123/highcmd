package net.mcreator.highcmdforge.mixin;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ServerLevel.class, priority = Integer.MAX_VALUE)
public class AMixinBan {

	@Inject(at = @At("HEAD"), method = "addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
	private void onEntityJoin(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		ServerLevel level = (ServerLevel) (Object) this;

		if (HighCmdforgeModVariables.MapVariables.get(level) != null && HighCmdforgeModVariables.MapVariables.get(level).DEATH == true) {
			if (!(entity instanceof Player)) {
				if (!(entity instanceof net.mcreator.highcmdforge.entity.TerminalEntity)) {
					cir.setReturnValue(false);
					cir.cancel();
				}
				else {

				}
			}
		}
	}
}





