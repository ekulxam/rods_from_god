package survivalblock.rods_from_god.mixin.archimedeslever;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandExecutionContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.init.RodsFromGodCommands;

import java.util.Locale;
import java.util.function.Consumer;

@Debug(export = true)
@Mixin(CommandManager.class)
public abstract class CommandManagerMixin {

    @WrapWithCondition(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/CommandManager;callWithContext(Lnet/minecraft/server/command/ServerCommandSource;Ljava/util/function/Consumer;)V"))
    private boolean noWorldLever(ServerCommandSource source, Consumer<CommandExecutionContext<ServerCommandSource>> callback, @Local(argsOnly = true) String command) throws CommandSyntaxException {
        String lower = command.toLowerCase(Locale.ROOT);
        if (lower.startsWith("execute") && lower.contains("rodsfromgod") && lower.contains("worldlever")) {
            throw RodsFromGodCommands.ILLEGAL_EXECUTE.create();
        }
        return true;
    }
}
