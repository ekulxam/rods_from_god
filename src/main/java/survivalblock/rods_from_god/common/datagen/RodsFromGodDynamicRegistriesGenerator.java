package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.AtmosphericDynamicRegistriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RodsFromGodDynamicRegistriesGenerator extends AtmosphericDynamicRegistriesProvider {

    public RodsFromGodDynamicRegistriesGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Set.of(RegistryKeys.DAMAGE_TYPE), registriesFuture);
    }
}
