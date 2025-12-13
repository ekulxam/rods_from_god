package survivalblock.rods_from_god.client.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.item.TheOneWatchComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

public record TheOneWatchComponentC2SPayload(String subcommand, String arguments, int slot) implements CustomPayload {
    public static final CustomPayload.Id<TheOneWatchComponentC2SPayload> ID = new Id<>(RodsFromGod.id("the_one_watch_c2s_payload"));
    public static final PacketCodec<RegistryByteBuf, TheOneWatchComponentC2SPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, TheOneWatchComponentC2SPayload::subcommand,
            PacketCodecs.STRING, TheOneWatchComponentC2SPayload::arguments,
            PacketCodecs.VAR_INT, TheOneWatchComponentC2SPayload::slot,
            TheOneWatchComponentC2SPayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<TheOneWatchComponentC2SPayload> {

        public static final Receiver INSTANCE = new Receiver();

        @Override
        public void receive(TheOneWatchComponentC2SPayload payload, ServerPlayNetworking.Context context) {
            ItemStack stack = context.player().getInventory().getStack(payload.slot());
            if (!stack.isOf(RodsFromGodItems.THE_ONE_WATCH)) {
                return;
            }
            String receivedArguments = payload.arguments();
            StringBuilder actualArguments = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                // hopefully this prevents executing chained commands or whatever
                int space = receivedArguments.indexOf(" ");
                if (space < 0) {
                    break;
                }
                actualArguments.append(receivedArguments, 0, space);
                receivedArguments = receivedArguments.substring(space);
            }
            stack.set(RodsFromGodDataComponentTypes.THE_ONE_WATCH, new TheOneWatchComponent(payload.subcommand(), actualArguments.toString()));
        }
    }
}