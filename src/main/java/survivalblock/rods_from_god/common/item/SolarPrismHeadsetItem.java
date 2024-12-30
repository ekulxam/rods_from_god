package survivalblock.rods_from_god.common.item;

import com.google.common.base.Suppliers;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.component.item.SolarPrismHeadsetComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;

import java.util.List;
import java.util.function.Supplier;

public class SolarPrismHeadsetItem extends Item implements Equipment {

    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public SolarPrismHeadsetItem(Settings settings) {
        super(settings);
        this.attributeModifiers = Suppliers.memoize(() -> {
            RegistryEntry<ArmorMaterial> material = ArmorMaterials.DIAMOND;
            ArmorItem.Type type = ArmorItem.Type.HELMET;
            int i = material.value().getProtection(type);
            float f = material.value().toughness();
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());
            Identifier identifier = Identifier.ofVanilla("armor." + type.getName());
            builder.add(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, i, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            builder.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(identifier, f, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            float g = material.value().knockbackResistance();
            if (g > 0.0F) {
                builder.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(identifier, g, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            }

            return builder.build();
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        SolarPrismHeadsetComponent solarPrismHeadsetComponent = stack.getOrDefault(RodsFromGodDataComponentTypes.SOLAR_PRISM_HEADSET, SolarPrismHeadsetComponent.DEFAULT_INSTANCE);
        solarPrismHeadsetComponent.appendTooltip(context, tooltip::add, type);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.equipAndSwap(this, world, user, hand);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }
}
