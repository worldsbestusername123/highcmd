package net.mcreator.highcmdforge;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CMDProtectedEntities {
    public static List<UUID> protectedPlayers = new ArrayList<>();

    public static boolean isEntityDefended(Entity entity)
    {
        if (entity instanceof Player player)
            return protectedPlayers.contains(player.getGameProfile().getId());
        return entity instanceof TerminalEntity;
    }
}
