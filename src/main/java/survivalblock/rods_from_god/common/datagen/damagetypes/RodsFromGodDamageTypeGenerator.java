package survivalblock.rods_from_god.common.datagen.damagetypes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;

import java.util.Map;

public class RodsFromGodDamageTypeGenerator extends FabricDamageTypeProvider {

    public RodsFromGodDamageTypeGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    protected void setup(FabricDamageTypesContainer damageTypesContainer) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : RodsFromGodDamageTypes.asDamageTypes().entrySet()) {
            damageTypesContainer.add(entry.getKey().getValue().getPath(), entry.getValue());
        }
    }
}
