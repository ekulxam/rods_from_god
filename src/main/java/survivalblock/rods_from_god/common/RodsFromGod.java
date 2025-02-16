package survivalblock.rods_from_god.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.atmosphere.atmospheric_api.not_mixin.AtmosphericAPI;
import survivalblock.atmosphere.atmospheric_api.not_mixin.render.screenshake.ScreenShakePreventerRegistry;
import survivalblock.atmosphere.atmospheric_api.not_mixin.resource.AtmosphericResourceManagerHelper;
import survivalblock.rods_from_god.client.networking.BookTargetC2SPayload;
import survivalblock.rods_from_god.client.networking.TheOneWatchComponentC2SPayload;
import survivalblock.rods_from_god.common.block.SynthesisTable;
import survivalblock.rods_from_god.common.compat.config.RodsFromGodConfig;
import survivalblock.rods_from_god.common.init.RodsFromGodCommands;
import survivalblock.rods_from_god.common.init.*;
import survivalblock.rods_from_god.common.recipe.AimingDeviceFireRecipe;
import survivalblock.rods_from_god.common.recipe.AimingDeviceUndoFireRecipe;

import java.util.Objects;

public class RodsFromGod implements ModInitializer {

	public static final String MOD_ID = "rods_from_god";
	public static final Logger LOGGER = LoggerFactory.getLogger("Rods from God");

	public static final Identifier ARCHIMEDES_LEVER_ALLOW_OVERWORLD = RodsFromGod.id("archimedes_lever_allow_lifting_overworld");
	public static final String ARCHIMEDES_LEVER_SCREENSHAKE_REASON = "archimedes_lever_world_lifting";

	public static boolean shouldDoConfig = false;
	public static boolean configLoaded = false;

	@Override
	public void onInitialize() {
		AtmosphericAPI.resetIsConnectorLoaded();
		shouldDoConfig = FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
		if (shouldDoConfig) {
			configLoaded = RodsFromGodConfig.load();
			if (!configLoaded) {
				RodsFromGod.LOGGER.warn("Rods from God Config could not be loaded!");
			}
		}

		ScreenShakePreventerRegistry.ALLOW_SHAKING.register((screenShaker) -> {
			if (Objects.equals(MOD_ID, screenShaker.getModId()) && Objects.equals(ARCHIMEDES_LEVER_SCREENSHAKE_REASON, screenShaker.getReason())) {
				return RodsFromGodConfig.allowScreenShakingForArchimedesLever();
			}
			return true;
		});

		RodsFromGodEntityTypes.init();
		RodsFromGodSoundEvents.init();
		RodsFromGodGameRules.init();
		RodsFromGodDataComponentTypes.init();
		RodsFromGodBlocks.init();
		RodsFromGodItems.init();
		RodsFromGodStatusEffects.init();
		RodsFromGodStatusEffects.RodsFromGodPotions.init(); // I don't understand how classloading works here; is this even necessary?
		RodsFromGodCommands.init();

		SynthesisTable.SynthesisScreenHandler.init();
		AimingDeviceFireRecipe.init();
		AimingDeviceUndoFireRecipe.init();

		PayloadTypeRegistry.playC2S().register(TheOneWatchComponentC2SPayload.ID, TheOneWatchComponentC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(BookTargetC2SPayload.ID, BookTargetC2SPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(TheOneWatchComponentC2SPayload.ID, TheOneWatchComponentC2SPayload.Receiver.INSTANCE);
		ServerPlayNetworking.registerGlobalReceiver(BookTargetC2SPayload.ID, BookTargetC2SPayload.Receiver.INSTANCE);

		FabricLoader.getInstance().getModContainer(MOD_ID)
				.ifPresent(RodsFromGod::registerBuiltinDataPacks);

		ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, server) ->
				server.getWorlds().forEach(RodsFromGodWorldComponents.WORLD_LEVER::sync));
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static void registerBuiltinDataPacks(ModContainer modContainer) {
		AtmosphericResourceManagerHelper.registerBuiltinDataPack(ARCHIMEDES_LEVER_ALLOW_OVERWORLD, modContainer, Text.translatable("dataPack.rods_from_god.archimedes_lever_allow_lifting_overworld.name"), ResourcePackActivationType.DEFAULT_ENABLED);
	}
}