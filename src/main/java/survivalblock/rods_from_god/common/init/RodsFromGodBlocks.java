package survivalblock.rods_from_god.common.init;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.BlockRegistrant;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.block.ArchimedesLeverBlock;
import survivalblock.rods_from_god.common.block.SuperBouncySlimeBlock;
import survivalblock.rods_from_god.common.block.SynthesisTable;

import java.util.LinkedHashMap;

@SuppressWarnings("UnstableApiUsage")
public class RodsFromGodBlocks {
    public static final BlockRegistrant registrant = new BlockRegistrant(RodsFromGod::id);

    public static final LinkedHashMap<DyeColor, SuperBouncySlimeBlock> SUPER_BOUNCY_SLIME_BLOCKS = new LinkedHashMap<>();

    public static final Block SYNTHESIS_TABLE = registrant.register("synthesis_table", SynthesisTable::new, Blocks.CRAFTING_TABLE.getSettings());
    public static final ArchimedesLeverBlock ARCHIMEDES_LEVER = registrant.register("archimedes_lever", ArchimedesLeverBlock::new, AbstractBlock.Settings.create().noCollision().strength(0.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).pistonBehavior(PistonBehavior.DESTROY));

    public static void init() {

    }

    static {
        DyeColor[] dyeColors = DyeColor.values();
        for (int i = 0; i < 16; i++) {
            DyeColor dyeColor = dyeColors[i];
            AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.SLIME_BLOCK);
            SuperBouncySlimeBlock bouncy = registrant.register(dyeColor.getName() + "_super_bouncy_slime_block", new SuperBouncySlimeBlock(dyeColor, settings));
            SUPER_BOUNCY_SLIME_BLOCKS.put(dyeColor, bouncy);
        }
    }
}
