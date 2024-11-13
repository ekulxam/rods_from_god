package survivalblock.rods_from_god.common;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

@SuppressWarnings({"unused", "deprecation"})
public enum TickSubcommand implements StringIdentifiable {
    /**
     * As defined in {@link net.minecraft.server.command.TickCommand}
     */
    QUERY("query"),
    RATE("rate"),
    STEP("step"),
    SPRINT("sprint"),
    FREEZE("freeze"),
    UNFREEZE("unfreeze");

    private final String name;
    public static final StringIdentifiable.EnumCodec<TickSubcommand> CODEC = StringIdentifiable.createCodec(TickSubcommand::values);
    private static final IntFunction<TickSubcommand> BY_ID = ValueLists.<TickSubcommand>createIdToValueFunction(Enum::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);

    TickSubcommand(final String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public static TickSubcommand getType(int type) {
        return BY_ID.apply(type);
    }

    /**
     * USE THIS INSTEAD OF {@link TickSubcommand#valueOf(String)}
     */
    public static TickSubcommand getType(String name) {
        return CODEC.byId(name, QUERY);
    }
}