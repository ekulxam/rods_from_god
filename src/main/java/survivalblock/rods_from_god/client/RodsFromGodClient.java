package survivalblock.rods_from_god.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import survivalblock.rods_from_god.client.entity.OctagonalPrismEntityModel;
import survivalblock.rods_from_god.client.entity.OctagonalPrismEntityRenderer;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityTypes;

public class RodsFromGodClient implements ClientModInitializer {

	public static final EntityModelLayer TUNGSTEN_ROD = new EntityModelLayer(RodsFromGod.id("tungsten_rod"), "main");

	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(TUNGSTEN_ROD, OctagonalPrismEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(RodsFromGodEntityTypes.TUNGSTEN_ROD,
				(ctx -> new OctagonalPrismEntityRenderer(ctx, new OctagonalPrismEntityModel(ctx.getPart(TUNGSTEN_ROD)), 0.625f)));
		EntityRendererRegistry.register(RodsFromGodEntityTypes.SMOKE_BOMB, FlyingItemEntityRenderer::new);
	}
}