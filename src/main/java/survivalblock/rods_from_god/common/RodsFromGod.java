package survivalblock.rods_from_god.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.rods_from_god.common.init.*;

public class RodsFromGod implements ModInitializer {

	public static final String MOD_ID = "rods_from_god";
	public static final Logger LOGGER = LoggerFactory.getLogger("Rods from God");

	@Override
	public void onInitialize() {
		RodsFromGodEntityTypes.init();
		RodsFromGodSoundEvents.init();
		RodsFromGodGameRules.init();
		RodsFromGodDataComponentTypes.init();
		RodsFromGodBlocks.init();
		RodsFromGodItems.init();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}