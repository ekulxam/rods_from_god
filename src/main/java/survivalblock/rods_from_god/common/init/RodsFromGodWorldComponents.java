package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.Entity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.*;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

public class RodsFromGodWorldComponents implements WorldComponentInitializer {

    public static final ComponentKey<WorldLeverComponent> WORLD_LEVER = ComponentRegistry.getOrCreate(RodsFromGod.id("world_lever"), WorldLeverComponent.class);

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry worldComponentFactoryRegistry) {
        worldComponentFactoryRegistry.register(WORLD_LEVER, WorldLeverComponent::new);
    }
}