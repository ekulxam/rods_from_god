package survivalblock.rods_from_god.mixin.book.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.client.screen.BookTargetScreen;
import survivalblock.rods_from_god.common.entity.BookEntity;
import survivalblock.rods_from_god.mixin.book.PlayerEntityMixin;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntityMixin {

    protected AbstractClientPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult rods_from_god$openBookTargetScreen(ActionResult original, Hand hand, BookEntity book) {
        World world = this.getWorld();
        if (!world.isClient) {
            return original;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (!BookEntity.CAN_EDIT.apply((AbstractClientPlayerEntity) (Object) this)) {
            return original;
        }
        client.setScreen(
                new BookTargetScreen(
                        Text.translatable("entity.rods_from_god.book"),
                        client.currentScreen,
                        book
                )
        );
        return original;
    }
}
