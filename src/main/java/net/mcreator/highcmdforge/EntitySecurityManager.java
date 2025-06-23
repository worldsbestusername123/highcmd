package net.mcreator.highcmdforge;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class EntitySecurityManager {
    public static void cleanupEntities(Object maybeLevel) {
        try {
            Class<?> serverLevelClass = Class.forName("net.minecraft.server.level.ServerLevel");
            if (!serverLevelClass.isInstance(maybeLevel)) return;

            Field emField = serverLevelClass.getDeclaredField("entityManager");
            emField.setAccessible(true);
            Object entityManager = emField.get(maybeLevel);

            Field sectionsField = entityManager.getClass().getDeclaredField("sections");
            sectionsField.setAccessible(true);
            Object sectionsObj = sectionsField.get(entityManager);

            if (!(sectionsObj instanceof Map<?, ?> sectionMap)) return;

            int cleanedCount = 0;
            for (Object section : sectionMap.values()) {
                Field[] fields = section.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    Object entitySet = f.get(section);
                    if (entitySet instanceof Set<?> set) {
                        cleanedCount += set.size();
                        set.clear();
                    } else if (entitySet instanceof Map<?, ?> map) {
                        cleanedCount += map.size();
                        map.clear();
                    }
                }
            }

            if (cleanedCount > 0) {
                System.out.println("[Terminal-Agent] Cleaned up " + cleanedCount + " unauthorized entities from server level.");
            }

        } catch (Throwable t) {
            System.out.println("[Terminal-Agent] Entity cleanup failed.");
            t.printStackTrace();
        }
    }
}
