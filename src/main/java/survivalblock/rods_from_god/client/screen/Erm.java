package survivalblock.rods_from_god.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.TickSubcommand;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;

public class Erm {

    public static void erm(World world, int slot, ItemStack stack) {
        if (world.isClient()) {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayer = client.player;
            if (clientPlayer != null) {
                String subcommand = stack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_SUBCOMMAND, TickSubcommand.QUERY.getName());
                String arguments = stack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_ARGUMENTS, "");
                client.setScreen(new TheOneWatchScreen(Text.empty(), client.currentScreen, slot, clientPlayer.getId(), subcommand, arguments));
            }
        }
    }
}
