package survivalblock.rods_from_god.common.block;

import com.mojang.serialization.MapCodec;

import java.util.List;
import java.util.function.BiConsumer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import survivalblock.atmosphere.atmospheric_api.not_mixin.screenshake.ScreenShakeS2CPayload;
import survivalblock.rods_from_god.common.component.WorldLeverComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

public class ArchimedesLeverBlock extends WallMountedBlock {

    public static final MapCodec<ArchimedesLeverBlock> CODEC = createCodec(ArchimedesLeverBlock::new);
    public static final BooleanProperty ACTIVATED = Properties.POWERED;
    protected static final VoxelShape NORTH_WALL_SHAPE = Block.createCuboidShape(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
    protected static final VoxelShape SOUTH_WALL_SHAPE = Block.createCuboidShape(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
    protected static final VoxelShape WEST_WALL_SHAPE = Block.createCuboidShape(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
    protected static final VoxelShape EAST_WALL_SHAPE = Block.createCuboidShape(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
    protected static final VoxelShape FLOOR_Z_AXIS_SHAPE = Block.createCuboidShape(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
    protected static final VoxelShape FLOOR_X_AXIS_SHAPE = Block.createCuboidShape(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
    protected static final VoxelShape CEILING_Z_AXIS_SHAPE = Block.createCuboidShape(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
    protected static final VoxelShape CEILING_X_AXIS_SHAPE = Block.createCuboidShape(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

    public MapCodec<ArchimedesLeverBlock> getCodec() {
        return CODEC;
    }

    public ArchimedesLeverBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(ACTIVATED, false).with(FACE, BlockFace.WALL));
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACE)) {
            case FLOOR -> switch (state.get(FACING).getAxis()) {
                case X -> FLOOR_X_AXIS_SHAPE;
                default -> FLOOR_Z_AXIS_SHAPE;
            };
            case WALL -> switch (state.get(FACING)) {
                case EAST -> EAST_WALL_SHAPE;
                case WEST -> WEST_WALL_SHAPE;
                case SOUTH -> SOUTH_WALL_SHAPE;
                default -> NORTH_WALL_SHAPE;
            };
            default -> switch (state.get(FACING).getAxis()) {
                case X -> CEILING_X_AXIS_SHAPE;
                default -> CEILING_Z_AXIS_SHAPE;
            };
        };
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            BlockState blockState = state.cycle(ACTIVATED);
            if (blockState.get(ACTIVATED)) {
                spawnParticles(blockState, world, pos, 1.0F);
            }

            return ActionResult.SUCCESS;
        } else {
            this.togglePower(state, world, pos, null);
            return ActionResult.CONSUME;
        }
    }

    protected void onExploded(BlockState state, World world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
        if (explosion.canTriggerBlocks()) {
            this.togglePower(state, world, pos, null);
        }

        super.onExploded(state, world, pos, explosion, stackMerger);
    }

    public void togglePower(BlockState state, World world, BlockPos pos, @Nullable PlayerEntity player) {
        state = state.cycle(ACTIVATED);
        world.setBlockState(pos, state, 3);
        this.updateNeighbors(state, world, pos);
        playClickSound(player, world, pos, state);
        world.emitGameEvent(player, state.get(ACTIVATED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        if (world instanceof ServerWorld serverWorld) {
            WorldLeverComponent worldLeverComponent = RodsFromGodWorldComponents.WORLD_LEVER.get(world);
            if (worldLeverComponent.lifted() == state.get(ACTIVATED)) {
                return;
            }
            worldLeverComponent.setLifted(state.get(ACTIVATED));
            for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                ServerPlayNetworking.send(serverPlayer, new ScreenShakeS2CPayload(0.65f, WorldLeverComponent.MAX_CELESTIAL_ZOOM));
            }
        }
    }

    protected static void playClickSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state) {
        float f = state.get(ACTIVATED) ? 0.6F : 0.5F;
        world.playSound(player, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
    }

    private static void spawnParticles(BlockState state, WorldAccess world, BlockPos pos, float alpha) {
        Direction direction = state.get(FACING).getOpposite();
        Direction direction2 = getDirection(state).getOpposite();
        double d = (double)pos.getX() + 0.5 + 0.1 * (double)direction.getOffsetX() + 0.2 * (double)direction2.getOffsetX();
        double e = (double)pos.getY() + 0.5 + 0.1 * (double)direction.getOffsetY() + 0.2 * (double)direction2.getOffsetY();
        double f = (double)pos.getZ() + 0.5 + 0.1 * (double)direction.getOffsetZ() + 0.2 * (double)direction2.getOffsetZ();
        world.addParticle(new DustParticleEffect(Vec3d.unpackRgb(15368284).toVector3f(), alpha), d, e, f, 0.0, 0.0, 0.0); // looks like amarong compat lol
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ACTIVATED) && random.nextFloat() < 0.25F) {
            spawnParticles(state, world, pos, 0.5F);
        }
    }

    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            if (state.get(ACTIVATED)) {
                this.updateNeighbors(state, world, pos);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(ACTIVATED) ? 15 : 0;
    }

    protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(ACTIVATED) && getDirection(state) == direction ? 15 : 0;
    }

    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    private void updateNeighbors(BlockState state, World world, BlockPos pos) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(getDirection(state).getOpposite()), this);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACE, FACING, ACTIVATED);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        tooltip.add(Text.translatable("block.rods_from_god.archimedes_lever.tooltip"));
    }
}
