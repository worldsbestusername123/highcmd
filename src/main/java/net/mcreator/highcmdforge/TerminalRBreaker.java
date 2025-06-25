package net.mcreator.highcmdforge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

import java.lang.instrument.ClassFileTransformer;

public class TerminalRBreaker implements ClassFileTransformer {
    public static void collapseGL() {
        try {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glClearColor(1F, 0F, 0F, 1F);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
            System.out.println("[Terminality] Terminality has injected onto OpenGL Rendering");
        } catch (Throwable t) {
            System.out.println("[Terminality] Failed to inject Terminality");
            t.printStackTrace();
        }
    }

    private static final class MinecraftRenderingFucker extends EntityRenderDispatcher {
        public MinecraftRenderingFucker(Minecraft minecraft, TextureManager textureManager, ItemRenderer itemRenderer,
                                        BlockRenderDispatcher blockRenderer, Font font, Options options,
                                        EntityModelSet entityModels) {
            super(minecraft, textureManager, itemRenderer, blockRenderer, font, options, entityModels);
        }


        @Override
        public boolean shouldRenderHitBoxes() {
            return false;
        }
    }
}
