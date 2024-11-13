package survivalblock.rods_from_god.mixin.corruptedstarfragment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.rods_from_god.common.component.DeathExplosionComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

@SuppressWarnings("UnreachableCode")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @ModifyReturnValue(method = "tryUseTotem", at = @At(value = "RETURN", ordinal = 1))
    private boolean supernovae(boolean original, DamageSource source) {
        if (original) {
            return true;
        }
        if (!((LivingEntity) (Object) this instanceof PlayerEntity player)) {
            return false;
        }
        ItemStack itemStack = null;
        for (Hand hand : Hand.values()) {
            ItemStack itemStack2 = this.getStackInHand(hand);
            if (itemStack2.isOf(RodsFromGodItems.CORRUPTED_STAR_FRAGMENT)) {
                itemStack = itemStack2.copy();
                if (!itemStack2.getOrDefault(RodsFromGodDataComponentTypes.KEEP_CORRUPTED_STAR_FRAGMENT, false)) {
                    itemStack2.decrement(1);
                }
                break;
            }
        }
        boolean emptyStack = itemStack == null || itemStack.isEmpty();
        if (!emptyStack) {
            this.setHealth(1.0F);
            if (this.getWorld() instanceof ServerWorld) {
                RodsFromGodEntityComponents.DEATH_EXPLOSION.get(player).setup(true, source);
            }
            return true;
        }
        return false;
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void cancelDamageIfExploding(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!((LivingEntity) (Object) this instanceof PlayerEntity player)) {
            return;
        }
        if (amount == Float.MAX_VALUE) {
            return;
        }
        DeathExplosionComponent deathExplosionComponent = RodsFromGodEntityComponents.DEATH_EXPLOSION.get(player);
        if (deathExplosionComponent.shouldExplodeOnDeath()) {
            cir.setReturnValue(false);
        }
    }
}
