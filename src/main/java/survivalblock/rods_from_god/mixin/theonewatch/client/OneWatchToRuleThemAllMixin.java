package survivalblock.rods_from_god.mixin.theonewatch.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.rods_from_god.client.screen.TheOneWatchScreen;
import survivalblock.rods_from_god.common.TickSubcommand;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.item.OneWatchToRuleThemAll;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(OneWatchToRuleThemAll.class)
public class OneWatchToRuleThemAllMixin {

    // Please don't ask
    @Inject(method = "onClicked", at = @At("HEAD"), cancellable = true)
    private void openScreen(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        if (Objects.equals(clickType, ClickType.RIGHT)) {
            if (otherStack == null || otherStack.isEmpty()) {
                World world = player.getWorld();
                if (world.isClient) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    ClientPlayerEntity clientPlayer = client.player;
                    if (clientPlayer != null) {
                        String subcommand = stack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_SUBCOMMAND, TickSubcommand.QUERY.getName());
                        String arguments = stack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_ARGUMENTS, "");
                        client.setScreen(new TheOneWatchScreen(Text.translatable("item.rods_from_god.the_one_watch"), client.currentScreen, slot.getIndex(), clientPlayer.getId(), subcommand, arguments));
                    }
                }
                cir.setReturnValue(true);
            }
        }
    }
}
