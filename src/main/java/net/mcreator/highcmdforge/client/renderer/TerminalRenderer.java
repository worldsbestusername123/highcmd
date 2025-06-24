
package net.mcreator.highcmdforge.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

import net.mcreator.highcmdforge.entity.TerminalEntity;
import org.jetbrains.annotations.NotNull;

public class TerminalRenderer extends HumanoidMobRenderer<TerminalEntity, HumanoidModel<TerminalEntity>> {
	public TerminalRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull TerminalEntity entity) {
		return ResourceLocation.parse("high_cmdforge:textures/entities/error.png");
	}
}
