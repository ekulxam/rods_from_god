package survivalblock.rods_from_god.mixin.compat.lithium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.caffeinemc.mods.lithium.common.entity.EntityClassGroup;
import net.caffeinemc.mods.lithium.common.world.WorldHelper;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.component.cca.entity.StoneStatueComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(value = WorldHelper.class, remap = false)
public class WorldHelperMixin {

    @ModifyExpressionValue(method = {"getEntitiesForCollision", "getOtherEntitiesForCollision"}, at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/lithium/common/entity/EntityClassGroup$NoDragonClassGroup;BOAT_SHULKER_LIKE_COLLISION:Lnet/caffeinemc/mods/lithium/common/entity/EntityClassGroup$NoDragonClassGroup;"))
    private static EntityClassGroup.NoDragonClassGroup statuesHaveCollisionToo(EntityClassGroup.NoDragonClassGroup original) {
        return new EntityClassGroup.NoDragonClassGroup(((EntityClassGroupAccessor) original).rods_from_god$getClassAndTypeFixEvaluator()) {
            @Override
            public boolean contains(Entity entity) {
                return super.contains(entity) || RodsFromGodEntityComponents.STONE_STATUE.maybeGet(entity).map(StoneStatueComponent::isStatue).orElse(false);
            }
        };
    }
}
