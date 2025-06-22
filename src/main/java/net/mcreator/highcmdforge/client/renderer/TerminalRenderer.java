
package net.mcreator.highcmdforge.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

import net.mcreator.highcmdforge.entity.TerminalEntity;

public class TerminalRenderer extends HumanoidMobRenderer<TerminalEntity, HumanoidModel<TerminalEntity>> {
	public TerminalRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<TerminalEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(TerminalEntity entity) {
		return new ResourceLocation("high_cmdforge:textures/entities/error.png");
	}
}
