package net.mcreator.highcmdforge.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;

public class TurnOffOnKeyPressedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		HighCmdforgeModVariables.MapVariables.get(world).DEATH = false;
		HighCmdforgeModVariables.MapVariables.get(world).syncData(world);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal("Off"), false);
	}
}
