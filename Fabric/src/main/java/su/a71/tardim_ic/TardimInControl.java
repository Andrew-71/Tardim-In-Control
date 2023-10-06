package su.a71.tardim_ic;

import net.fabricmc.api.ModInitializer;
import su.a71.tardim_ic.tardim_ic.registration.Registration;

public class TardimInControl implements ModInitializer {

    @Override
    public void onInitialize() {
        Registration.register();
    }
}
