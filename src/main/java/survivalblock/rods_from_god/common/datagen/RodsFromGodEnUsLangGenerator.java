package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodEnUsLangGenerator extends FabricLanguageProvider {

    public RodsFromGodEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        // entity
        translationBuilder.add(RodsFromGodEntityTypes.TUNGSTEN_ROD, "Tungsten Rod");
        translationBuilder.add(RodsFromGodEntityTypes.SMOKE_BOMB, "Smoke Bomb");

        // damage
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion", "%1$s was struck by a kinetic bombardment");
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion.player", "%1$s was struck by a kinetic bombardment from %2$s");
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion.item", "%1$s was struck by a kinetic bombardment from %2$s using %3$s");

        // gamerules
        translationBuilder.add("gamerule.rodsFromGodKineticExplosionCanMakeFire", "Rods from God - Kinetic Explosions Can Produce Fire");
        translationBuilder.add("gamerule.rodsFromGodKineticExplosionSourceType", "Rods from God - Kinetic Explosion Source Type");

        // item
        translationBuilder.add(RodsFromGodItems.AIMING_DEVICE, "Rods from God - Aiming Device");
        translationBuilder.add(RodsFromGodItems.SMOKE_BOMB, "Smoke Bomb");
    }
}
