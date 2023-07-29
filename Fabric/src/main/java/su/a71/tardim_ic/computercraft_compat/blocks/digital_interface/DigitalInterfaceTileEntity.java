package su.a71.tardim_ic.computercraft_compat.blocks.digital_interface;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import su.a71.tardim_ic.tardim_ic.registration.ComputerCraftCompat;


public class DigitalInterfaceTileEntity extends BlockEntity {//implements IDigitalInterfaceEntity {
    public DigitalInterfaceTileEntity(BlockPos pos, BlockState state) {
        super(ComputerCraftCompat.DIGITAL_TARDIM_INTERFACE_TILEENTITY, pos, state);
    }
}
