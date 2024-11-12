package survivalblock.rods_from_god.mixin.tungstenrod;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

import java.util.Collection;
import java.util.function.Consumer;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {

    @Shadow @Final private Entity entity;

    @WrapOperation(method = "sendPackets", constant = @Constant(classValue = LivingEntity.class, ordinal = 0))
    private boolean updateTungstenRodAttributes(Object object, Operation<Boolean> original, ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> sender) {
        if (this.entity instanceof TungstenRodEntity tungstenRod) {
            Collection<EntityAttributeInstance> collection = tungstenRod.getAttributes().getAttributesToSend();
            if (!collection.isEmpty()) {
                sender.accept(new EntityAttributesS2CPacket(this.entity.getId(), collection));
            }
        }
        return false;
    }
}
