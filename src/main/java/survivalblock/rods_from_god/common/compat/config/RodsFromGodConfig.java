package survivalblock.rods_from_god.common.compat.config;

import survivalblock.rods_from_god.common.RodsFromGod;

public interface RodsFromGodConfig {

    RodsFromGodConfig INSTANCE = getInstance();

    default boolean archimedesLeverScreenShake() {
        return Defaults.ARCHIMEDES_LEVER_SCREEN_SHAKE;
    }

    static RodsFromGodConfig getInstance() {
        if (RodsFromGod.YACL) {
            return RodsFromGodYACLCompat.getYACLConfigInstance();
        }
        RodsFromGod.LOGGER.warn("YACL is not installed, so Rods from God's YACL Config will not be accessible!");
        return new RodsFromGodConfig() {};
    }

    static void init() {
        // NO-OP
    }

    final class Defaults {
        private Defaults() {
        }

        public static final boolean ARCHIMEDES_LEVER_SCREEN_SHAKE = true;
    }
}
