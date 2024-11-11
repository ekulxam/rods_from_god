package survivalblock.rods_from_god.mixin.tungstenrod.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "onEntityAttributes", at = @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void doNotCrashIfTungstenRod(EntityAttributesS2CPacket packet, CallbackInfo ci, @Local Entity entity) {
        if (!(entity instanceof TungstenRodEntity tungstenRod)) {
            return;
        }
        AttributeContainer attributeContainer = tungstenRod.getAttributes();
        for (EntityAttributesS2CPacket.Entry entry : packet.getEntries()) {
            EntityAttributeInstance entityAttributeInstance = attributeContainer.getCustomInstance(entry.attribute());
            if (entityAttributeInstance == null) {
                LOGGER.warn("Entity {} does not have attribute {}", entity, entry.attribute().getIdAsString());
            } else {
                entityAttributeInstance.setBaseValue(entry.base());
                entityAttributeInstance.clearModifiers();

                for (EntityAttributeModifier entityAttributeModifier : entry.modifiers()) {
                    entityAttributeInstance.addTemporaryModifier(entityAttributeModifier);
                }
            }
        }
        ci.cancel();
    }
}
