package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.init.RodsFromGodTags;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodTagGenerator {

    public static class RodsFromGodItemTagGenerator extends FabricTagProvider.ItemTagProvider {

        public RodsFromGodItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("chronoception", "stopwatch"));
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("shattered_stopwatch", "stopwatch"));
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("rewindwatch", "rewind_watch"));
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("unstable_timepiece", "unstable_timepiece"));
        }
    }
}
