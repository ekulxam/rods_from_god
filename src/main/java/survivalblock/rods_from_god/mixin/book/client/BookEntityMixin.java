package survivalblock.rods_from_god.mixin.book.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.client.screen.BookTargetScreen;
import survivalblock.rods_from_god.common.entity.BookEntity;

@Debug(export = true)
@Environment(EnvType.CLIENT)
@Mixin(value = BookEntity.class)
public class BookEntityMixin {

    @ModifyReturnValue(method = "interact", at = @At(value = "RETURN"))
    private ActionResult openScreen(ActionResult original, PlayerEntity player, Hand hand) {
        World world = player.getWorld();
        if (!world.isClient) {
            return original;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (!BookEntity.CAN_EDIT.apply(player)) {
            return original;
        }
        client.setScreen(new BookTargetScreen(Text.translatable("entity.rods_from_god.book"),
                client.currentScreen,
                (BookEntity) (Object) this));
        return original;
    }
}
