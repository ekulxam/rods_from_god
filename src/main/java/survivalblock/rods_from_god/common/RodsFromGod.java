package survivalblock.rods_from_god.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.atmosphere.atmospheric_api.not_mixin.AtmosphericAPI;
import survivalblock.rods_from_god.client.networking.TheOneWatchComponentC2SPayload;
import survivalblock.rods_from_god.common.component.TheOneWatchComponent;
import survivalblock.rods_from_god.common.init.*;
import survivalblock.rods_from_god.common.recipe.AimingDeviceFireRecipe;
import survivalblock.rods_from_god.common.recipe.AimingDeviceUndoFireRecipe;

public class RodsFromGod implements ModInitializer {

	public static final String MOD_ID = "rods_from_god";
	public static final Logger LOGGER = LoggerFactory.getLogger("Rods from God");

	@Override
	public void onInitialize() {
		AtmosphericAPI.resetIsConnectorLoaded();
		RodsFromGodEntityTypes.init();
		RodsFromGodSoundEvents.init();
		RodsFromGodGameRules.init();
		RodsFromGodDataComponentTypes.init();
		RodsFromGodBlocks.init();
		RodsFromGodItems.init();
		RodsFromGodStatusEffects.init();
		RodsFromGodStatusEffects.RodsFromGodPotions.init(); // I don't understand how classloading works here; is this even necessary?
		AimingDeviceFireRecipe.init();
		AimingDeviceUndoFireRecipe.init();
		PayloadTypeRegistry.playC2S().register(TheOneWatchComponentC2SPayload.ID, TheOneWatchComponentC2SPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(TheOneWatchComponentC2SPayload.ID, (payload, context) -> {
			Entity entity = context.player().getWorld().getEntityById(payload.entityId());
			if (entity instanceof PlayerEntity player) {
				// hmm, I just realized that players might be able to send payloads with entity ids that belong to other players
				ItemStack stack = player.getInventory().getStack(payload.slot());
				if (!stack.isOf(RodsFromGodItems.THE_ONE_WATCH)) {
					return;
				}
				stack.set(RodsFromGodDataComponentTypes.THE_ONE_WATCH, new TheOneWatchComponent(payload.subcommand(), payload.arguments()));
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}