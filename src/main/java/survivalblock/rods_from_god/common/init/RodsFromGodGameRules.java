package survivalblock.rods_from_god.common.init;

import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodGameRules {

    public static final CustomGameRuleCategory RODS_FROM_GOD_CATEGORY = new CustomGameRuleCategory(RodsFromGod.id("rods_from_god"), Text.translatable("gamerule.category.rods_from_god"));

    public static final GameRules.Key<GameRules.BooleanRule> KINETIC_EXPLOSION_CAN_MAKE_FIRE = registerBool("rodsFromGodKineticExplosionCanMakeFire", true);
    public static final GameRules.Key<EnumRule<World.ExplosionSourceType>> KINETIC_EXPLOSION_SOURCE_TYPE = register("rodsFromGodKineticExplosionSourceType", GameRuleFactory.createEnumRule(World.ExplosionSourceType.NONE));
    public static final GameRules.Key<GameRules.BooleanRule> SMOKE_BOMBS_TRIGGER_BLOCKS = registerBool("rodsFromGodSmokeBombsTriggerBlocks", false);
    public static final GameRules.Key<GameRules.BooleanRule> DISABLE_WORLD_LEVER = registerBool("rodsFromGodDisableWorldLever", false);
    public static final GameRules.Key<GameRules.BooleanRule> LIGHTNING_SPLASH_POTION_CREATES_FIRE = registerBool("rodsFromGodLightningSplashPotionCreatesFire", true);

    public static void init() {

    }

    public static GameRules.Key<GameRules.BooleanRule> registerBool(String name, boolean defaultValue) {
        return register(name, GameRuleFactory.createBooleanRule(defaultValue));
    }

    public static <T extends GameRules.Rule<T>> GameRules.Key<T> register(String name, GameRules.Type<T> type) {
        return GameRuleRegistry.register(name, RODS_FROM_GOD_CATEGORY, type);
    }
}
