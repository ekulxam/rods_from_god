package survivalblock.rods_from_god.mixin.lightningsplashpotion;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

@Mixin(value = LightningEntity.class, priority = 1500)
public abstract class LightningEntityMixin extends Entity {

    public LightningEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "floatValue=10000.0"))
    private float lightningGlobalSound(float original) {
        return RodsFromGodWorldComponents.WAS_DAY.get(this.getWorld()).getLightningVolume();
    }
}
