package survivalblock.rods_from_god.common.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class RodsFromGodGameRules {

    public static final GameRules.Key<GameRules.BooleanRule> KINETIC_EXPLOSION_CAN_MAKE_FIRE = GameRuleRegistry.register("rodsFromGodKineticExplosionCanMakeFire", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<EnumRule<World.ExplosionSourceType>> KINETIC_EXPLOSION_SOURCE_TYPE = GameRuleRegistry.register("rodsFromGodKineticExplosionSourceType", GameRules.Category.MISC, GameRuleFactory.createEnumRule(World.ExplosionSourceType.NONE));

    public static void init() {

    }
}
