package survivalblock.rods_from_god.mixin.lightningsplashpotion.client;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

@Mixin(ItemColors.class)
public class ItemColorsMixin {

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/item/ItemColors;register(Lnet/minecraft/client/color/item/ItemColorProvider;[Lnet/minecraft/item/ItemConvertible;)V", ordinal = 4), index = 1)
    private static ItemConvertible[] addLightningPotionColors(ItemConvertible[] items) {
        ItemConvertible[] addLightningPotion = new ItemConvertible[items.length + 1];
        System.arraycopy(items, 0, addLightningPotion, 0, items.length);
        addLightningPotion[addLightningPotion.length - 1] = RodsFromGodItems.LIGHTNING_SPLASH_POTION;
        return addLightningPotion;
    }
}
