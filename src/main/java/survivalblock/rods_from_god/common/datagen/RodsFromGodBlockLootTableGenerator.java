package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.rods_from_god.common.init.RodsFromGodBlocks;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodBlockLootTableGenerator extends FabricBlockLootTableProvider {

    protected RodsFromGodBlockLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.addDrop(RodsFromGodBlocks.ARCHIMEDES_LEVER);
        this.addDrop(RodsFromGodBlocks.SYNTHESIS_TABLE);
        for (Block block : RodsFromGodBlocks.SUPER_BOUNCY_SLIME_BLOCKS.values()) {
            this.addDrop(block);
        }
    }
}
