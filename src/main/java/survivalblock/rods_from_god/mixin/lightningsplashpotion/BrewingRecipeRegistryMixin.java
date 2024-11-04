package survivalblock.rods_from_god.mixin.lightningsplashpotion;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

import java.util.Optional;

import static survivalblock.rods_from_god.common.init.RodsFromGodItems.LIGHTING_POTION_CONTENTS;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {

    @SuppressWarnings("UnreachableCode")
    @ModifyReturnValue(method = "craft", at = @At(value = "RETURN", ordinal = 2))
    private ItemStack changeSplashToThickOnly(ItemStack original) {
        if (original.isOf(RodsFromGodItems.LIGHTNING_SPLASH_POTION)) {
            var potionContents = original.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            int color = potionContents.getColor();
            if (LIGHTING_POTION_CONTENTS.customColor().isPresent()) {
                if (color == PotionContentsComponentAccessor.rods_from_god$getEffectlessColor()) {
                    color = LIGHTING_POTION_CONTENTS.customColor().get();
                } else {
                    color = ColorHelper.Argb.averageArgb(LIGHTING_POTION_CONTENTS.customColor().get(), color);
                }
            }
            original.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potionContents.potion(), Optional.of(color), potionContents.customEffects()));
        }
        return original;
    }
}
