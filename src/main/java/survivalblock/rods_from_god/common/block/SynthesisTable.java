package survivalblock.rods_from_god.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodBlocks;
import survivalblock.rods_from_god.mixin.synthesistable.CraftingScreenHandlerAccessor;

public class SynthesisTable extends CraftingTableBlock {

    public static final Text TITLE = Text.translatable("container.crafting");
    public static final MapCodec<CraftingTableBlock> CODEC = createCodec(SynthesisTable::new);

    public SynthesisTable(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends CraftingTableBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new SynthesisScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE
        );
    }

    public static class SynthesisScreenHandler extends CraftingScreenHandler {

        public static final ScreenHandlerType<SynthesisScreenHandler> TYPE = Registry.register(Registries.SCREEN_HANDLER, RodsFromGod.id("synthesis_screen_handler"), new ScreenHandlerType<>(SynthesisScreenHandler::new, FeatureSet.empty()));
        public static final int CRAFTING_SLOT_COUNT = 1 + 25;
        public static final int MAX_SIDE_LENGTH = 5;
        public static final int RESULT_SLOT_X_OFFSET = 15;
        public static final int RECIPE_BOOK_Y_OFFSET = 18;

        public static boolean isSynthesisAndNotRegularCrafting = false;

        public SynthesisScreenHandler(int syncId, PlayerInventory playerInventory) {
            super(wrapSyncId(syncId), playerInventory);
        }

        public SynthesisScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
            super(wrapSyncId(syncId), playerInventory, context);
        }

        private static int wrapSyncId(int syncId) {
            isSynthesisAndNotRegularCrafting = true;
            return syncId;
        }

        @Override
        public boolean canUse(PlayerEntity player) {
            return canUse(((CraftingScreenHandlerAccessor) this).rods_from_god$getContext(), player, RodsFromGodBlocks.SYNTHESIS_TABLE);
        }

        @Override
        public int getCraftingSlotCount() {
            return CRAFTING_SLOT_COUNT;
        }

        public static void init() {

        }
    }
}
