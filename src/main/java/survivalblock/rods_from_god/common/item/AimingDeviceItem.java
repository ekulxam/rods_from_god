package survivalblock.rods_from_god.common.item;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.entity.RodLandingMarkerEntity;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;

import java.util.List;

import static survivalblock.rods_from_god.common.entity.TungstenRodEntity.*;

public class AimingDeviceItem extends Item {
    public static final int DEFAULT_COOLDOWN_TICKS = 200;

    public AimingDeviceItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = AimingDeviceItem.raycast(world, user);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(stack);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(stack);
        }
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (world instanceof ServerWorld serverWorld) {
            TungstenRodEntity tungstenRod = new TungstenRodEntity(world, Vec3d.ofCenter(blockPos).add(0, 400,0));
            int cooldownTicks;
            if (stack.isEmpty()) {
                cooldownTicks = DEFAULT_COOLDOWN_TICKS;
            } else {
                cooldownTicks = stack.getComponents().getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_COOLDOWN, DEFAULT_COOLDOWN_TICKS);
                tungstenRod.setMaxExplosions(stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_MAX_EXPLOSIONS, DEFAULT_MAX_EXPLOSIONS));
                tungstenRod.setExplosionPower(stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_EXPLOSION_POWER, DEFAULT_EXPLOSION_POWER));
                tungstenRod.setInverseExplosionDamageFactor(stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_INVERSE_EXPLOSION_DAMAGE_FACTOR, DEFAULT_INVERSE_EXPLOSION_DAMAGE_FACTOR));
                tungstenRod.setCreatesFire(stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_CREATES_FIRE, DEFAULT_FIRE_BOOLEAN_VALUE));
                AttributeContainer attributes = tungstenRod.getAttributes();
                if (attributes == null) {
                    tungstenRod.resetAttributes();
                }
                // if this is null at this point, we have a problem
                //noinspection DataFlowIssue
                attributes.getCustomInstance(EntityAttributes.GENERIC_GRAVITY).setBaseValue(stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_GRAVITY, DEFAULT_GRAVITY));
                //noinspection DataFlowIssue
                attributes.getCustomInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_SCALE, DEFAULT_SCALE));
            }
            if (cooldownTicks > 0) user.getItemCooldownManager().set(this, cooldownTicks);
            world.spawnEntity(tungstenRod);
            RodLandingMarkerEntity rodLandingMarker = new RodLandingMarkerEntity(serverWorld, Vec3d.ofCenter(blockPos).add(0, 1.1, 0), tungstenRod.getUuid());
            rodLandingMarker.setYaw(MathHelper.nextBetween(serverWorld.getRandom(), 0, 360));
            world.spawnEntity(rodLandingMarker);
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    public static BlockHitResult raycast(World world, PlayerEntity player) {
        Vec3d vec3d = player.getEyePos();
        Vec3d vec3d2 = vec3d.add(player.getRotationVector(player.getPitch(), player.getYaw()).multiply(Math.max(player.getBlockInteractionRange(), 4.5) * 256));
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_CREATES_FIRE, DEFAULT_FIRE_BOOLEAN_VALUE)) tooltip.add(Text.translatable("item.rods_from_god.aiming_device.aiming_device_creates_fire").withColor(16742418)); // orange
        tooltip.add(Text.translatable("item.rods_from_god.aiming_device.aiming_device_cooldown", stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_COOLDOWN, DEFAULT_COOLDOWN_TICKS)).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.rods_from_god.aiming_device.aiming_device_max_explosions", stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_MAX_EXPLOSIONS, DEFAULT_MAX_EXPLOSIONS)).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.rods_from_god.aiming_device.aiming_device_explosion_power", stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_EXPLOSION_POWER, DEFAULT_EXPLOSION_POWER)).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.rods_from_god.aiming_device.aiming_device_inverse_explosion_damage_factor", stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_INVERSE_EXPLOSION_DAMAGE_FACTOR, DEFAULT_INVERSE_EXPLOSION_DAMAGE_FACTOR)).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.rods_from_god.aiming_device.aiming_device_gravity", stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_GRAVITY, DEFAULT_GRAVITY)).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.rods_from_god.aiming_device.aiming_device_scale", stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE_SCALE, DEFAULT_SCALE)).formatted(Formatting.GRAY));
    }
}
