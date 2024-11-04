package survivalblock.rods_from_god.mixin.solarprismheadset;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.entity.EntityLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor {

    @Invoker("getEntityLookup")
    EntityLookup<Entity> rods_from_god$invokeGetEntityLookup();
}
