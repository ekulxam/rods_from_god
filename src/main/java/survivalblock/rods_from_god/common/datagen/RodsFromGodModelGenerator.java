package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import survivalblock.rods_from_god.common.init.RodsFromGodBlocks;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

public class RodsFromGodModelGenerator extends FabricModelProvider {
    public RodsFromGodModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(RodsFromGodBlocks.SUPER_BOUNCY_SLIME_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(RodsFromGodItems.AIMING_DEVICE, Models.GENERATED);
        itemModelGenerator.register(RodsFromGodItems.SMOKE_BOMB, Models.GENERATED);
        itemModelGenerator.register(RodsFromGodItems.CORRUPTED_STAR_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(RodsFromGodItems.THE_ONE_WATCH, Models.GENERATED); // texture idea: clock/compass, but I put an eye and end portal texture in it
    }
}
