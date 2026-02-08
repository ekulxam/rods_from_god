package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.FabricDataPackGenerator;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;

public class RodsFromGodDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RodsFromGodEnUsLangGenerator::new);
		pack.addProvider(RodsFromGodDynamicRegistriesGenerator::new);
		pack.addProvider(RodsFromGodModelGenerator::new);
		pack.addProvider(RodsFromGodBlockLootTableGenerator::new);
		pack.addProvider(RodsFromGodRecipeGenerator::new);
		pack.addProvider(RodsFromGodTagGenerator.RodsFromGodDamageTypeTagGenerator::new);
		FabricDataGenerator.Pack datapackForDimensionTags = FabricDataPackGenerator.createBuiltinDataPack(fabricDataGenerator, RodsFromGod.ARCHIMEDES_LEVER_ALLOW_OVERWORLD);
		datapackForDimensionTags.addProvider(RodsFromGodTagGenerator.RodsFromGodWorldTagGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, RodsFromGodDamageTypes.registrant::bootstrap);
	}
}
