package survivalblock.rods_from_god.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SplashPotionItem;

public class LightningSplashPotionItem extends SplashPotionItem {
    public LightningSplashPotionItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getTranslationKey(); // removes the .effect.<insert effect here> from the name
    }
}
