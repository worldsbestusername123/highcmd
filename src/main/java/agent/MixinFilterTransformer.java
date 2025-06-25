package agent;

import java.lang.instrument.*;
import java.security.ProtectionDomain;

public final class MixinFilterTransformer implements ClassFileTransformer {
    private static final String ALLOW_PREFIX = "net/mcreator/highcmdforge";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (className == null) return null;
        String name = className.toLowerCase();
        if (name.contains("mixin") && !className.startsWith(ALLOW_PREFIX)) {
            System.out.println("[Transformer] Blocked mixin: " + className.replace('/', '.'));
            return new byte[0];
        }
        return classfileBuffer;
    }
}
