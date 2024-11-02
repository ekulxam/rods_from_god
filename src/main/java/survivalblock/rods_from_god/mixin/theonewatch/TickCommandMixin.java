package survivalblock.rods_from_god.mixin.theonewatch;

import net.minecraft.server.command.TickCommand;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import survivalblock.rods_from_god.common.item.OneWatchToRuleThemAll;

@Debug(export = true)
@Mixin(TickCommand.class)
public class TickCommandMixin {

    @ModifyArg(method = {"executeRate", "executeQuery", "executeSprint", "executeFreeze", "executeStep", "executeStopStep", "executeStopSprint"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendFeedback(Ljava/util/function/Supplier;Z)V"), index = 1)
    private static boolean doNotBroadcast(boolean broadcastToOps) {
        return broadcastToOps && OneWatchToRuleThemAll.tickCommandBroadcastToOps;
    }
}
