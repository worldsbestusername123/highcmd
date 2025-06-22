package agent;

import java.io.File;
import java.lang.instrument.*;
import java.security.ProtectionDomain;
import java.util.Set;

public class Premain2 implements ClassFileTransformer {
    public static void premain(String args, Instrumentation inst) {
        System.out.println("[Terminality-Agent] Attaching runtime transformer...");
        inst.addTransformer(new MixinFilterTransformer(), true);
    }

    public static class MixinFilterTransformer implements ClassFileTransformer {
        private static final String ALLOW_PREFIX = "net/mcreator/highcmdforge";

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
                throws IllegalClassFormatException {
            if (className == null) return null;
            String name = className.toLowerCase();
            if (name.contains("mixin") && !className.startsWith(ALLOW_PREFIX)) {
                System.out.println("[Transformer] Blocked mixin: " + className.replace('/', '.'));
                return new byte[0];
            }
            return classfileBuffer;
        }
    }
}
