package survivalblock.rods_from_god.mixin.medusa;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

    @Inject(method = "canHit(Lnet/minecraft/entity/Entity;)Z", at = {@At("HEAD")}, cancellable = true)
    private void medusaStatueHitProjectile(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity living && RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue()) {
            cir.setReturnValue(true);
        }
    }
}
