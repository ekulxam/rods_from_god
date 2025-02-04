package survivalblock.rods_from_god.mixin.book;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.entity.EnchantedArrowEntity;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    private boolean doNotAddCritParticles(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        return !((PersistentProjectileEntity) (Object) this instanceof EnchantedArrowEntity);
    }
}
