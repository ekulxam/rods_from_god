package survivalblock.rods_from_god.common.datafix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

import java.util.Collection;

public abstract class MergeComponentFix extends DataFix {

    private final String name;
    private final Collection<String> oldComponentIds;
    private final String newComponentId;

    public MergeComponentFix(Schema outputSchema, String name, Collection<String> oldComponentIds, String newComponentId) {
        super(outputSchema, false);
        this.name = name;
        this.oldComponentIds = oldComponentIds;
        this.newComponentId = newComponentId;
    }

    @Override
    public final TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<?> opticFinder = type.findField("components");
        return this.fixTypeEverywhereTyped(
                this.name,
                type,
                typed -> typed.updateTyped(
                        opticFinder,
                        typedx -> typedx.update(DSL.remainderFinder(), dynamic -> {
                            oldComponentIds.forEach(oldId -> dynamic.renameAndFixField(oldId,
                                    this.newComponentId,
                                    dynamic1 -> fixComponent(dynamic1, oldId)));
                            return dynamic;
                        })
                )
        );
    }

    protected abstract <T> Dynamic<T> fixComponent(Dynamic<T> dynamic, String oldComponentId);
}
