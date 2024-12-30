package survivalblock.rods_from_god.common.datafix;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.fix.ComponentFix;

import java.util.Optional;

// nice half-baked componentfix you got there
public class EvokerInvokerComponentFix extends ComponentFix {
    public EvokerInvokerComponentFix(Schema outputSchema) {
        super(outputSchema, "EvokerInvokerComponentFix", "rods_from_god:evoker_invoker_cooldown", "rods_from_god:evoker_invoker");
    }

    @Override
    protected <T> Dynamic<T> fixComponent(Dynamic<T> dynamic) {
        Optional<Dynamic<T>> optional = dynamic.get("").result();
        dynamic = dynamic.remove("");
        if (optional.isPresent()) {
            dynamic = dynamic.set("cooldown", optional.get());
        }
        return dynamic;
    }
}
