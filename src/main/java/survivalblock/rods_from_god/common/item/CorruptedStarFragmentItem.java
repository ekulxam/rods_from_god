package survivalblock.rods_from_god.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import survivalblock.atmosphere.atmospheric_api.not_mixin.item.ItemOfUndying;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

import java.util.List;

public class CorruptedStarFragmentItem extends Item implements ItemOfUndying {

    public CorruptedStarFragmentItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean shouldDecrementOnDeathCancel(LivingEntity living, ItemStack stack, int amount, DamageSource source) {
        return !stack.getOrDefault(RodsFromGodDataComponentTypes.KEEP_CORRUPTED_STAR_FRAGMENT, false);
    }

    @Override
    public boolean shouldIncrementStatAndTriggerCriteria(LivingEntity living, ItemStack stack, DamageSource source) {
        return false;
    }

    @Override
    public boolean canUse(LivingEntity living, ItemStack stack, DamageSource source) {
        return living instanceof PlayerEntity;
    }

    @Override
    public void activate(LivingEntity living, ItemStack stack, DamageSource source) {
        living.setHealth(1.0F);
        if (living.getWorld() instanceof ServerWorld) {
            RodsFromGodEntityComponents.DEATH_EXPLOSION.get(living).setup(true, source);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (stack.getOrDefault(RodsFromGodDataComponentTypes.KEEP_CORRUPTED_STAR_FRAGMENT, false)) {
            tooltip.add(Text.translatable("item.rods_from_god.corrupted_star_fragment.keep_corrupted_star_fragment").formatted(Formatting.DARK_GRAY));
        }
    }
}
