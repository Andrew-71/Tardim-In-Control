package su.a71.tardim_ic.tardim_ic.registration;

import com.swdteam.tardim.common.init.CommandManager;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import su.a71.tardim_ic.Constants;
import su.a71.tardim_ic.command.CommandModemTransmit;
import su.a71.tardim_ic.computercraft_compat.digital_interface.DigitalInterfaceBlock;
import su.a71.tardim_ic.computercraft_compat.digital_interface.DigitalInterfaceTileEntity;

import static su.a71.tardim_ic.tardim_ic.registration.Registration.registerBlock;
//import su.a71.tardim_ic.computercraft_compat.blocks.digital_interface.DigitalInterfaceTileEntity;

public class ComputerCraftCompat {
    public static final Block DIGITAL_TARDIM_INTERFACE = new DigitalInterfaceBlock();

    public static final BlockEntityType<DigitalInterfaceTileEntity> DIGITAL_INTERFACE_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "digital_tardim_interface"),
            FabricBlockEntityTypeBuilder.create(DigitalInterfaceTileEntity::new, DIGITAL_TARDIM_INTERFACE).build()
    );

    public static void register() {
        Constants.LOG.info("Loaded ComputerCraft compatibility!");


        registerBlock("digital_tardim_interface", () -> DIGITAL_TARDIM_INTERFACE, null);
//
//        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), DIGITAL_TARDIM_INTERFACE);
//        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), new BlockItem(DIGITAL_TARDIM_INTERFACE, new FabricItemSettings().tab(Registration.TARDIM_IC_TAB)));

//        CommandManager.register(new CommandModemTransmit());
//        PeripheralLookup.get().registerSelf();

//        ComputerCraftAPI.registerPeripheralProvider(new TardimPeripheralProvider());
    }
}
