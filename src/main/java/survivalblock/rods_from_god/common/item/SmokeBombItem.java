package survivalblock.rods_from_god.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.entity.SmokeBombEntity;
import survivalblock.rods_from_god.common.init.RodsFromGodSoundEvents;

public class SmokeBombItem extends Item implements ProjectileItem {

    public SmokeBombItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                RodsFromGodSoundEvents.SMOKE_BOMB_THROW,
                SoundCategory.PLAYERS,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (!world.isClient) {
            SmokeBombEntity smokeBombEntity = new SmokeBombEntity(world, user);
            smokeBombEntity.setItem(itemStack);
            smokeBombEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0F, 1.4F, 1.0F);
            world.spawnEntity(smokeBombEntity);
            user.getItemCooldownManager().set(this, 80);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        SmokeBombEntity smokeBombEntity = new SmokeBombEntity(world, pos.getX(), pos.getY(), pos.getZ());
        smokeBombEntity.setItem(stack);
        return smokeBombEntity;
    }

    @Override
    public ProjectileItem.Settings getProjectileSettings() {
        return ProjectileItem.Settings.builder()
                .uncertainty(ProjectileItem.Settings.DEFAULT.uncertainty() * 0.5F)
                .power(ProjectileItem.Settings.DEFAULT.power() * 2.5F)
                .build();
    }
}
