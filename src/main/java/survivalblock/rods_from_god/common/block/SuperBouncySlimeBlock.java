package survivalblock.rods_from_god.common.block;

import net.minecraft.block.SlimeBlock;
import net.minecraft.util.DyeColor;

public class SuperBouncySlimeBlock extends SlimeBlock {

    private final DyeColor dyeColor;

    public SuperBouncySlimeBlock(DyeColor dyeColor, Settings settings) {
        super(settings);
        this.dyeColor = dyeColor;
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }
}
