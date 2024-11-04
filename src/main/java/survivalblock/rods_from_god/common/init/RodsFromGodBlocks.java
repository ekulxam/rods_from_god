package survivalblock.rods_from_god.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.SuperBouncySlimeBlock;

public class RodsFromGodBlocks {

    public static final Block SUPER_BOUNCY_SLIME_BLOCK = registerBlock("super_bouncy_slime_block", new SuperBouncySlimeBlock(Blocks.SLIME_BLOCK.getSettings()));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, RodsFromGod.id(name), block);
    }

    public static void init() {

    }
}
