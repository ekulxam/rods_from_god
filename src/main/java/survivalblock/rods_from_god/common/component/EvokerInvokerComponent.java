package survivalblock.rods_from_god.common.component;

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
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;
import java.util.function.Consumer;

import static survivalblock.rods_from_god.common.item.EvokerInvokerItem.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class EvokerInvokerComponent implements TooltipAppender {

    private final Optional<Integer> cooldown;
    private final Optional<Integer> maxSets;
    private final Optional<Double> maxDistance;

    public static final EvokerInvokerComponent DEFAULT_INSTANCE = new EvokerInvokerComponent(120, 3, 45d);

    public static final Codec<EvokerInvokerComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codecs.NONNEGATIVE_INT.optionalFieldOf("cooldown").forGetter((component) -> component.cooldown),
                            Codecs.NONNEGATIVE_INT.optionalFieldOf("maxSets").forGetter((component) -> component.maxSets),
                            Codec.DOUBLE.optionalFieldOf("maxDistance").forGetter((component) -> component.maxDistance)
                    )
                    .apply(instance, EvokerInvokerComponent::new)
    );
    public static final PacketCodec<RegistryByteBuf, EvokerInvokerComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT.collect(PacketCodecs::optional), ((component) -> component.cooldown),
            PacketCodecs.VAR_INT.collect(PacketCodecs::optional), ((component) -> component.maxSets),
            PacketCodecs.DOUBLE.collect(PacketCodecs::optional), ((component) -> component.maxDistance),
            EvokerInvokerComponent::new
    );

    public EvokerInvokerComponent(int cooldown, int maxSets, double maxDistance) {
        this.cooldown = Optional.of(cooldown);
        this.maxSets = Optional.of(maxSets);
        this.maxDistance = Optional.of(maxDistance);
    }

    public EvokerInvokerComponent(Optional<Integer> cooldown, Optional<Integer> maxSets, Optional<Double> maxDistance) {
        this.cooldown = cooldown;
        this.maxSets = maxSets;
        this.maxDistance = maxDistance;
    }

    public int cooldown() {
        return cooldown.orElse(DEFAULT_COOLDOWN_TICKS);
    }

    public int maxSets() {
        return maxSets.orElse(DEFAULT_MAX_SETS);
    }

    public double maxDistance() {
        return maxDistance.orElse(DEFAULT_MAX_DISTANCE);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof EvokerInvokerComponent evokerInvokerComponent) {
            return (this.cooldown() == evokerInvokerComponent.cooldown())
                    && (this.maxSets() == this.maxSets())
                    && (this.maxDistance() == this.maxDistance());
        }
        return false;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        tooltip.accept(Text.translatable("item.rods_from_god.evoker_invoker.evoker_invoker_cooldown", cooldown()).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("item.rods_from_god.evoker_invoker.evoker_invoker_max_sets", maxSets()).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("item.rods_from_god.evoker_invoker.evoker_invoker_max_distance", maxDistance()).formatted(Formatting.GRAY));
    }
}
