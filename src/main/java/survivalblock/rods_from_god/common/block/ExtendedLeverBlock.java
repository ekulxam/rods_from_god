package survivalblock.rods_from_god.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.LeverBlock;

public abstract class ExtendedLeverBlock extends LeverBlock {

    protected final MapCodec<LeverBlock> codec = getActualCodec().xmap(extendedLever -> extendedLever, lever -> getInstance());

    public ExtendedLeverBlock(Settings settings) {
        super(settings);
    }

    public abstract <T extends ExtendedLeverBlock> T getInstance();

    public abstract MapCodec<? extends ExtendedLeverBlock> getActualCodec();

    @Override
    public MapCodec<LeverBlock> getCodec() {
        return this.codec;
    }
}
