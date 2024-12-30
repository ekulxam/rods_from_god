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
import survivalblock.rods_from_god.common.TickSubcommand;

import java.util.Objects;
import java.util.function.Consumer;

public record TheOneWatchComponent(String subcommand, String arguments) implements TooltipAppender {

    public static final TheOneWatchComponent DEFAULT_INSTANCE = new TheOneWatchComponent(TickSubcommand.QUERY.getName(), "");

    public static final Codec<TheOneWatchComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.STRING.fieldOf("subcommand").forGetter(TheOneWatchComponent::subcommand),
                            Codec.STRING.fieldOf("arguments").forGetter(TheOneWatchComponent::arguments)
                    )
                    .apply(instance, TheOneWatchComponent::new)
    );
    public static final PacketCodec<RegistryByteBuf, TheOneWatchComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, TheOneWatchComponent::subcommand,
            PacketCodecs.STRING, TheOneWatchComponent::arguments,
            TheOneWatchComponent::new
    );

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TheOneWatchComponent oneWatchComponent) {
            return (Objects.equals(this.subcommand(), oneWatchComponent.subcommand()))
                    && (Objects.equals(this.arguments(), oneWatchComponent.arguments()));
        }
        return false;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        tooltip.accept(Text.translatable("item.rods_from_god.the_one_watch.screen.subcommand." + subcommand).formatted(Formatting.GOLD));
        tooltip.accept(Text.translatable("item.rods_from_god.the_one_watch.arguments", arguments).formatted(Formatting.GRAY));
    }
}