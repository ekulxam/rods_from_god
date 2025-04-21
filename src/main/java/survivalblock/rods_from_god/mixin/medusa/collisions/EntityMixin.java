package survivalblock.rods_from_god.mixin.medusa.collisions;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(Entity.class)
public class EntityMixin {

    @ModifyReturnValue(method = "isCollidable", at = @At("RETURN"))
    private boolean statuesAreCollidable(boolean original){
        //noinspection ConstantValue
        return original || ((Entity) (Object) this instanceof LivingEntity living && RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue());
    }
}
