package su.a71.tardim_ic.computercraft_compat.digital_interface;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static su.a71.tardim_ic.tardim_ic.registration.ComputerCraftCompat.DIGITAL_INTERFACE_BE;


public class DigitalInterfaceTileEntity extends BlockEntity {
    public DigitalInterfaceTileEntity(BlockPos pos, BlockState state) {
        super(DIGITAL_INTERFACE_BE, pos, state);
    }
}
