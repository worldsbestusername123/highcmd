package net.mcreator.highcmdforge;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class MixinNullifier implements ClassFileTransformer {
    private static final String ALLOW_PREFIX = "net/mcreator/highcmdforge";

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        if (className == null) return null;
        String lowered = className.toLowerCase();
        if (lowered.contains("mixin") && !className.startsWith(ALLOW_PREFIX)) {
            System.out.println("[Terminal-Agent] DISABLED FOREIGN MIXIN: " + className.replace('/', '.'));
            return new byte[0];
        }
        return null;
    }
}