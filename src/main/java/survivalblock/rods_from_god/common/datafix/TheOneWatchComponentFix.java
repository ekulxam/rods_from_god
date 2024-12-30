package survivalblock.rods_from_god.common.datafix;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

import java.util.List;
import java.util.Optional;

/**
 * DFU for <a href="https://github.com/ekulxam/rods_from_god/compare/9ab8bee..e937770">https://github.com/ekulxam/rods_from_god/compare/9ab8bee..e937770</a>
 */
public class TheOneWatchComponentFix extends MergeComponentFix {

    public TheOneWatchComponentFix(Schema outputSchema) {
        super(outputSchema, "TheOneWatchComponentFix", List.of("one_watch_subcommand", "one_watch_arguments"), "rods_from_god:the_one_watch");
    }

    @Override
    protected <T> Dynamic<T> fixComponent(Dynamic<T> dynamic, String oldComponentId) {
        Optional<Dynamic<T>> optional = dynamic.get("pos").result();
        Optional<Dynamic<T>> optional2 = dynamic.get("dimension").result();
        dynamic = dynamic.remove("pos").remove("dimension");
        if (optional.isPresent() && optional2.isPresent()) {
            dynamic = dynamic.set("target", dynamic.emptyMap().set("pos", optional.get()).set("dimension", optional2.get()));
        }

        return dynamic;
    }
}