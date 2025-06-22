package net.mcreator.highcmdforge;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class AgentTester implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        if (className != null) {
            System.out.println("[AgentTester] Saw: " + className.replace('/', '.'));
        }
        return null;
    }
}