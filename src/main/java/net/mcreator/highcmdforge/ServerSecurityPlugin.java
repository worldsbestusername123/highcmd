package net.mcreator.highcmdforge;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.*;

public class ServerSecurityPlugin implements ILaunchPluginService {

    private static final Set<String> PROTECTED_CLASSES = Set.of(
            "net.minecraft.server.level.ServerLevel",
            "net.minecraft.client.renderer.GameRenderer"
    );

    @Override
    public String name() {
        return "high-cmd-security-plugin";
    }

    @Override
    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        String name = classType.getClassName();
        return PROTECTED_CLASSES.contains(name) ? EnumSet.of(Phase.BEFORE) : EnumSet.noneOf(Phase.class);
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
                                "net/mcreator/highcmdforge/EntitySecurityManager",
                                "cleanupEntities",
                                "(Ljava/lang/Object;)V",
                                false
                        ));
                        method.instructions.insertBefore(target, inject);
                        System.out.println("[Terminal-Agent] Injected entity cleanup into ServerLevel.<init>");
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
