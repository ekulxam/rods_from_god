package survivalblock.rods_from_god.mixin.util;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.init.RodsFromGodTags;

@Mixin(value = PlayerEntity.class, priority = 500)
public class PlayerEntityMixin {

    @ModifyExpressionValue(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean bypassesCreativeMode(boolean original, DamageSource source, float amount) {
        return original || source.isIn(RodsFromGodTags.RodsFromGodDamageTypeTags.BYPASSES_CREATIVE);
    }

}
