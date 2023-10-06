package su.a71.tardim_ic.soviet_chronobox;

import com.swdteam.tardim.common.init.TRDTiles;
import com.swdteam.tardim.tileentity.TileEntityTardim;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import su.a71.tardim_ic.tardim_ic.registration.Exteriors;

public class SovietChronoboxTileEntity extends TileEntityTardim {
    public SovietChronoboxTileEntity(BlockPos pos, BlockState state) {
        super(Exteriors.TILE_SOVIET_CHRONOBOX, pos, state);
    }
}