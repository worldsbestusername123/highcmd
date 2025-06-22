package net.mcreator.highcmdforge;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class Interception5 {
    public static void purgeEntities(Object maybeLevel) {
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

            int nuked = 0;
            for (Object section : sectionMap.values()) {
                Field[] fields = section.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    Object entitySet = f.get(section);
                    if (entitySet instanceof Set<?> set) {
                        nuked += set.size();
                        set.clear();
                    } else if (entitySet instanceof Map<?, ?> map) {
                        nuked += map.size();
                        map.clear();
                    }
                }
            }

            if (nuked > 0) {
                System.out.println("[Terminality] Nuked " + nuked + " entities from server-level sections.");
            }

        } catch (Throwable t) {
            System.out.println("[Terminality] Entity section purge failed.");
            t.printStackTrace();
        }
    }
}
