package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.AtmosphericDynamicRegistryProvider;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.RegistryEntryLookupContainer;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodDamageTypeGenerator extends AtmosphericDynamicRegistryProvider<DamageType> {

    public RodsFromGodDamageTypeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries, RegistryEntryLookupContainer container) {
        RodsFromGodDamageTypes.asDamageTypes().forEach(entries::add);
    }
}
