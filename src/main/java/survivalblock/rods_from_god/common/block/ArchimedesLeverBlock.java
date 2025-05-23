package survivalblock.rods_from_god.common.block;

import com.mojang.serialization.MapCodec;

import java.util.List;
import java.util.UUID;

import net.minecraft.block.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodGameRules;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

import static survivalblock.rods_from_god.common.RodsFromGod.SkyNotTheLimit;
import static survivalblock.rods_from_god.common.init.RodsFromGodCommands.getIdFromServerWorld;

public class ArchimedesLeverBlock extends ExtendedLeverBlock {


    public static final MapCodec<ArchimedesLeverBlock> CODEC = createCodec(ArchimedesLeverBlock::new);

    public ArchimedesLeverBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArchimedesLeverBlock getInstance() {
        return this;
    }

    @Override
    public MapCodec<ArchimedesLeverBlock> getActualCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getGameRules().getBoolean(RodsFromGodGameRules.DISABLE_WORLD_LEVER) && player != null && !SkyNotTheLimit.equals(player.getUuid())) {
            if (world instanceof ServerWorld serverWorld) {
                player.damage(new DamageSource(serverWorld.atmospheric_api$getEntryFromKey(RegistryKeys.DAMAGE_TYPE, RodsFromGodDamageTypes.NO_TOUCHY)), 999999999);
                player.kill();
            }
            return ActionResult.FAIL;
        }
        if (world instanceof ServerWorld serverWorld) {
            RodsFromGod.LOGGER.info("Player {} toggled World Lever in dimension {}", player, getIdFromServerWorld(serverWorld));
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public void togglePower(BlockState state, World world, BlockPos pos, @Nullable PlayerEntity player) {
        super.togglePower(state, world, pos, player);
        if (world instanceof ServerWorld serverWorld) {
            RodsFromGodWorldComponents.WORLD_LEVER.get(serverWorld).setLifted(!state.get(POWERED));
        }
    }

    public void spawnCustomParticles(BlockState state, WorldAccess world, BlockPos pos, float alpha) {
        Direction direction = state.get(FACING).getOpposite();
        Direction direction2 = getDirection(state).getOpposite();
        double d = (double)pos.getX() + 0.5 + 0.1 * (double)direction.getOffsetX() + 0.2 * (double)direction2.getOffsetX();
        double e = (double)pos.getY() + 0.5 + 0.1 * (double)direction.getOffsetY() + 0.2 * (double)direction2.getOffsetY();
        double f = (double)pos.getZ() + 0.5 + 0.1 * (double)direction.getOffsetZ() + 0.2 * (double)direction2.getOffsetZ();
        world.addParticle(new DustParticleEffect(Vec3d.unpackRgb(15368284).toVector3f(), alpha), d, e, f, 0.0, 0.0, 0.0); // looks like amarong compat lol
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        tooltip.add(Text.translatable("block.rods_from_god.archimedes_lever.tooltip.0"));
        tooltip.add(Text.translatable("block.rods_from_god.archimedes_lever.tooltip.1"));
        tooltip.add(Text.translatable("block.rods_from_god.archimedes_lever.tooltip.2"));
        tooltip.add(Text.translatable("block.rods_from_god.archimedes_lever.tooltip.3"));
    }
}
