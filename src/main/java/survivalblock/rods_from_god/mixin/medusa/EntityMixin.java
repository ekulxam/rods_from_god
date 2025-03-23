package survivalblock.rods_from_god.mixin.medusa;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@SuppressWarnings("UnreachableCode")
@Mixin(value = Entity.class, priority = 2000)
public abstract class EntityMixin {

    @Shadow public abstract void setVelocity(Vec3d velocity);

    @ModifyReturnValue(method = "isInvulnerableTo", at = @At("RETURN"))
    private boolean medusaStatueImmune(boolean original, DamageSource source){
        if (!((Entity) (Object) this instanceof LivingEntity living)) {
            return original;
        }
        if (RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue()) {
            return !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY);
        }
        return original;
    }

    @Inject(method = "tickRiding", at = @At(value = "HEAD"), cancellable = true)
    private void medusaCancelTickRiding(CallbackInfo ci){
        if ((Entity) (Object) this instanceof LivingEntity living && RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue()) {
            this.setVelocity(Vec3d.ZERO);
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "isCollidable", at = @At("RETURN"))
    private boolean statuesAreCollidable(boolean original){
        //noinspection ConstantValue
        return original || ((Entity) (Object) this instanceof LivingEntity living && RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue());
    }
}
