package net.mcreator.highcmdforge;

import java.lang.instrument.*;
import java.util.Set;

public final class SecurityClassLoader extends ClassLoader implements ClassFileTransformer {
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
            "net.mcreator.highcmdforge",
            "com.sun.",
            "agent."
    );

    public SecurityClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.toLowerCase().contains("mixin") &&
                !name.startsWith("net.mcreator.highcmdforge") &&
                !name.startsWith("org.spongepowered")  &&
                !name.equals("agentmain") || !name.equals("MixinFilterTransformer")) {
            throw new SecurityException("[Terminal-Agent] Blocked unauthorized mixin class: " + name);
        }
        return super.loadClass(name, resolve);
    }
}
