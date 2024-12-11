package survivalblock.rods_from_god.client.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import survivalblock.rods_from_god.common.RodsFromGod;

public record TheOneWatchComponentC2SPayload(String subcommand, String arguments, int slot, int entityId) implements CustomPayload {
    public static final CustomPayload.Id<TheOneWatchComponentC2SPayload> ID = new Id<>(RodsFromGod.id("the_one_watch_c2s_payload"));
    public static final PacketCodec<RegistryByteBuf, TheOneWatchComponentC2SPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, TheOneWatchComponentC2SPayload::subcommand,
            PacketCodecs.STRING, TheOneWatchComponentC2SPayload::arguments,
            PacketCodecs.VAR_INT, TheOneWatchComponentC2SPayload::slot,
            PacketCodecs.VAR_INT, TheOneWatchComponentC2SPayload::entityId,
            TheOneWatchComponentC2SPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}