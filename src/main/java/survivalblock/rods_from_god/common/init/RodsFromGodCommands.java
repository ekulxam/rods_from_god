package survivalblock.rods_from_god.common.init;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.stream.Collectors;

public class RodsFromGodCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<ServerCommandSource> worldLeverNode = CommandManager.literal("worldlever")
                    .requires((source) -> source.hasPermissionLevel(2)).build();

            LiteralCommandNode<ServerCommandSource> setLiftedNode = CommandManager.literal("setLifted")
                    .then(CommandManager.argument("lifted", BoolArgumentType.bool())
                            .executes(context -> {
                                ServerWorld serverWorld = context.getSource().getWorld();
                                Identifier id = getIdFromServerWorld(serverWorld);
                                boolean lifted = BoolArgumentType.getBool(context, "lifted");
                                context.getSource().sendFeedback(() -> Text.stringifiedTranslatable("commands.worldlever.setlifted.success", id, lifted), true);
                                return RodsFromGodWorldComponents.WORLD_LEVER.get(serverWorld).setLifted(lifted) ? 1 : 0;
                            }).build())
                    .build();

            LiteralCommandNode<ServerCommandSource> queryNode = CommandManager.literal("query").build();

            LiteralCommandNode<ServerCommandSource> isSwitchingNode = CommandManager.literal("switching")
                    .executes(context -> {
                        ServerWorld serverWorld = context.getSource().getWorld();
                        Identifier id = getIdFromServerWorld(serverWorld);
                        boolean switching = RodsFromGodWorldComponents.WORLD_LEVER.get(serverWorld).isSwitching();
                        context.getSource().sendFeedback(() -> Text.stringifiedTranslatable(
                                switching ? "commands.worldlever.query.switching.true" : "commands.worldlever.query.switching.false",
                                id), true);
                        return 1;
                    }).build();

            LiteralCommandNode<ServerCommandSource> liftedNode = CommandManager.literal("lifted")
                    .executes(context -> {
                        ServerWorld serverWorld = context.getSource().getWorld();
                        Identifier id = getIdFromServerWorld(serverWorld);
                        boolean lifted = RodsFromGodWorldComponents.WORLD_LEVER.get(serverWorld).isLifted();
                        context.getSource().sendFeedback(() -> Text.stringifiedTranslatable(
                                lifted ? "commands.worldlever.query.lifted.true" : "commands.worldlever.query.lifted.false",
                                id), true);
                        return 1;
                    }).build();

            LiteralCommandNode<ServerCommandSource> canSwitchNode = CommandManager.literal("canSwitch")
                    .executes(context -> {
                        ServerWorld serverWorld = context.getSource().getWorld();
                        Identifier id = getIdFromServerWorld(serverWorld);
                        boolean canSwitch = RodsFromGodWorldComponents.WORLD_LEVER.get(serverWorld).canSwitch(serverWorld);
                        context.getSource().sendFeedback(() -> Text.stringifiedTranslatable(
                                canSwitch ? "commands.worldlever.query.canswitch.true" : "commands.worldlever.query.canswitch.false",
                                id), true);
                        return 1;
                    }).build();

            dispatcher.getRoot().addChild(worldLeverNode);

            worldLeverNode.addChild(setLiftedNode);

            worldLeverNode.addChild(queryNode);
            queryNode.addChild(liftedNode);
            queryNode.addChild(canSwitchNode);
            queryNode.addChild(isSwitchingNode);
        });
    }

    public static Identifier getIdFromServerWorld(ServerWorld serverWorld) {
        MinecraftServer server = serverWorld.getServer();
        return server.getWorldRegistryKeys().stream().collect(Collectors.toMap(server::getWorld, RegistryKey::getValue)).get(serverWorld);
    }
}
