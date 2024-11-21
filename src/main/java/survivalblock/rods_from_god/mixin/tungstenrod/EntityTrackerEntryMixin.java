package survivalblock.rods_from_god.mixin.tungstenrod;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

import java.lang.reflect.Method;
import java.util.Collection;

@Debug(export = true)
@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {

    @Shadow @Final private Entity entity;

    @Inject(method = "sendPackets", at = @At("HEAD"))
    private void renderCustomThing(ServerPlayerEntity player, @Coerce Object sender, CallbackInfo ci, @Share("sender") LocalRef senderRef) {
        senderRef.set(sender);
    }

    @WrapOperation(method = "sendPackets", constant = @Constant(classValue = LivingEntity.class, ordinal = 0))
    private boolean updateTungstenRodAttributes(Object object, Operation<Boolean> original, @Share("sender") LocalRef senderRef) {
        if (this.entity instanceof TungstenRodEntity tungstenRod) {
            Collection<EntityAttributeInstance> collection = tungstenRod.getAttributes().getAttributesToSend();
            if (!collection.isEmpty()) {
                try {
                    Object sender = senderRef.get();
                    Class<?> clazz = sender.getClass();
                    String methodName = "accept";
                    Method acceptMethod = clazz.getMethod(methodName, RodsFromGod.isConnectorLoaded ? Packet.class : Object.class);
                    acceptMethod.invoke(sender, new EntityAttributesS2CPacket(this.entity.getId(), collection));
                } catch (Throwable throwable) {
                    RodsFromGod.LOGGER.error("Error while doing reflection to get Tungsten Rod attributes!", throwable);
                }
            }
        }
        return original.call(object);
    }
}
