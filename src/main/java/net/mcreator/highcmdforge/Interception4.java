package net.mcreator.highcmdforge;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.*;

public class Interception4 implements ILaunchPluginService {

    private static final Set<String> targets = Set.of(
            "net.minecraft.server.level.ServerLevel",
            "net.minecraft.client.renderer.GameRenderer"
    );

    @Override
    public String name() {
        return "terminality-launch-plugin";
    }

    @Override
    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        String name = classType.getClassName();
        return targets.contains(name) ? EnumSet.of(Phase.BEFORE) : EnumSet.noneOf(Phase.class);
    }

    @Override
    public boolean processClass(Phase phase, ClassNode classNode, Type classType, String reason) {
        String fullName = classType.getClassName();

        if (fullName.equals("net.minecraft.server.level.ServerLevel")) {
            for (MethodNode method : classNode.methods) {
                if (method.name.equals("<init>")) {
                    AbstractInsnNode target = null;
                    for (AbstractInsnNode insn : method.instructions) {
                        if (insn.getOpcode() == Opcodes.INVOKESPECIAL &&
                                insn instanceof MethodInsnNode minsn &&
                                minsn.name.equals("<init>")) {
                            target = insn.getNext();
                            break;
                        }
                    }
                    if (target != null) {
                        InsnList inject = new InsnList();
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        inject.add(new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "net/mcreator/highcmdforge/Interception5",
                                "purgeEntities",
                                "(Ljava/lang/Object;)V",
                                false
                        ));
                        method.instructions.insertBefore(target, inject);
                        System.out.println("[Terminality] Injected purgeEntities into ServerLevel.<init>");
                    }
                }
            }
        }

        if (fullName.equals("net.minecraft.client.renderer.GameRenderer")) {
            for (MethodNode method : classNode.methods) {
                if (method.name.equals("render") && method.desc.contains("F")) {
                    InsnList inject = new InsnList();
                    inject.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            "net/mcreator/highcmdforge/TerminalRBreaker",
                            "collapseGL",
                            "()V",
                            false
                    ));
                    method.instructions.insert(inject);
                    System.out.println("[Terminality] Injected TerminalRBreaker into GameRenderer.render()");
                }
            }
        }

        return false;
    }
}
