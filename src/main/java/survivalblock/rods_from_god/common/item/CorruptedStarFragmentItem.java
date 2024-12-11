package survivalblock.rods_from_god.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;

import java.util.List;

public class CorruptedStarFragmentItem extends Item {
    public CorruptedStarFragmentItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (stack.getOrDefault(RodsFromGodDataComponentTypes.KEEP_CORRUPTED_STAR_FRAGMENT, false)) {
            tooltip.add(Text.translatable("item.rods_from_god.corrupted_star_fragment.keep_corrupted_star_fragment").formatted(Formatting.DARK_GRAY));
        }
    }
}
