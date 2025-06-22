package agent;

import com.sun.tools.attach.VirtualMachine;
import net.mcreator.highcmdforge.AgentTester;
import net.mcreator.highcmdforge.Interception;
import net.mcreator.highcmdforge.MixinNullifier;
import net.mcreator.highcmdforge.TerminalRBreaker;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.io.File;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

            VirtualMachine current = VirtualMachine.attach(currentPid);
            current.loadAgent(agentJarAbsolutePath);
            current.detach();

            System.out.println("[Terminal-Agent] Agent attached from: " + agentJarAbsolutePath);
        } catch (Throwable e) {
            System.err.println("[Terminal-Agent] Failed to attach agent:");
            e.printStackTrace();
            throw new RuntimeException("Agent attachment failed", e);
        }
    }
}