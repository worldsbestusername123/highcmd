
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.highcmdforge.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.highcmdforge.client.renderer.TerminalRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HighCmdforgeModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(HighCmdforgeModEntities.TERMINAL.get(), TerminalRenderer::new);
	}
}
