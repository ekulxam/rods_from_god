package survivalblock.rods_from_god.mixin.theonewatch;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.item.OneWatchToRuleThemAll;

import java.util.function.Supplier;

@Mixin(ServerTickManager.class)
public class ServerTickManagerMixin {

    @WrapWithCondition(method = "finishSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendFeedback(Ljava/util/function/Supplier;Z)V"))
    private boolean doNotSendFeedbackIfActivatedByWatch(ServerCommandSource instance, Supplier<Text> feedbackSupplier, boolean broadcastToOps) {
        if (OneWatchToRuleThemAll.isSprintingBecauseOfWatch) {
            OneWatchToRuleThemAll.isSprintingBecauseOfWatch = false;
            return false;
        }
        return true;
    }
}
