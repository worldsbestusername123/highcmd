package net.mcreator.highcmdforge.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.Entity;


@Mixin(MobRenderer.class)
public class BlockOthersRenderMixin {

    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    private void blockNonGodRender(net.minecraft.world.entity.Mob mob, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        String className = mob.getClass().getSimpleName();
        if (!className.contains("highcmd")) {
            System.out.println("[Interceptor] BLOCKED RENDER: " + className);
            ci.cancel();
        }
    }
}