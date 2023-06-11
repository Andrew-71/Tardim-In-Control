package su.a71.tardim_ic.tardim_ic.registration;

import com.simibubi.create.content.logistics.block.display.AllDisplayBehaviours;
import net.minecraft.resources.ResourceLocation;
import su.a71.tardim_ic.tardim_ic.Constants;
import su.a71.tardim_ic.tardim_ic.create_compat.display_source.fuel_storage.FuelLevelDisplaySource;
import su.a71.tardim_ic.tardim_ic.create_compat.display_source.fuel_storage.RequiredFuelDisplaySource;

import static com.swdteam.tardim.common.init.TRDTiles.TILE_FUEL_STORAGE;

public class CreateCompat {

    public static void register() {
        Constants.LOG.info("Loaded Create compatibility!");
//        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), DIGITAL_TARDIM_INTERFACE);
//        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), new BlockItem(DIGITAL_TARDIM_INTERFACE, new FabricItemSettings().tab(Registration.TARDIM_IC_TAB)));

        AllDisplayBehaviours.assignTile(AllDisplayBehaviours.register(new ResourceLocation(Constants.MOD_ID, "fuel_storage_display_source"), new FuelLevelDisplaySource()), TILE_FUEL_STORAGE);
        AllDisplayBehaviours.assignTile(AllDisplayBehaviours.register(new ResourceLocation(Constants.MOD_ID, "fuel_required_display_source"), new RequiredFuelDisplaySource()), TILE_FUEL_STORAGE);
    }
}
