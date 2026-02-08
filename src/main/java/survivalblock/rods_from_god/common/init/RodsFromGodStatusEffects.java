package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.PotionRegistrant;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.StatusEffectRegistrant;
import survivalblock.rods_from_god.common.RodsFromGod;

@SuppressWarnings("UnstableApiUsage")
public class RodsFromGodStatusEffects {
    public static final StatusEffectRegistrant registrant = new StatusEffectRegistrant(RodsFromGod::id);
    public static final RegistryEntry<StatusEffect> GRAVITY_INCREASE = registrant.registerReference(
            "gravity_increase",
            new RodsFromGodStatusEffect(StatusEffectCategory.HARMFUL, 12895428)
                    .addAttributeModifier(EntityAttributes.GENERIC_GRAVITY, Identifier.ofVanilla("effect.gravity_increase"), 0.2F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void init() {

    }

    public static class RodsFromGodPotions {
        public static final PotionRegistrant registrant = new PotionRegistrant(RodsFromGod::id);
        public static final RegistryEntry<Potion> GRAVITY_INCREASE = registrant.registerReference("gravity_increase", new Potion(new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 3600)));
        public static final RegistryEntry<Potion> STRONG_GRAVITY_INCREASE = registrant.registerReference("strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 1800, 1)));
        public static final RegistryEntry<Potion> VERY_STRONG_GRAVITY_INCREASE = registrant.registerReference("very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 900, 2)));
        public static final RegistryEntry<Potion> VERY_VERY_STRONG_GRAVITY_INCREASE = registrant.registerReference("very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 450, 3)));
        public static final RegistryEntry<Potion> VERY_VERY_VERY_STRONG_GRAVITY_INCREASE = registrant.registerReference("very_very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 225, 4)));

        public static final RegistryEntry<Potion> LONG_GRAVITY_INCREASE = registrant.registerReference("long_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 7200)));
        public static final RegistryEntry<Potion> LONG_STRONG_GRAVITY_INCREASE = registrant.registerReference("long_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 3600, 1)));
        public static final RegistryEntry<Potion> LONG_VERY_STRONG_GRAVITY_INCREASE = registrant.registerReference("long_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 1800, 2)));
        public static final RegistryEntry<Potion> LONG_VERY_VERY_STRONG_GRAVITY_INCREASE = registrant.registerReference("long_very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 900, 3)));
        public static final RegistryEntry<Potion> LONG_VERY_VERY_VERY_STRONG_GRAVITY_INCREASE = registrant.registerReference("long_very_very_very_strong_gravity_increase", new Potion("gravity_increase", new StatusEffectInstance(RodsFromGodStatusEffects.GRAVITY_INCREASE, 450, 4)));

        public static void init() {

        }
    }

    public static class RodsFromGodStatusEffect extends StatusEffect {
        public RodsFromGodStatusEffect(StatusEffectCategory category, int color) {
            super(category, color);
        }
    }
}
