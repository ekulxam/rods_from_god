package survivalblock.rods_from_god.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.client.entity.OctagonalPrismEntityModel;
import survivalblock.rods_from_god.client.entity.OctagonalPrismEntityRenderer;
import survivalblock.rods_from_god.client.entity.SmallPlaneEntityModel;
import survivalblock.rods_from_god.client.entity.SmallPlaneEntityRenderer;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.TickSubcommand;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

public class RodsFromGodClient implements ClientModInitializer {

	public static final EntityModelLayer TUNGSTEN_ROD = new EntityModelLayer(RodsFromGod.id("tungsten_rod"), "main");
	public static final EntityModelLayer ROD_LANDING_MARKER = new EntityModelLayer(RodsFromGod.id("rod_landing_marker"), "main");

	public static final Identifier SMOKE_SCREEN_OVERLAY = RodsFromGod.id("textures/misc/smoke_screen_overlay.png");
	public static final Identifier SOLAR_LASER_OVERHEAT_OVERLAY = RodsFromGod.id("textures/misc/solar_laser_overheat_overlay.png");

	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(TUNGSTEN_ROD, OctagonalPrismEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(RodsFromGodEntityTypes.TUNGSTEN_ROD,
				(ctx -> new OctagonalPrismEntityRenderer(ctx, new OctagonalPrismEntityModel(ctx.getPart(TUNGSTEN_ROD)), 0.625f)));
		EntityRendererRegistry.register(RodsFromGodEntityTypes.SMOKE_BOMB, FlyingItemEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ROD_LANDING_MARKER, SmallPlaneEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(RodsFromGodEntityTypes.ROD_LANDING_MARKER,
				(ctx -> new SmallPlaneEntityRenderer(ctx, new SmallPlaneEntityModel(ctx.getPart(ROD_LANDING_MARKER)), 0.5f)));
		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
			if (itemStack.isOf(RodsFromGodItems.THE_ONE_WATCH)) {
				if (Screen.hasShiftDown()) {
					for (int i = 0; i < 8; i++) list.add(Text.translatable("item.rods_from_god.the_one_watch.lore." + i).formatted(Formatting.GRAY));
				} else {
					list.add(Text.translatable("item.rods_from_god.the_one_watch.screen.subcommand." + itemStack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_SUBCOMMAND, TickSubcommand.QUERY.getName())).formatted(Formatting.GOLD));
					list.add(Text.translatable("item.rods_from_god.the_one_watch.arguments", itemStack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_ARGUMENTS, "")).formatted(Formatting.GRAY));
					list.add(Text.translatable("item.rods_from_god.the_one_watch.lore.hidden").formatted(Formatting.DARK_GRAY));
				}
			}
		});
	}
}