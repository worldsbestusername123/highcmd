package agent;

import com.sun.tools.attach.VirtualMachine;
import net.mcreator.highcmdforge.*;
import sun.misc.Unsafe;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Premain {
    private static boolean attached = false;

    public static void premain(String args, Instrumentation inst) {
        System.out.println("[Terminal-Agent] Initializing security agent...");

        try {
            inst.addTransformer(new Premain2(), true);
            inst.addTransformer(new SecurityClassLoader.SecurityInspector.SecurityTransformer(), true);
            inst.addTransformer(new TerminalRBreaker(), true);
            inst.addTransformer(new SecurityClassLoader(ClassLoader.getSystemClassLoader()), true);
            inst.addTransformer(new SecurityClassLoader.SecurityInspector.MixinBlocker(), true);
            inst.addTransformer(new MixinNullifier(), true);
            inst.addTransformer(new AgentTester(), true);

            Class<?>[] needTransformClasses = Arrays.stream(inst.getAllLoadedClasses()).toArray(Class[]::new);
            for (Class<?> needTransformClass : needTransformClasses) {
                try {
                    inst.retransformClasses(needTransformClass);
                } catch (Throwable ignored) {
                }
            }
            System.out.println("[Terminal-Agent] Security transformers registered successfully.");
        } catch (Throwable t) {
            System.err.println("[Terminal-Agent] Security transformer registration failed:");
            t.printStackTrace();
        }
    }

    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("[Terminal-Agent] Initializing security agent via hot attach...");
        premain(args, inst);
    }

    public static String getJarAbsolutePathFromClass(Class<?> clazz) {
        String jarPath = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        String jarPathDecoded = URLDecoder.decode(jarPath, StandardCharsets.UTF_8);
        String jarPathProcessed = jarPathDecoded.substring(0, jarPathDecoded.lastIndexOf(".jar") + ".jar".length());
        return new File(jarPathProcessed).getAbsolutePath();
    }

    public static void attachAgent() {
        if (attached) return;
        attached = true;

        try {
            String currentPid = String.valueOf(ProcessHandle.current().pid());
            String agentJarAbsolutePath = getJarAbsolutePathFromClass(Premain.class);

            System.out.println("[Terminal-Agent] Attaching agent from: " + agentJarAbsolutePath);
            Class<?> hotSpotVirtualMachineClass = Class.forName("sun.tools.attach.HotSpotVirtualMachine");
            Field field = hotSpotVirtualMachineClass.getDeclaredField("ALLOW_ATTACH_SELF");
            Unsafe unsafe = Helper.getUnsafe();
            assert unsafe != null;
            boolean originalValue = unsafe.getBoolean(field, unsafe.staticFieldOffset(field));
            unsafe.putBooleanVolatile(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), true);
            VirtualMachine current = VirtualMachine.attach(currentPid);
            current.loadAgent(agentJarAbsolutePath);
            current.detach();
            unsafe.putBoolean(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), originalValue);

        } catch (Throwable e) {
            System.err.println("[Terminal-Agent] Failed to attach agent:");
            e.printStackTrace();
            throw new RuntimeException("Agent attachment failed", e);
        }
    }
}