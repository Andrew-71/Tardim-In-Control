package su.a71.tardim_ic.tardim_ic;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import su.a71.tardim_ic.tardim_ic.registration.CommandInit;
import com.swdteam.tardim.TardimSaveHandler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class TardimInControl {

    public TardimInControl() {
        Registration.register();

        // Register ourselves for server and other game events we are interested in. Currently, we do not use any events
        MinecraftForge.EVENT_BUS.register(this);
    }
}
