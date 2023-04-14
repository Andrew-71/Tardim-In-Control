package su.a71.tardim_ic.tardim_ic.digital_interface;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class FakeDigitalInterfaceTileEntity implements IDigitalInterfaceEntity {
    public BlockPos blockPos;
    public Level level;

    FakeDigitalInterfaceTileEntity(BlockPos in_block, Level in_level) {
        this.blockPos = in_block;
        this.level = in_level;
    }

    @Override
    public BlockPos getPos() {
        return this.blockPos;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }
}
