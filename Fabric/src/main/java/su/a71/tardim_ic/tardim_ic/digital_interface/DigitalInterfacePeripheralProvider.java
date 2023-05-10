package su.a71.tardim_ic.tardim_ic.digital_interface;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DigitalInterfacePeripheralProvider implements IPeripheralProvider {
    @NotNull
    @Override
    public IPeripheral getPeripheral(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        if (level.isClientSide()) return null; // Please...?
        if (level.getBlockState(blockPos).getBlock() instanceof DigitalInterfaceBlock) {
            return new DigitalInterfacePeripheral(new FakeDigitalInterfaceTileEntity(blockPos, level));
        }

        return null;
    }
}