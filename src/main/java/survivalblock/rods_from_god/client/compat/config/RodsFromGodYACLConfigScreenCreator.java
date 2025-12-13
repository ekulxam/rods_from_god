package survivalblock.rods_from_god.client.compat.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import survivalblock.rods_from_god.common.compat.config.RodsFromGodYACLCompat;

@Environment(EnvType.CLIENT)
public class RodsFromGodYACLConfigScreenCreator implements RodsFromGodConfigScreenCreator {

    @Override
    public Screen create(Screen parent) {
        return  YetAnotherConfigLib.createBuilder()
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
}
