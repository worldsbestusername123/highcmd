package net.mcreator.highcmdforge;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.*;

public class Utils {
    public static final Map<ServerLevel, Map<TerminalEntity, BlockPos>> poses = new HashMap<>();

    // No very strong, but can deal with normal mod.
    public static void tickCheck(ServerLevel level) {
        if (!poses.containsKey(level)) return;

        Map<TerminalEntity, BlockPos> entityToPos = poses.get(level);
        int entityCount = entityToPos.size(), entityInLevelCount = 0;
        List<TerminalEntity> entitiesInLevel = new ArrayList<>();
        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof TerminalEntity terminalEntity) {
                if (entityToPos.containsKey(terminalEntity)) {
                    entitiesInLevel.add(terminalEntity);
                    entityInLevelCount++;
                }
            }
        }
        if (entityCount > entityInLevelCount) {
            entityToPos.keySet().stream()
                    .filter(entity -> !entitiesInLevel.contains(entity))
                    .forEach(e -> TerminalEntity.spawn(level, entityToPos.get(e)));
        }
    }

    private static Optional<ServerLevel> levelCheck(Level level) {
        if (level.isClientSide) return Optional.empty();
        if (level instanceof ServerLevel serverLevel) return Optional.of(serverLevel);
        return Optional.empty();
    }

    public static void addToSpanList(TerminalEntity entity) {
        Optional<ServerLevel> levelCheck = levelCheck(entity.level());
        if (levelCheck.isEmpty()) return;
        ServerLevel serverLevel = levelCheck.get();

        Map<TerminalEntity, BlockPos> entityToPos = poses.containsKey(serverLevel) ? poses.get(serverLevel) : new HashMap<>();
        entityToPos.put(entity, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()));
        poses.put(serverLevel, entityToPos);
    }

    public static void updatePos(TerminalEntity entity) {
        Optional<ServerLevel> levelCheck = levelCheck(entity.level());
        if (levelCheck.isEmpty()) return;
        ServerLevel serverLevel = levelCheck.get();

        Map<TerminalEntity, BlockPos> entityToPos = poses.containsKey(serverLevel) ? poses.get(serverLevel) : new HashMap<>();
        if (!entityToPos.containsKey(entity)) {
            addToSpanList(entity);
        } else {
            entityToPos.put(entity, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()));
            poses.put(serverLevel, entityToPos);
        }
    }
}
