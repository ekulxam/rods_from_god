package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import survivalblock.rods_from_god.common.init.RodsFromGodBlocks;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

import java.util.function.BiConsumer;

public class RodsFromGodModelGenerator extends FabricModelProvider {

    public RodsFromGodModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(RodsFromGodBlocks.SUPER_BOUNCY_SLIME_BLOCK);
        excludeAndRegister(RodsFromGodBlocks.ARCHIMEDES_LEVER, blockStateModelGenerator, this::registerCustomLever);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(RodsFromGodItems.AIMING_DEVICE, Models.GENERATED);
        itemModelGenerator.register(RodsFromGodItems.SMOKE_BOMB, Models.GENERATED);
        itemModelGenerator.register(RodsFromGodItems.CORRUPTED_STAR_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(RodsFromGodItems.THE_ONE_WATCH, Models.GENERATED); // texture idea: clock/compass, but I put an eye and end portal texture in it
        itemModelGenerator.register(RodsFromGodItems.EVOKER_INVOKER, Models.GENERATED);
    }

    @SuppressWarnings("SameParameterValue")
    private void excludeAndRegister(Block block, BlockStateModelGenerator blockStateModelGenerator, BiConsumer<Block, BlockStateModelGenerator> biConsumer) {
        blockStateModelGenerator.excludeFromSimpleItemModelGeneration(block);
        biConsumer.accept(block, blockStateModelGenerator);
    }

    private void registerCustomLever(Block block, BlockStateModelGenerator blockStateModelGenerator) {
        Identifier identifier = ModelIds.getBlockModelId(block);
        Identifier identifier2 = ModelIds.getBlockSubModelId(block, "_on");
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block)
                        .coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.POWERED, identifier, identifier2))
                        .coordinate(BlockStateVariantMap.create(Properties.BLOCK_FACE, Properties.HORIZONTAL_FACING)
                                .register(BlockFace.CEILING,
                                        Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(BlockFace.CEILING,
                                        Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(BlockFace.CEILING, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180))
                                .register(BlockFace.CEILING,
                                        Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(BlockFace.FLOOR, Direction.NORTH, BlockStateVariant.create())
                                .register(BlockFace.FLOOR, Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(BlockFace.FLOOR, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(BlockFace.FLOOR, Direction.WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(BlockFace.WALL, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
                                .register(BlockFace.WALL,
                                        Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(BlockFace.WALL,
                                        Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(BlockFace.WALL,
                                        Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270))));
    }
}
