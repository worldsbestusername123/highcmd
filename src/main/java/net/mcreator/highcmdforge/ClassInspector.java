package net.mcreator.highcmdforge;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassInspector {
    public static class MixinBlocker implements ClassFileTransformer {
        private static final String ALLOW_PREFIX = "net/mcreator/highcmdforge";
        private static final String ALLOW_PREFIX2 = "agentmainterminality";

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer)
                throws IllegalClassFormatException {
            if (className == null) return null;
            String name = className.toLowerCase();
            if (name.contains("Mx") && !className.replace('/', '.').startsWith(ALLOW_PREFIX) || !className.replace('/', '.').equals(ALLOW_PREFIX2)) {
                return new byte[0];
            }
            return classfileBuffer;
        }
    }

    public static class DisableClassTransformer implements ClassFileTransformer {
    }
}