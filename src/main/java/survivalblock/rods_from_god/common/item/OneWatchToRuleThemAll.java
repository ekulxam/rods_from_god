package survivalblock.rods_from_god.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import survivalblock.rods_from_god.client.screen.Erm;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.TickSubcommand;

import java.util.Objects;


public class OneWatchToRuleThemAll extends Item {

    public static boolean tickCommandBroadcastToOps = true;

    public OneWatchToRuleThemAll(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (Objects.equals(clickType, ClickType.RIGHT)) {
            if (otherStack == null || otherStack.isEmpty()) {
                World world = player.getWorld();
                if (world.isClient) {
                    Erm.erm(world, slot.getIndex(), stack);
                }
                return true;
            }
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            String command = "tick " + stack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_SUBCOMMAND, TickSubcommand.QUERY.getName());
            String arguments = stack.getOrDefault(RodsFromGodDataComponentTypes.ONE_WATCH_ARGUMENTS, "");
            if (!Objects.equals(arguments, "")) {
                command = command + " " + arguments;
            }
            tickCommandBroadcastToOps = false;
            runCommand(serverWorld, user, 3, command);
            tickCommandBroadcastToOps = true;
            user.sendMessage(Text.literal("aaaaa"), true);
        }
        return TypedActionResult.success(stack, world.isClient);
    }

    public static void runCommand(ServerWorld serverWorld, Entity entity, int level, String command) {
        serverWorld.getServer().getCommandManager().executeWithPrefix(new ServerCommandSource(entity, entity.getPos(), entity.getRotationClient(), serverWorld, level,
                entity.getName().getString(), entity.getDisplayName(), serverWorld.getServer(), entity), command);
    }
}
