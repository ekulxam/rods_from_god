package survivalblock.rods_from_god.client.compat.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class RodsFromGodModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return RodsFromGodConfigScreenCreator.INSTANCE::create;
    }
}
