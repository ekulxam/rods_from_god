package survivalblock.rods_from_god.client.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.cca.entity.BookTargetComponent;
import survivalblock.rods_from_god.common.entity.BookEntity;

public record BookTargetC2SPayload(float scale, int projectileDuration, boolean onlyTargetsPlayers, double range, int entityId) implements CustomPayload {

    public static final Id<BookTargetC2SPayload> ID = new Id<>(RodsFromGod.id("book_target_c2s_payload"));

    public static final PacketCodec<RegistryByteBuf, BookTargetC2SPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, (payload) -> payload.scale,
            PacketCodecs.VAR_INT, (payload) -> payload.projectileDuration,
            PacketCodecs.BOOL, (payload) -> payload.onlyTargetsPlayers,
            PacketCodecs.DOUBLE, (payload) -> payload.range,
            PacketCodecs.VAR_INT, (payload) -> payload.entityId,
            BookTargetC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<BookTargetC2SPayload> {

        public static final Receiver INSTANCE = new Receiver();

        @Override
        public void receive(BookTargetC2SPayload payload, ServerPlayNetworking.Context context) {
            var player = context.player();
            if (!BookEntity.CAN_EDIT.apply(player)) {
                return;
            }
            Entity entity = player.getWorld().getEntityById(payload.entityId);
            if (!(entity instanceof BookEntity book)) {
                return;
            }
            book.setProjectileDuration(payload.projectileDuration);
            EntityAttributeInstance attributeInstance = book.getAttributes().getCustomInstance(EntityAttributes.GENERIC_SCALE);
            if (attributeInstance != null) {
                attributeInstance.setBaseValue(Math.max(1, payload.scale));
            }
            BookTargetComponent component = book.getComponent();
            component.setValues(payload.onlyTargetsPlayers, payload.range);
        }
    }
}