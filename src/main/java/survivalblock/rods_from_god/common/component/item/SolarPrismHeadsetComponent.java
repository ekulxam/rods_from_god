package survivalblock.rods_from_god.common.component.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SolarPrismHeadsetComponent implements TooltipAppender {

    private final Optional<Boolean> noOverheat;
    private final Optional<Boolean> alwaysActive;

    public static final SolarPrismHeadsetComponent DEFAULT_INSTANCE = new SolarPrismHeadsetComponent(false, false);

    public static final Codec<SolarPrismHeadsetComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.BOOL.optionalFieldOf("noOverheat").forGetter((component) -> component.noOverheat),
                            Codec.BOOL.optionalFieldOf("alwaysActive").forGetter((component) -> component.alwaysActive)
                    )
                    .apply(instance, SolarPrismHeadsetComponent::new)
    );

    public static final PacketCodec<RegistryByteBuf, SolarPrismHeadsetComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL.collect(PacketCodecs::optional), ((component) -> component.noOverheat),
            PacketCodecs.BOOL.collect(PacketCodecs::optional), ((component) -> component.alwaysActive),
            SolarPrismHeadsetComponent::new
    );

    public SolarPrismHeadsetComponent(boolean noOverheat, boolean alwaysActive) {
        this(Optional.of(noOverheat), Optional.of(alwaysActive));
    }

    public SolarPrismHeadsetComponent(Optional<Boolean> noOverheat, Optional<Boolean> alwaysActive) {
        this.noOverheat = noOverheat;
        this.alwaysActive = alwaysActive;
    }

    public boolean noOverheat() {
        return noOverheat.orElse(false);
    }

    public boolean alwaysActive() {
        return alwaysActive.orElse(false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SolarPrismHeadsetComponent solarPrismHeadsetComponent) {
            return (this.noOverheat() == solarPrismHeadsetComponent.noOverheat())
                    && (this.alwaysActive() == solarPrismHeadsetComponent.alwaysActive());
        }
        return false;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (noOverheat()) tooltip.accept(Text.translatable("item.rods_from_god.solar_prism_headset.no_overheat").formatted(Formatting.GOLD));
        if (alwaysActive()) tooltip.accept(Text.translatable("item.rods_from_god.solar_prism_headset.always_active", alwaysActive()).formatted(Formatting.LIGHT_PURPLE));
    }
}
