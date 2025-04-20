package survivalblock.rods_from_god.mixin.compat.lithium;

import net.caffeinemc.mods.lithium.common.entity.EntityClassGroup;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

@Mixin(value = EntityClassGroup.class, remap = false)
public interface EntityClassGroupAccessor {

    @Accessor("classAndTypeFitEvaluator")
    BiPredicate<Class<?>, Supplier<EntityType<?>>> rods_from_god$getClassAndTypeFixEvaluator();
}
