package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.FabricDamageTypeProvider;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RodsFromGodDamageTypeGenerator extends FabricDamageTypeProvider {

    public RodsFromGodDamageTypeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : RodsFromGodDamageTypes.asDamageTypes().entrySet()) {
            entries.add(entry.getKey(), entry.getValue());
        }
    }
}
