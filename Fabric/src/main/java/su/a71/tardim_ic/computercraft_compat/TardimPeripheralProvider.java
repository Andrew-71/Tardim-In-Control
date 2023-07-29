package su.a71.tardim_ic.computercraft_compat;

import com.swdteam.tardim.common.block.BlockFuelStorage;
import com.swdteam.tardim.common.block.BlockRotor;
import com.swdteam.tardim.common.block.BlockTardimScanner;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import su.a71.tardim_ic.computercraft_compat.peripherals.TimeRotorPeripheral;
import su.a71.tardim_ic.computercraft_compat.peripherals.FuelStoragePeripheral;
import su.a71.tardim_ic.computercraft_compat.peripherals.TardimScannerPeripheral;
import su.a71.tardim_ic.computercraft_compat.blocks.digital_interface.DigitalInterfaceBlock;
import su.a71.tardim_ic.computercraft_compat.peripherals.DigitalInterfacePeripheral;

public class TardimPeripheralProvider implements IPeripheralProvider {
    @Override
    public IPeripheral getPeripheral(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        if (level.isClientSide()) return null; // Please...?

        Block block = level.getBlockState(blockPos).getBlock();
        if (block instanceof DigitalInterfaceBlock) {
            return new DigitalInterfacePeripheral(new FakeTardimPeripheralTileEntity(blockPos, level));
        } else if (block instanceof BlockFuelStorage) {
            return new FuelStoragePeripheral(new FakeTardimPeripheralTileEntity(blockPos, level));
        } else if (block instanceof BlockRotor) {
            return new TimeRotorPeripheral(new FakeTardimPeripheralTileEntity(blockPos, level));
        } else if (block instanceof BlockTardimScanner) {
            return new TardimScannerPeripheral(new FakeTardimPeripheralTileEntity(blockPos, level));
        }

        return null;
    }
}