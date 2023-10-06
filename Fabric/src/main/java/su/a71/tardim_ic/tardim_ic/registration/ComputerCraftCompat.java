package su.a71.tardim_ic.tardim_ic.registration;

import com.swdteam.tardim.common.block.BlockFuelStorage;
import com.swdteam.tardim.common.block.BlockRotor;
import com.swdteam.tardim.common.block.BlockTardimScanner;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import su.a71.tardim_ic.Constants;
import su.a71.tardim_ic.computercraft_compat.digital_interface.DigitalInterfaceBlock;
import su.a71.tardim_ic.computercraft_compat.digital_interface.DigitalInterfaceTileEntity;
import su.a71.tardim_ic.computercraft_compat.entity.FakeTardimPeripheralTileEntity;
import su.a71.tardim_ic.computercraft_compat.peripherals.DigitalInterfacePeripheral;
import su.a71.tardim_ic.computercraft_compat.peripherals.FuelStoragePeripheral;
import su.a71.tardim_ic.computercraft_compat.peripherals.TardimScannerPeripheral;
import su.a71.tardim_ic.computercraft_compat.peripherals.TimeRotorPeripheral;

import static com.swdteam.tardim.common.init.TRDBlocks.*;
import static su.a71.tardim_ic.tardim_ic.registration.Registration.registerBlock;

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

        PeripheralLookup.get().registerForBlockEntity((entity, direction) -> new DigitalInterfacePeripheral(new FakeTardimPeripheralTileEntity(entity.getBlockPos(), entity.getLevel())), DIGITAL_INTERFACE_BE);
        PeripheralLookup.get().registerForBlocks((world, pos, state, blockEntity, direction) -> {
            if (state.getBlock() instanceof BlockFuelStorage) {
                return new FuelStoragePeripheral(new FakeTardimPeripheralTileEntity(pos, world));
            } else if (state.getBlock() instanceof BlockRotor) {
                return new TimeRotorPeripheral(new FakeTardimPeripheralTileEntity(pos, world));
            } else if (state.getBlock() instanceof BlockTardimScanner) {
                return new TardimScannerPeripheral(new FakeTardimPeripheralTileEntity(pos, world));
            }
            return null;
        }, FUEL_STORAGE, SCANNER, ROTOR);
    }

    public static void addToTab(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(DIGITAL_TARDIM_INTERFACE);
    }
}
