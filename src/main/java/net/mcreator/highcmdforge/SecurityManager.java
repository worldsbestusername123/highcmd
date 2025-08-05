package net.mcreator.highcmdforge;

public class SecurityManager extends java.lang.SecurityManager {
    @Override
    public void checkPackageAccess(String pkg) {
        Class<?>[] context = getClassContext();

        for (Class<?> caller : context) {
            String callerPackage = caller.getPackage().getName();

            if (callerPackage.startsWith("net.mcreator.highcmdforge")) {
                if (pkg.equals("java.lang.reflect")) {
                    throw new SecurityException("Stopped Reflection");
                }
                break;
            }
        }
    }
}