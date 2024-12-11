package survivalblock.rods_from_god.common.init;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.block.ArchimedesLeverBlock;
import survivalblock.rods_from_god.common.block.SuperBouncySlimeBlock;

public class RodsFromGodBlocks {

    public static final Block SUPER_BOUNCY_SLIME_BLOCK = registerBlock("super_bouncy_slime_block", new SuperBouncySlimeBlock(Blocks.SLIME_BLOCK.getSettings()));
    public static final Block ARCHIMEDES_LEVER = registerBlock("archimedes_lever", new ArchimedesLeverBlock(AbstractBlock.Settings.create().noCollision().strength(0.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).pistonBehavior(PistonBehavior.DESTROY)));

    @SuppressWarnings("SameParameterValue")
    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, RodsFromGod.id(name), block);
    }

    public static void init() {

    }
}
