package net.mcreator.highcmdforge.procedures;

import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

public class TerminalOnInitialEntitySpawnProcedure {
public static void execute(
LevelAccessor world
) {
if (!world.isClientSide() && world.getServer() != null)
world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<TERMINAL> EVENTBUS SHUTDOWN."), false);if (!world.isClientSide() && world.getServer() != null)
world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("EVENTBUS \u00A7k SHUTDOWN"), false);
MinecraftForge.EVENT_BUS.shutdown();
}
}
