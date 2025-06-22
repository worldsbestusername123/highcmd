package net.mcreator.highcmdforge;

import java.io.File;
import java.lang.instrument.*;
import java.security.ProtectionDomain;
import java.util.Set;

public class Interception extends ClassLoader implements ClassFileTransformer {

    private static final Set<String> allowedPackages = Set.of(
            "java.",
            "jdk.",
            "net.minecraft",
            "com.google.",
            "com.mojang.",
            "org.apache.",
            "cpw.mods.",
            "io.netty.",
            "org.lwjgl.",
            "org.spongepowered",
            "net.mcreator.highcmdforge"
    );

    public Interception(ClassLoader parent) {
        super(parent);
        System.out.println("[Terminality] Initialised ClassLoader Manipulation");
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.toLowerCase().contains("mixin") && !name.startsWith("net.mcreator.highcmdforge")) {
            throw new SecurityException("[Terminality] Blocked external mixin class: " + name);
        }
        return super.loadClass(name, resolve);
    }

    public static class BLOCKTHELIES {
        public static void premain(String args, Instrumentation inst) {
            try {
                String jarPath = new File(BLOCKTHELIES.class.getProtectionDomain()
                        .getCodeSource().getLocation().toURI()).getAbsolutePath();
                System.out.println("[BLOCKTHELIES] Agent jar: " + jarPath);
                if (args == null || !args.contains(jarPath)) {
                    throw new SecurityException("Mismatched Terminality agent");
                }
                inst.addTransformer(new Interception.ClassInspector.MixinBlocker(), true);
            } catch (Exception e) {
                throw new RuntimeException("Agent premain failed", e);
            }
        }
    }

    public static class ClassInspector {
        public static class MixinBlocker implements ClassFileTransformer {
            private static final String ALLOW_PREFIX = "net/mcreator/highcmdforge";

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {
                if (className == null) return null;
                String name = className.toLowerCase();
                if (name.contains("mixin") && !className.replace('/', '.').startsWith(ALLOW_PREFIX)) {
                    System.out.println("[Transformer] Blocked external mixin bytecode: " + className.replace('/', '.'));
                    return new byte[0];
                }
                return classfileBuffer;
            }
        }

        public static class DisableClassTransformer implements ClassFileTransformer {
        }
    }
}
