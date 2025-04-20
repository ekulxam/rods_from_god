package survivalblock.rods_from_god.common.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.cca.entity.StoneStatueComponent;
import survivalblock.rods_from_god.common.component.cca.world.WasDayComponent;

import java.util.Optional;
import java.util.stream.Collectors;

import static survivalblock.rods_from_god.common.component.cca.world.WasDayComponent.DEFAULT_LIGHTNING_VOLUME;

public class RodsFromGodCommands {

    public static final DynamicCommandExceptionType STATUE_ENTITY_NOT_SUPPORTED = new DynamicCommandExceptionType((obj) -> Text.stringifiedTranslatable("argument.rodsfromgod.medusastatue.entity_not_supported", obj));

    public static final SimpleCommandExceptionType NOT_PLAYER_SOURCE = new SimpleCommandExceptionType(Text.translatable("commands.rodsfromgod.notplayersource"));
    public static final SimpleCommandExceptionType ILLEGAL_EXECUTE = new SimpleCommandExceptionType(Text.translatable("commands.rodsfromgod.illegalexecute"));

    public static void init(CommandDispatcher<ServerCommandSource> dispatcher,
                            @SuppressWarnings("unused") CommandRegistryAccess registryAccess,
                            @SuppressWarnings("unused") CommandManager.RegistrationEnvironment environment) {

        LiteralCommandNode<ServerCommandSource> rodsFromGodNode = CommandManager.literal("rodsfromgod").build();

        LiteralCommandNode<ServerCommandSource> worldLeverNode = CommandManager.literal("worldlever").build();

        LiteralCommandNode<ServerCommandSource> setLiftedNode = CommandManager.literal("setLifted")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("lifted", BoolArgumentType.bool())
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            if (!(source.getEntity() instanceof ServerPlayerEntity serverPlayer)) {
                                throw NOT_PLAYER_SOURCE.create();
                            }
                            ServerWorld serverWorld = context.getSource().getWorld();
                            Identifier id = getIdFromServerWorld(serverWorld);
                            boolean lifted = BoolArgumentType.getBool(context, "lifted");
                            context.getSource().sendFeedback(() -> Text.stringifiedTranslatable("commands.rodsfromgod.worldlever.setlifted.success", id, lifted), true);
                            RodsFromGod.LOGGER.info("Player {} set the value of lifted in the World Lever Component to {} for dimension {}", serverPlayer, lifted, id);
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
                            switching ? "commands.rodsfromgod.worldlever.query.switching.true" : "commands.rodsfromgod.worldlever.query.switching.false",
                            id), true);
                    return 1;
                }).build();

        LiteralCommandNode<ServerCommandSource> liftedNode = CommandManager.literal("lifted")
                .executes(context -> {
                    ServerWorld serverWorld = context.getSource().getWorld();
                    Identifier id = getIdFromServerWorld(serverWorld);
                    boolean lifted = RodsFromGodWorldComponents.WORLD_LEVER.get(serverWorld).isLifted();
                    context.getSource().sendFeedback(() -> Text.stringifiedTranslatable(
                            lifted ? "commands.rodsfromgod.worldlever.query.lifted.true" : "commands.rodsfromgod.worldlever.query.lifted.false",
                            id), true);
                    return 1;
                }).build();

        LiteralCommandNode<ServerCommandSource> canSwitchNode = CommandManager.literal("canSwitch")
                .executes(context -> {
                    ServerWorld serverWorld = context.getSource().getWorld();
                    Identifier id = getIdFromServerWorld(serverWorld);
                    boolean canSwitch = RodsFromGodWorldComponents.WORLD_LEVER.get(serverWorld).canSwitch(serverWorld);
                    context.getSource().sendFeedback(() -> Text.stringifiedTranslatable(
                            canSwitch ? "commands.rodsfromgod.worldlever.query.canswitch.true" : "commands.rodsfromgod.worldlever.query.canswitch.false",
                            id), true);
                    return 1;
                }).build();

        LiteralCommandNode<ServerCommandSource> medusaStatueNode = CommandManager.literal("medusastatue")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("target", EntityArgumentType.entity())
                        .executes(RodsFromGodCommands::executeMedusaCurse).build()).build();

        LiteralCommandNode<ServerCommandSource> lightningVolumeNode = CommandManager.literal("lightningvolume")
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerWorld serverWorld = source.getWorld();
                    source.sendFeedback(() -> Text.stringifiedTranslatable("commands.rodsfromgod.lightningvolume.get", getIdFromServerWorld(serverWorld), RodsFromGodWorldComponents.WAS_DAY.get(serverWorld).getLightningVolume()), false);
                    return 1;
                })
                .then(CommandManager.literal("set")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(ctx -> executeSetLightningVolume(ctx.getSource(), DEFAULT_LIGHTNING_VOLUME))
                        .then(CommandManager.argument("value", FloatArgumentType.floatArg(2, DEFAULT_LIGHTNING_VOLUME))
                                .executes(ctx -> executeSetLightningVolume(ctx.getSource(), FloatArgumentType.getFloat(ctx, "value")))
                        ).build())
                .build();

        dispatcher.getRoot().addChild(rodsFromGodNode);

        rodsFromGodNode.addChild(worldLeverNode);

        worldLeverNode.addChild(setLiftedNode);

        worldLeverNode.addChild(queryNode);
        queryNode.addChild(liftedNode);
        queryNode.addChild(canSwitchNode);
        queryNode.addChild(isSwitchingNode);

        rodsFromGodNode.addChild(medusaStatueNode);

        rodsFromGodNode.addChild(lightningVolumeNode);
    }

    private static int executeMedusaCurse(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Entity entity = EntityArgumentType.getEntity(context, "target");
        Optional<StoneStatueComponent> optional = RodsFromGodEntityComponents.STONE_STATUE.maybeGet(entity);
        Text name = entity.getName();
        if (optional.isEmpty()) {
            throw STATUE_ENTITY_NOT_SUPPORTED.create(name);
        }
        StoneStatueComponent stoneStatueComponent = optional.get();
        Text text;
        if (stoneStatueComponent.showcase()) {
            stoneStatueComponent.setInStone(false, true);
            text = Text.stringifiedTranslatable("commands.rodsfromgod.medusastatue.remove_statue", name);
        } else {
            stoneStatueComponent.setInStone(true, true);
            text = Text.stringifiedTranslatable("commands.rodsfromgod.medusastatue.set_statue", name);
        }
        context.getSource().sendFeedback(() -> text, true);
        return 1;
    }


    public static Identifier getIdFromServerWorld(ServerWorld serverWorld) {
        MinecraftServer server = serverWorld.getServer();
        return server.getWorldRegistryKeys().stream().collect(Collectors.toMap(server::getWorld, RegistryKey::getValue)).get(serverWorld);
    }

    private static int executeSetLightningVolume(ServerCommandSource source, float newVolume) {
        ServerWorld serverWorld = source.getWorld();
        WasDayComponent wasDayComponent = RodsFromGodWorldComponents.WAS_DAY.get(serverWorld);
        float oldVolume = wasDayComponent.getLightningVolume();
        wasDayComponent.setLightningVolume(newVolume);
        source.sendFeedback(() -> Text.stringifiedTranslatable("commands.rodsfromgod.lightningvolume.set", getIdFromServerWorld(serverWorld), oldVolume, newVolume), true);
        return 1;
    }
}
