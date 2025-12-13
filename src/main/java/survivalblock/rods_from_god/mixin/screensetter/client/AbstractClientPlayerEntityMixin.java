package survivalblock.rods_from_god.mixin.screensetter.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import survivalblock.rods_from_god.client.screen.BookTargetScreen;
import survivalblock.rods_from_god.client.screen.TheOneWatchScreen;
import survivalblock.rods_from_god.common.component.item.TheOneWatchComponent;
import survivalblock.rods_from_god.common.entity.BookEntity;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.mixin.screensetter.PlayerEntityMixin;

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

    @Override
    public boolean rods_from_god$openOneWatchScreen(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, StackReference cursorStackReference) {
        if (!super.rods_from_god$openOneWatchScreen(stack, otherStack, slot, clickType, cursorStackReference)) {
            return false;
        }

        World world = this.getWorld();
        if (!world.isClient) {
            return true;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayer = client.player;
        if (clientPlayer != null) {
            TheOneWatchComponent oneWatchComponent = stack.getOrDefault(RodsFromGodDataComponentTypes.THE_ONE_WATCH, TheOneWatchComponent.DEFAULT_INSTANCE);
            client.setScreen(new TheOneWatchScreen(Text.translatable("item.rods_from_god.the_one_watch"),
                    client.currentScreen,
                    slot.getIndex(),
                    clientPlayer.getId(),
                    oneWatchComponent.subcommand(),
                    oneWatchComponent.arguments()));
        }
        return true;
    }
}
