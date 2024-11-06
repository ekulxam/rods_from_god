package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodStatusEffects {

    public static final RegistryEntry<StatusEffect> GRAVITY_INCREASE = register(
            "gravity_increase",
            new RodsFromGodStatusEffect(StatusEffectCategory.HARMFUL, 12895428)
                    .addAttributeModifier(EntityAttributes.GENERIC_GRAVITY, Identifier.ofVanilla("effect.gravity_increase"), 0.2F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    @SuppressWarnings("SameParameterValue")
    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, RodsFromGod.id(id), statusEffect);
    }

    public static class RodsFromGodPotions {
        public static final RegistryEntry<Potion> GRAVITY_INCREASE = register("gravity_increase", new Potion(new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 3600)));
        public static final RegistryEntry<Potion> STRONG_GRAVITY_INCREASE = register("strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 1800, 1)));
        public static final RegistryEntry<Potion> VERY_STRONG_GRAVITY_INCREASE = register("very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 900, 2)));
        public static final RegistryEntry<Potion> VERY_VERY_STRONG_GRAVITY_INCREASE = register("very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 450, 3)));
        public static final RegistryEntry<Potion> VERY_VERY_VERY_STRONG_GRAVITY_INCREASE = register("very_very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 225, 4)));

        public static final RegistryEntry<Potion> LONG_GRAVITY_INCREASE = register("long_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 7200)));
        public static final RegistryEntry<Potion> LONG_STRONG_GRAVITY_INCREASE = register("long_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 3600, 1)));
        public static final RegistryEntry<Potion> LONG_VERY_STRONG_GRAVITY_INCREASE = register("long_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 1800, 2)));
        public static final RegistryEntry<Potion> LONG_VERY_VERY_STRONG_GRAVITY_INCREASE = register("long_very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 900, 3)));
        public static final RegistryEntry<Potion> LONG_VERY_VERY_VERY_STRONG_GRAVITY_INCREASE = register("long_very_very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 450, 4)));

        private static RegistryEntry<Potion> register(String name, Potion potion) {
            return Registry.registerReference(Registries.POTION, RodsFromGod.id(name), potion);
        }

        public static void init() {

        }
    }

    public static void init() {

    }

    public static class RodsFromGodStatusEffect extends StatusEffect {

        public RodsFromGodStatusEffect(StatusEffectCategory category, int color) {
            super(category, color);
        }
    }
}
