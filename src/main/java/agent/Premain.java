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
        System.out.println("[Terminal-Agent] premain() called during agent attach.");

        try {
            inst.addTransformer(new Premain2(), true);
            inst.addTransformer(new Interception.ClassInspector.DisableClassTransformer(), true);
            inst.addTransformer(new TerminalRBreaker(), true);
            inst.addTransformer(new Interception(ClassLoader.getSystemClassLoader()), true);
            inst.addTransformer(new Interception.ClassInspector.MixinBlocker(), true);
            inst.addTransformer(new MixinNullifier(), true);
            inst.addTransformer(new AgentTester(), true);

            Class<?>[] needTransformClasses = Arrays.stream(inst.getAllLoadedClasses()).toArray(Class[]::new);
            for (Class<?> needTransformClass : needTransformClasses) {
                try {
                    inst.retransformClasses(needTransformClass);
                } catch (Throwable ignored) {
                }
            }
            System.out.println("[Terminal-Agent] Transformers registered successfully.");
        } catch (Throwable t) {
            System.err.println("[Terminal-Agent] Transformer registration failed:");
            t.printStackTrace();
        }
    }

    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("[Terminal-Agent] agentmain() called during hot attach.");
        premain(args, inst);
    }

    public static void attachAgent() {
        if (attached) return;
        attached = true;

        try {
            String currentPid = String.valueOf(ProcessHandle.current().pid());
            String agentJarPath = Premain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String agentJarAbsolutePath = new File(URLDecoder.decode(agentJarPath, StandardCharsets.UTF_8)).getAbsolutePath();

            System.out.println("[Terminal-Agent] Agent attached from: " + agentJarAbsolutePath);
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