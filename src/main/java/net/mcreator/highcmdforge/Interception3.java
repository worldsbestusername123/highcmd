package net.mcreator.highcmdforge;

import agent.Premain;
import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.*;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;

import java.lang.reflect.Field;
import java.util.*;

public class Interception3 implements ITransformationService {

    @Override
    public String name() {
        return "terminality-launch-plugin";
    }
    static {
        LaunchPluginHandler handler = Helper.getFieldValue(Launcher.INSTANCE, "launchPlugins", LaunchPluginHandler.class);
        Map<String, ILaunchPluginService> plugins = (Map<String, ILaunchPluginService>) Helper.getFieldValue(handler, "plugins", Map.class);
        Map<String, ILaunchPluginService> newMap = new HashMap<>();
        newMap.put("terminality-launch-plugin", new Interception4(){
        });
        if (plugins != null) for (String name : plugins.keySet())
            newMap.put(name, plugins.get(name));
        Helper.setFieldValue(handler, "plugins", newMap);
        Helper.coexistenceCoreAndMod();
    }

    @Override
    public void initialize(IEnvironment environment) {
        System.out.println("[Terminality-AI] TransformationService initialized early");
        Premain.attachAgent();
    }

    @Override public void onLoad(IEnvironment environment, Set<String> otherServices) {}
    @Override public List<Resource> beginScanning(IEnvironment env) { return List.of(); }
    @Override public List<ITransformer> transformers() { return List.of(); }

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
            newPlugins.put("terminality-launch-plugin", new Interception4());
            pluginMapField.set(pluginHandler, newPlugins);

            System.out.println("[Terminality] Interception4 injected into ModLauncher plugin map.");
        } catch (Throwable t) {
            System.err.println("[Terminality] Launch plugin injection failed.");
            t.printStackTrace();
        }
    }
}
