package net.mcreator.highcmdforge;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.lang.reflect.Array.set;

public class UltraInterceptor{
    public static Object setNull(Object target) {
        return null;
    }


    public static void allReturn(Object target) throws IllegalAccessException, NoSuchFieldException {



        Class<?> clazz = target.getClass();
        while (clazz != null) {

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Class<?> type = field.getType();

                        field.set(null, null);

                } catch (IllegalAccessException e) {
                    System.err.println("Failed to reset field: " + field.getName());
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        System.out.println("This is why you don't use variables to define your mob's existence.");
    }

}