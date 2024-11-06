package survivalblock.rods_from_god.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// FINE, I'm making a util class
public class RodsFromGodUtil {

    public static boolean isAffectedByDaylight(LivingEntity living) {
        if (living == null) {
            return false;
        }
        World world = living.getWorld();
        if (world.isDay()) {
            @SuppressWarnings("deprecation") float f = living.getBrightnessAtEyes();
            BlockPos blockPos = BlockPos.ofFloored(living.getX(), living.getEyeY(), living.getZ());
            return f > 0.5F && world.isSkyVisible(blockPos);
        }
        return false;
    }
}
