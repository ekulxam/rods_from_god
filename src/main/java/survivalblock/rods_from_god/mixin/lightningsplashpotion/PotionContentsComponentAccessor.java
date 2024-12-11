package survivalblock.rods_from_god.mixin.lightningsplashpotion;

import net.minecraft.component.type.PotionContentsComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PotionContentsComponent.class)
public interface PotionContentsComponentAccessor {

    @Accessor("EFFECTLESS_COLOR")
    static int rods_from_god$getEffectlessColor() {
        throw new UnsupportedOperationException();
    }
}
