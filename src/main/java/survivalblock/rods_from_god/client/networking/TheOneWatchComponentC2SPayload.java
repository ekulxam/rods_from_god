package survivalblock.rods_from_god.client.networking;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import survivalblock.rods_from_god.common.RodsFromGod;

import java.util.Objects;

public class TheOneWatchComponentC2SPayload implements CustomPayload {
    public static final CustomPayload.Id<TheOneWatchComponentC2SPayload> ID = new CustomPayload.Id<>(RodsFromGod.id("the_one_watch_c2s_payload"));
    public static final PacketCodec<RegistryByteBuf, TheOneWatchComponentC2SPayload> CODEC = PacketCodec.of(TheOneWatchComponentC2SPayload::write, TheOneWatchComponentC2SPayload::new);

    public final String subcommand, arguments;
    public final int slot, entityId;

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    private void write(RegistryByteBuf buf) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("subcommand", this.subcommand);
        nbt.putString("arguments", this.arguments);
        nbt.putInt("slot", this.slot);
        nbt.putInt("entityId", this.entityId);
        buf.writeNbt(nbt);
    }

    public TheOneWatchComponentC2SPayload(String subcommand, String arguments, int slot, int entityId) {
        this.subcommand = subcommand;
        this.arguments = arguments;
        this.slot = slot;
        this.entityId = entityId;
    }

    private TheOneWatchComponentC2SPayload(RegistryByteBuf buf) {
        NbtCompound nbt = Objects.requireNonNull(buf.readNbt());
        this.subcommand = nbt.getString("subcommand");
        this.arguments = nbt.getString("arguments");
        this.slot = nbt.getInt("slot");
        this.entityId = nbt.getInt("entityId");
    }
}