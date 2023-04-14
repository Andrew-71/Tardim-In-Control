package su.a71.tardim_ic.tardim_ic.digital_interface;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import su.a71.tardim_ic.tardim_ic.Registration;


public class DigitalInterfaceTileEntity extends BlockEntity implements IDigitalInterfaceEntity {

    public DigitalInterfaceTileEntity(BlockPos pos, BlockState state) {
        super(Registration.DIGITAL_TARDIM_INTERFACE_TILEENTITY, pos, state);
    }

    public BlockPos getPos() {
        return this.worldPosition;
    }

    /**
     * The peripheral
     */
    protected DigitalInterfacePeripheral peripheral = new DigitalInterfacePeripheral(this);
}
