package net.mcreator.highcmdforge;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UltraInterceptor{
    public static Object setNull(Object target) {
        return null;
    }


    public static void allReturn(Object target) throws IllegalAccessException, NoSuchFieldException {



        Class<?> clazz = target.getClass();
        while (clazz != null) {

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.set(null, null);
                try {
                    Class<?> type = field.getType();
                    if (type == int.class) {

                        field.setInt(target, 0);
                    }
                    else if (type == boolean.class) {
                        field.setBoolean(target, false);
                    }
                    else if (type == double.class) {
                        field.setDouble(target, 0);
                    }
                    else if (type == short.class) {
                        field.setShort(target, (short) 0);
                    }
                    else if (type == byte.class) {
                        field.setByte(target, (byte) 0);
                    }

                    else if (type == Entity.class) {
                        setNull(target);
                    }
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