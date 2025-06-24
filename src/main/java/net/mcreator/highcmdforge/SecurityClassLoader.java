package net.mcreator.highcmdforge;

import agent.Premain;

import java.io.File;
import java.lang.instrument.*;
import java.security.ProtectionDomain;
import java.util.Set;

public class SecurityClassLoader extends ClassLoader implements ClassFileTransformer {

    private static final Set<String> ALLOWED_PACKAGES = Set.of(
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

    public SecurityClassLoader(ClassLoader parent) {
        super(parent);
        System.out.println("[Terminal-Agent] Initialized ClassLoader Security Layer");
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.toLowerCase().contains("mixin") && !name.startsWith("net.mcreator.highcmdforge")) {
            throw new SecurityException("[Terminal-Agent] Blocked unauthorized mixin class: " + name);
        }
        return super.loadClass(name, resolve);
    }

    public static class SecurityAgent {
        public static void premain(String args, Instrumentation inst) {
            try {
                String jarPath = Premain.getJarAbsolutePathFromClass(SecurityAgent.class);
                System.out.println("[Terminal-Agent] Agent jar: " + jarPath);
                if (args == null || !args.contains(jarPath)) {
                    throw new SecurityException("Mismatched security agent");
                }
                inst.addTransformer(new SecurityInspector.MixinBlocker(), true);
            } catch (Exception e) {
                throw new RuntimeException("Agent premain failed", e);
            }
        }
    }

    public static class SecurityInspector {
        public static class MixinBlocker implements ClassFileTransformer {
            private static final String ALLOW_PREFIX = "net/mcreator/highcmdforge";

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (className == null) return null;
                String name = className.toLowerCase();
                if (name.contains("mixin") && !className.replace('/', '.').startsWith(ALLOW_PREFIX)) {
                    System.out.println("[Terminal-Agent] Blocked unauthorized mixin bytecode: " + className.replace('/', '.'));
                    return new byte[0];
                }
                return classfileBuffer;
            }
        }

        public static class SecurityTransformer implements ClassFileTransformer {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                  Class<?> classBeingRedefined,
                                  ProtectionDomain protectionDomain,
                                  byte[] classfileBuffer) {
                return classfileBuffer;
            }
        }
    }
}
