package net.mcreator.highcmdforge;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.*;

public class Interception2 implements IMixinConfigPlugin {

    private static final Set<String> ALLOWED_MIXINS = Set.of(
            "ABOOLEAN1", "ABOOLEAN2", "ABOOLEAN3", "ABOOLEAN5",
            "AResist", "AResist2", "AResist4", "AResist5",
            "AResist7", "AResist8", "AResistandBoolean",
            "AMixinBan", "BlockOthersRenderMixin", "FuckYouPig2"
    );

    @Override
    public void onLoad(String mixinPackage) {
        try {
            agent.Premain.attachAgent();
            System.out.println("[Terminal-Agent] Terminality ability activated.");
        } catch (Throwable e) {
            System.err.println("[Terminal-Agent] Failed to attach agent:");
            e.printStackTrace();
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String simpleName = mixinClassName.substring(mixinClassName.lastIndexOf('.') + 1);
        boolean allowed = ALLOWED_MIXINS.contains(simpleName);
        System.out.println("[Terminal-Interceptor] " + (allowed ? "ALLOWED" : "BLOCKED") + ": " + mixinClassName);
        return allowed;
    }

    @Override
    public List<String> getMixins() {
        return new ArrayList<>();
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        System.out.println("[Terminal-Interceptor] My targets: " + myTargets);
        System.out.println("[Terminal-Interceptor] Ignored targets: " + otherTargets);
    }

    @Override public String getRefMapperConfig() { return null; }

    @Override public void preApply(String targetClassName, ClassNode classNode, String mixinClassName, IMixinInfo info) {}

    @Override public void postApply(String targetClassName, ClassNode classNode, String mixinClassName, IMixinInfo info) {
        System.out.println("[Terminal-Interceptor] Applied: " + mixinClassName);
    }
}