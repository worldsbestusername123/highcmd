package net.mcreator.highcmdforge;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.highcmdforge.client.renderer.TerminalRenderer;
import net.mcreator.highcmdforge.entity.TerminalEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

// gob-style rendering
public class TerminalEntityLevelRenderer extends LevelRenderer {
    private Minecraft mc;
    private HumanoidModel<TerminalEntity> terminalModel;

    public TerminalEntityLevelRenderer(Minecraft pMinecraft, EntityRenderDispatcher pEntityRenderDispatcher, BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, RenderBuffers pRenderBuffers) {
        super(pMinecraft, pEntityRenderDispatcher, pBlockEntityRenderDispatcher, pRenderBuffers);

        // absolutely important
        this.mc = pMinecraft;
        setLevel(mc.level);

        // haphazard(?, i have no idea what haphazard means) way to retrieve the model
        for (EntityRenderer renderer : pEntityRenderDispatcher.renderers.values())
        {
            if (renderer instanceof TerminalRenderer terminalRenderer)
                terminalModel = terminalRenderer.getModel();
        }
    }

    // override the rendering logic to make way for your own
    // noteable issue: disappears in F1
    @Override
    public void renderLevel(PoseStack pPoseStack, float pTick, long pFinishNanoTime, boolean pRenderBlockOutline, Camera pCamera, GameRenderer pGameRenderer, LightTexture pLightTexture, Matrix4f pProjectionMatrix) {
        // normal, god-fearing level rendering
        super.renderLevel(pPoseStack, pTick, pFinishNanoTime, pRenderBlockOutline, pCamera, pGameRenderer, pLightTexture, pProjectionMatrix);

        // get important data for rendering
        Vec3 vec3 = pCamera.getPosition();
        Entity entity = pCamera.getEntity();

        // push a new pose so the rendering won't collapse in on itself
        pPoseStack.pushPose();

        // get position of the player, render your entity where it should be, and neatly interpolate between ticks to fake smoothness
        pPoseStack.translate(-Mth.lerp(pTick, entity.getX(), vec3.x), -Mth.lerp(pTick, entity.getY(), vec3.y), -Mth.lerp(pTick, entity.getZ(), vec3.z));

        MultiBufferSource.BufferSource source = mc.renderBuffers().bufferSource();

        // attach the terminal texture
        RenderSystem.setShaderTexture(0, ResourceLocation.parse("high_cmdforge:textures/entities/error.png"));

        // get the vertex consumer and tick count for rendering
        VertexConsumer vc = source.getBuffer(RenderType.solid());
        int tick = getTicks();

        // render each part
        terminalModel.head.render(pPoseStack, vc, tick, 1);
        terminalModel.body.render(pPoseStack, vc, tick, 1);
        terminalModel.leftLeg.render(pPoseStack, vc, tick, 1);
        terminalModel.leftArm.render(pPoseStack, vc, tick, 1);
        terminalModel.rightLeg.render(pPoseStack, vc, tick, 1);
        terminalModel.rightArm.render(pPoseStack, vc, tick, 1);

        // pop the pose to prevent any later rendering from accumulating the position of the player
        pPoseStack.popPose();

    }
}
