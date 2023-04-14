package su.a71.tardim_ic.tardim_ic.redstone_input;

import com.swdteam.tardim.tileentity.TileEntityBaseTardimPanel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import su.a71.tardim_ic.tardim_ic.Registration;


public class RedstoneInputTileEntity extends TileEntityBaseTardimPanel {
    public RedstoneInputTileEntity(BlockPos pos, BlockState state) {
        super(Registration.REDSTONE_TARDIM_INPUT_TILEENTITY, pos, state);
    }

    public BlockPos getPos() {
        return this.worldPosition;
    }
}
