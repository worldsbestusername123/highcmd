package net.mcreator.highcmdforge.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.highcmdforge.network.HighCmdforgeModVariables;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;
import net.minecraftforge.common.MinecraftForge;

public class DeathTerminalEntitySwingsItemProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof Player)) {
					if (!world.isClientSide() && world.getServer() != null)
						world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("run terminaldeletionfunc"), false);
					if (!world.isClientSide() && world.getServer() != null)
						world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("entity.setRemoved"), false);
					if (!world.isClientSide() && world.getServer() != null)
						world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("boolean DELETE = true"), false);
					if (!world.isClientSide() && world.getServer() != null)
						world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Task completed in 1 tick!"), false);
					if (!entityiterator.level().isClientSide())
						entityiterator.setRemoved(Entity.RemovalReason.KILLED);
					MinecraftForge.EVENT_BUS.unregister(entityiterator);
					MinecraftForge.EVENT_BUS.shutdown();
					entityiterator.isRemoved();
					entityiterator.setRemoved(Entity.RemovalReason.DISCARDED);
					HighCmdforgeModVariables.MapVariables.get(world).DEATH = true;
					HighCmdforgeModVariables.MapVariables.get(world).syncData(world);
				}
			}
		}
	}
}
