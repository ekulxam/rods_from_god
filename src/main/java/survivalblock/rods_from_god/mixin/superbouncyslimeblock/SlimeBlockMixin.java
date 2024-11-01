package survivalblock.rods_from_god.mixin.superbouncyslimeblock;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.SlimeBlock;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import survivalblock.rods_from_god.common.SuperBouncySlimeBlock;

@Debug(export = true)
@SuppressWarnings("UnreachableCode")
@Mixin(SlimeBlock.class)
public class SlimeBlockMixin {

    @ModifyArg(method = "bounce", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(DDD)V"), index = 1)
    private double reachForTheSky(double original, @Local Vec3d vec3d, @Local(ordinal = 0) double d) {
        if ((SlimeBlock) (Object) this instanceof SuperBouncySlimeBlock) {
            // modified from 23w13a_or_b bouncy castle option
            double e = -vec3d.y;
            double f = Math.max(0, e - 3);
            e = Math.max(f * 5.5, e) * 2;
            return d * e;
        }
        return original;
    }
}
