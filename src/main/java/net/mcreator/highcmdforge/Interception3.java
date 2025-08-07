package net.mcreator.highcmdforge;

import agent.Premain;
import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.*;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Interception3 implements ITransformationService {

    static {
        System.out.println("[Terminality-AI] Static block begin.");

        // check if we haven't already relaunched the game
        if (!Objects.equals(System.getProperty("cmd.hasRelaunched"), "true")) {
            try {
                // THX https://codingtechroom.com/question/-java-run-jar-from-another-jar-with-passing-arguments
                URI minecraftLocation = Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI();

                // build a process and run the new game with our agent, of course
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-javaagent:" + Premain.getJarAbsolutePathFromClass(Premain.class), "-Dcmd.hasRelaunched=\"true\"", "-jar", new File(minecraftLocation).getPath());
                processBuilder.start();

                System.out.println("[Terminality-AI] Relaunch completed.");
                System.exit(-1);
            } catch (Throwable t) {
                // gob would've thrown a tantrum and crashed the game here, but not terminality, terminality is chill
                System.err.println("[Terminality-AI] ERROR. ERROR. ERROR.");
                throw new RuntimeException(t);
            }
        }
    }

    @Override
    public String name() {
        return "terminality-launch-plugin";
    }

    static {
        LaunchPluginHandler handler = Helper.getFieldValue(Launcher.INSTANCE, "launchPlugins", LaunchPluginHandler.class);
        Map<String, ILaunchPluginService> plugins = (Map<String, ILaunchPluginService>) Helper.getFieldValue(handler, "plugins", Map.class);
        Map<String, ILaunchPluginService> newMap = new HashMap<>();
        newMap.put("terminality-launch-plugin", new ServerSecurityPlugin() {
        });
        if (plugins != null) for (String name : plugins.keySet())
            newMap.put(name, plugins.get(name));
        Helper.setFieldValue(handler, "plugins", newMap);
        Helper.coexistenceCoreAndMod();
    }

    @Override
    public void initialize(IEnvironment environment) {
        System.out.println("[Terminality-AI] TransformationService initialized early");
    }

    @Override
    public void onLoad(IEnvironment environment, Set<String> otherServices) {
        System.out.println("[Terminality-AI] TransformationService is loading");
    }

    @Override
    public List<Resource> beginScanning(IEnvironment env) {
        return List.of();
    }

    @Override
    public List<ITransformer> transformers() {
        return List.of();
    }

    private void TerminalILaunchPlugin() {
        try {
            System.out.println("[Terminality] Attempting ModLauncher plugin injection...");

            Class<?> launcherClass = Class.forName("cpw.mods.modlauncher.Launcher");
            Field instanceField = launcherClass.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);
            Object launcher = instanceField.get(null);

            Field pluginHandlerField = launcherClass.getDeclaredField("launchPlugins");
            pluginHandlerField.setAccessible(true);
            Object pluginHandler = pluginHandlerField.get(launcher);

            Field pluginMapField = pluginHandler.getClass().getDeclaredField("plugins");
            pluginMapField.setAccessible(true);
            Map<String, Object> plugins = (Map<String, Object>) pluginMapField.get(pluginHandler);

            Map<String, Object> newPlugins = new HashMap<>(plugins);
            newPlugins.put("terminality-launch-plugin", new ServerSecurityPlugin());
            pluginMapField.set(pluginHandler, newPlugins);

            System.out.println("[Terminality] Interception4 injected into ModLauncher plugin map.");
        } catch (Throwable t) {
            System.err.println("[Terminality] Launch plugin injection failed.");
            t.printStackTrace();
        }
    }
}
