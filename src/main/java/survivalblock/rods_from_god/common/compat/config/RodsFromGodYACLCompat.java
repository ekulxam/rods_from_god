package survivalblock.rods_from_god.common.compat.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodYACLCompat {

    public static Screen create(Screen parent){
        if (!RodsFromGod.shouldDoConfig) {
            throw new UnsupportedOperationException();
        }
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("rods_from_god.yacl.category.main"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("rods_from_god.yacl.category.main"))
                        .tooltip(Text.translatable("rods_from_god.yacl.category.main.tooltip"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("rods_from_god.yacl.group.client"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("rods_from_god.yacl.option.boolean.allowScreenShakingForArchimedesLever"))
                                        .description(OptionDescription.of(Text.translatable("rods_from_god.yacl.option.boolean.allowScreenShakingForArchimedesLever.desc")))
                                        .binding(RodsFromGodYACLCompat.HANDLER.defaults().allowScreenShakingForArchimedesLever, () -> RodsFromGodYACLCompat.HANDLER.instance().allowScreenShakingForArchimedesLever, newVal -> RodsFromGodYACLCompat.HANDLER.instance().allowScreenShakingForArchimedesLever = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .save(RodsFromGodYACLCompat.HANDLER::save)
                .build()
                .generateScreen(parent);
    }

    public static final ConfigClassHandler<RodsFromGodYACLCompat> HANDLER = ConfigClassHandler.createBuilder(RodsFromGodYACLCompat.class)
            .id(RodsFromGod.id("rods_from_god"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("rods_from_god.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean allowScreenShakingForArchimedesLever = true;
}
