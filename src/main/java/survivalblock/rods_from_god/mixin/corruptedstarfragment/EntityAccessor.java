package survivalblock.rods_from_god.mixin.corruptedstarfragment;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Invoker("toNbtList")
    NbtList rods_from_god$invokeToNbtList(double... values);
}
