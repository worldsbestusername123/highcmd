package net.mcreator.highcmdforge;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public final class CMDProtectedEntities {
    private static final List<Entity> protectedEntities = new ArrayList<>();

    public static boolean isProtected(Entity entity) {
        if (protectedEntities.contains(entity)) {
            return true;
        }
        return entity instanceof TerminalEntity;
    }

    public static void setProtected(Entity entity) {
        if (protectedEntities.contains(entity)) return;
        protectedEntities.add(entity);
    }
}
