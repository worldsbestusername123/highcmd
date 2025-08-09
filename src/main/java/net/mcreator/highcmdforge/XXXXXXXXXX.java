package net.mcreator.highcmdforge;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class XXXXXXXXXX implements ClassFileTransformer {
    @Override
    public byte[] transform(
            Module module,
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer
    ) {
        if (classBeingRedefined != null && isProtected(className)) {
            throw new SecurityException("[Terminality-AI] smth trying to redefine a class: " + className);
        }

        return classfileBuffer;
    }

    private boolean isProtected(String className) {
        return className.equals("net/mcreator/highcmdforge/TerminalEntityLevelRenderer");
    }
}
