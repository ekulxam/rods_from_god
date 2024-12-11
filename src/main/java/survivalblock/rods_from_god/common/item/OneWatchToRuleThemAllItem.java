package survivalblock.rods_from_god.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import survivalblock.atmosphere.atmospheric_api.not_mixin.command.AtmosphericCommandExecutorUtil;
import survivalblock.rods_from_god.common.component.EvokerInvokerComponent;
import survivalblock.rods_from_god.common.component.TheOneWatchComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.TickSubcommand;

import java.util.List;
import java.util.Objects;

public class OneWatchToRuleThemAllItem extends Item {

    public static boolean tickCommandBroadcastToOps = true;
    public static boolean isSprintingBecauseOfWatch = false;

    public OneWatchToRuleThemAllItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return Objects.equals(clickType, ClickType.RIGHT);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            TheOneWatchComponent oneWatchComponent = stack.getOrDefault(RodsFromGodDataComponentTypes.THE_ONE_WATCH, TheOneWatchComponent.DEFAULT_INSTANCE);
            String subcommand = oneWatchComponent.subcommand();
            String command = "tick " + subcommand;
            String arguments = oneWatchComponent.arguments();
            if (!Objects.equals(arguments, "")) {
                command = command + " " + arguments;
            }
            tickCommandBroadcastToOps = false;
            isSprintingBecauseOfWatch = Objects.equals(subcommand, TickSubcommand.SPRINT.getName());
            AtmosphericCommandExecutorUtil.runCommand(serverWorld, user, 3, command);
            tickCommandBroadcastToOps = true;
        }
        return TypedActionResult.success(stack, world.isClient);
    }
}
