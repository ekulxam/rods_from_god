package survivalblock.rods_from_god.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;

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
        if (!world.isClient()) {
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
            }
            if (cooldownTicks > 0) user.getItemCooldownManager().set(this, cooldownTicks);
            world.spawnEntity(tungstenRod);
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    protected static BlockHitResult raycast(World world, PlayerEntity player) {
        Vec3d vec3d = player.getEyePos();
        Vec3d vec3d2 = vec3d.add(player.getRotationVector(player.getPitch(), player.getYaw()).multiply(Math.max(player.getBlockInteractionRange(), 4.5) * 256));
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
    }
}
