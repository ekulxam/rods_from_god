package survivalblock.rods_from_god.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.item.EvokerInvokerComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;

import java.util.Comparator;
import java.util.List;

public class EvokerInvokerItem extends Item {

    public static final int DEFAULT_COOLDOWN_TICKS = 120;
    public static final int DEFAULT_MAX_SETS = 3;
    public static final double DEFAULT_MAX_DISTANCE = 45;

    protected boolean warned = false;

    public EvokerInvokerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            EvokerInvokerComponent evokerInvokerComponent = stack.getOrDefault(RodsFromGodDataComponentTypes.EVOKER_INVOKER, EvokerInvokerComponent.DEFAULT_INSTANCE);
            Box box = user.getBoundingBox();
            TargetPredicate targetPredicate = TargetPredicate.createAttackable();
            Vec3d userPos = new Vec3d(user.getX(), user.getY(), user.getZ());
            List<LivingEntity> livings = world.getEntitiesByClass(LivingEntity.class, box.expand(evokerInvokerComponent.maxDistance()), (entity) -> entity.isAlive() && !entity.getType().equals(EntityType.ARMOR_STAND) && targetPredicate.test(user, entity));
            try {
                final Comparator<LivingEntity> sortByClosestLivingEntity = (first, second) -> (int) (100d * ((first.squaredDistanceTo(userPos) - second.squaredDistanceTo(userPos))));
                livings.sort(sortByClosestLivingEntity);
            } catch (RuntimeException runtimeException) {
                if (!warned) {
                    RodsFromGod.LOGGER.error("An error occurred when attempting to sort a list!", runtimeException);
                    warned = true;
                }
            }
            int fangSets = evokerInvokerComponent.maxSets();
            for (int i = 0; i < fangSets; i++) {
                if (livings.isEmpty()) {
                    break;
                }
                LivingEntity livingEntity = livings.getFirst();
                if (livingEntity == null) {
                    break;
                }
                if (i == 0) {
                    // no use doing cooldown and checking for more entities if there is no entity in range
                    int cooldownTicks = evokerInvokerComponent.cooldown();
                    if (cooldownTicks > 0) user.getItemCooldownManager().set(this, cooldownTicks);
                }
                castSpell(user, livingEntity);
                livings.remove(livingEntity);
            }
        }
        return TypedActionResult.consume(stack);
    }

    /* copy + pasting
    yeah, yeah, strike me down for being a hypocrite, whatever
    I don't have a lot of time to do mixin hacks for this, okay?
     */
    public static void castSpell(@NotNull PlayerEntity player, @NotNull LivingEntity livingEntity) {
        double d = Math.min(livingEntity.getY(), player.getY());
        double e = Math.max(livingEntity.getY(), player.getY()) + 1.0;
        float f = (float) MathHelper.atan2(livingEntity.getZ() - player.getZ(), livingEntity.getX() - player.getX());
        double squaredHorizontalDistance = getHorizontalSquaredDistanceTo(player.getPos(), livingEntity.getPos());
        if (squaredHorizontalDistance < 9.0) {
            for (int i = 0; i < 5; i++) {
                float g = f + (float)i * (float) Math.PI * 0.4F;
                conjureFangs(player, player.getX() + (double)MathHelper.cos(g) * 1.5, player.getZ() + (double)MathHelper.sin(g) * 1.5, d, e, g, 0);
            }
            for (int i = 0; i < 8; i++) {
                float g = f + (float)i * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
                conjureFangs(player, player.getX() + (double)MathHelper.cos(g) * 2.5, player.getZ() + (double)MathHelper.sin(g) * 2.5, d, e, g, 3);
            }
        } else {
            for (int i = 0; i * i < squaredHorizontalDistance; i++) {
                double h = 1.25 * (double)(i + 1);
                conjureFangs(player, player.getX() + (double)MathHelper.cos(f) * h, player.getZ() + (double)MathHelper.sin(f) * h, d, e, f, i);
            }
        }
    }

    public static double getHorizontalSquaredDistanceTo(Vec3d vec3d, Vec3d vec3d2) {
        double xDist = vec3d2.getX() - vec3d.getX();
        double zDist = vec3d2.getZ() - vec3d.getZ();
        return xDist * xDist + zDist * zDist;
    }

    public static void conjureFangs(PlayerEntity player, double x, double z, double maxY, double y, float yaw, int warmup) {
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        boolean bl = false;
        double d = 0.0;
        World world = player.getWorld();
        do {
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = world.getBlockState(blockPos2);
            if (blockState.isSideSolidFullSquare(world, blockPos2, Direction.UP)) {
                if (!world.isAir(blockPos)) {
                    BlockState blockState2 = world.getBlockState(blockPos);
                    VoxelShape voxelShape = blockState2.getCollisionShape(world, blockPos);
                    if (!voxelShape.isEmpty()) {
                        d = voxelShape.getMax(Direction.Axis.Y);
                    }
                }
                bl = true;
                break;
            }

            blockPos = blockPos.down();
        } while (blockPos.getY() >= MathHelper.floor(maxY) - 1);

        if (bl) {
            world.spawnEntity(new EvokerFangsEntity(world, x, (double)blockPos.getY() + d, z, yaw, warmup, player));
            world.emitGameEvent(GameEvent.ENTITY_PLACE, new Vec3d(x, (double)blockPos.getY() + d, z), GameEvent.Emitter.of(player));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        EvokerInvokerComponent evokerInvokerComponent = stack.getOrDefault(RodsFromGodDataComponentTypes.EVOKER_INVOKER, EvokerInvokerComponent.DEFAULT_INSTANCE);
        evokerInvokerComponent.appendTooltip(context, tooltip::add, type);
    }
}
