package survivalblock.rods_from_god.common.init;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.block.ArchimedesLeverBlock;
import survivalblock.rods_from_god.common.block.SuperBouncySlimeBlock;
import survivalblock.rods_from_god.common.block.SynthesisTable;

import java.util.LinkedHashMap;

public class RodsFromGodBlocks {

    public static final LinkedHashMap<DyeColor, SuperBouncySlimeBlock> SUPER_BOUNCY_SLIME_BLOCKS = new LinkedHashMap<>();

    public static final Block SYNTHESIS_TABLE = registerBlock("synthesis_table", new SynthesisTable(Blocks.CRAFTING_TABLE.getSettings()));
    public static final ArchimedesLeverBlock ARCHIMEDES_LEVER = registerBlock("archimedes_lever", new ArchimedesLeverBlock(AbstractBlock.Settings.create().noCollision().strength(0.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).pistonBehavior(PistonBehavior.DESTROY)));

    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> T registerBlock(String name, T block) {
        return Registry.register(Registries.BLOCK, RodsFromGod.id(name), block);
    }

    public static void init() {

    }

    static {
        for (DyeColor dyeColor : DyeColor.values()) {
            AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.SLIME_BLOCK);
            SuperBouncySlimeBlock bouncy = registerBlock(dyeColor.getName() + "_super_bouncy_slime_block", new SuperBouncySlimeBlock(dyeColor, settings));
            SUPER_BOUNCY_SLIME_BLOCKS.put(dyeColor, bouncy);
        }
    }
}
