package survivalblock.rods_from_god.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.*;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.item.AimingDeviceComponent;
import survivalblock.rods_from_god.common.component.item.EvokerInvokerComponent;
import survivalblock.rods_from_god.common.component.item.TheOneWatchComponent;
import survivalblock.rods_from_god.common.item.*;

import java.util.List;
import java.util.Optional;

public class RodsFromGodItems {

    public static final PotionContentsComponent LIGHTING_POTION_CONTENTS = new PotionContentsComponent(Optional.empty(), Optional.of(10724049), List.of());

    public static final Item AIMING_DEVICE = registerItem("aiming_device", new AimingDeviceItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).component(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE)));
    public static final Item SMOKE_BOMB = registerItem("smoke_bomb", new SmokeBombItem(new Item.Settings().maxCount(64)));
    public static final Item LIGHTNING_SPLASH_POTION = registerItem("lightning_splash_potion", new LightningSplashPotionItem(new Item.Settings().maxCount(1).component(DataComponentTypes.POTION_CONTENTS, LIGHTING_POTION_CONTENTS)));
    public static final Item SUPER_BOUNCY_SLIME_BLOCK = registerBlockItem(RodsFromGodBlocks.SUPER_BOUNCY_SLIME_BLOCK, new Item.Settings());
    public static final Item CORRUPTED_STAR_FRAGMENT = registerItem("corrupted_star_fragment", new CorruptedStarFragmentItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)));
    public static final Item THE_ONE_WATCH = registerItem("the_one_watch", new OneWatchToRuleThemAllItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).component(RodsFromGodDataComponentTypes.THE_ONE_WATCH, TheOneWatchComponent.DEFAULT_INSTANCE)));
    public static final Item EVOKER_INVOKER = registerItem("evoker_invoker", new EvokerInvokerItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).component(RodsFromGodDataComponentTypes.EVOKER_INVOKER, EvokerInvokerComponent.DEFAULT_INSTANCE)));
    public static final Item SOLAR_PRISM_HEADSET = registerItem("solar_prism_headset", new SolarPrismHeadsetItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    @SuppressWarnings("unused")
    public static final Item ARCHIMEDES_LEVER = registerBlockItem(RodsFromGodBlocks.ARCHIMEDES_LEVER, new Item.Settings().rarity(Rarity.EPIC));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, RodsFromGod.id(name), item);
    }

    @SuppressWarnings("SameParameterValue")
    private static Item registerBlockItem(Block block, Item.Settings settings) {
        BlockItem item = new BlockItem(block, settings);
        Identifier id = Registries.BLOCK.getId(block);
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        return Registry.register(Registries.ITEM, id, item);
    }

    @SuppressWarnings("CodeBlock2Expr")
    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(AIMING_DEVICE);
            entries.add(SMOKE_BOMB);
            entries.add(CORRUPTED_STAR_FRAGMENT);
            entries.add(EVOKER_INVOKER);
            entries.add(SOLAR_PRISM_HEADSET);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(LIGHTNING_SPLASH_POTION);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.SLIME_BLOCK, SUPER_BOUNCY_SLIME_BLOCK);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.addAfter(Items.LEVER, ARCHIMEDES_LEVER);
        });
        /*
        DO NOT add dispenser behavior for the solar prism headset because mobs should not be wearing it, only players should
        Also, forcing a Curse of Binding solar prism headset into someone's helmet slot is somewhat evil
         */
        DispenserBlock.registerProjectileBehavior(LIGHTNING_SPLASH_POTION);
        DispenserBlock.registerProjectileBehavior(SMOKE_BOMB);
        // smiling_imp emoji - I'm having too much fun
        FabricBrewingRecipeRegistryBuilder.BUILD.register(RodsFromGodItems::addCustomPotionRecipes);
    }

    public static void addCustomPotionRecipes(BrewingRecipeRegistry.Builder builder) {
        // Lightning Splash Potion
        builder.registerItemRecipe(Items.SPLASH_POTION, Items.LIGHTNING_ROD, LIGHTNING_SPLASH_POTION);

        // Gravity Increase - amplifier -> increased amplifier
        builder.registerPotionRecipe(Potions.THICK, Items.ANCIENT_DEBRIS, RodsFromGodStatusEffects.RodsFromGodPotions.GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.STRONG_GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.VERY_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.VERY_STRONG_GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.VERY_VERY_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.VERY_VERY_STRONG_GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.VERY_VERY_VERY_STRONG_GRAVITY_INCREASE);

        // Gravity Increase - amplifier -> long duration, amplifier
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.GRAVITY_INCREASE, Items.REDSTONE, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.STRONG_GRAVITY_INCREASE, Items.REDSTONE, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.VERY_STRONG_GRAVITY_INCREASE, Items.REDSTONE, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.VERY_VERY_STRONG_GRAVITY_INCREASE, Items.REDSTONE, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_VERY_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.VERY_VERY_VERY_STRONG_GRAVITY_INCREASE, Items.REDSTONE, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_VERY_VERY_STRONG_GRAVITY_INCREASE);

        // Gravity Increase - long duration, amplifier -> long duration, increased amplifier
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.LONG_GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.LONG_STRONG_GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_STRONG_GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_VERY_STRONG_GRAVITY_INCREASE);
        builder.registerPotionRecipe(RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_VERY_STRONG_GRAVITY_INCREASE, Items.GLOWSTONE_DUST, RodsFromGodStatusEffects.RodsFromGodPotions.LONG_VERY_VERY_VERY_STRONG_GRAVITY_INCREASE);
    }
}
