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
import survivalblock.rods_from_god.common.component.cca.entity.DeathExplosionComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

@SuppressWarnings("UnreachableCode")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract boolean damage(DamageSource source, float amount);

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
