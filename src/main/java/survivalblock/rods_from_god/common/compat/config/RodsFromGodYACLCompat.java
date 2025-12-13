package survivalblock.rods_from_god.common.compat.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodYACLCompat implements RodsFromGodConfig {

    public static final ConfigClassHandler<RodsFromGodYACLCompat> HANDLER = ConfigClassHandler.createBuilder(RodsFromGodYACLCompat.class)
            .id(RodsFromGod.id("rods_from_god"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("rods_from_god.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
                    .setJson5(true)
                    .build())
            .build();

    public static RodsFromGodConfig getYACLConfigInstance() {
        if (HANDLER.load()) {
            RodsFromGod.configLoaded = true;
            return HANDLER.instance();
        }
        RodsFromGod.LOGGER.warn("Rods from God YACL Config could not be loaded!");
        return new RodsFromGodConfig() {};
    }

    @SerialEntry
    public boolean allowScreenShakingForArchimedesLever = true;

    @Override
    public boolean archimedesLeverScreenShake() {
        return this.allowScreenShakingForArchimedesLever;
    }
}
