package su.a71.tardim_ic.tardim_ic.tardim_dock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import su.a71.tardim_ic.tardim_ic.Registration;

public class TardimDockBlockEntity extends BlockEntity {
    public int dock_id;
    public DockData data;

    public TardimDockBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(Registration.TARDIM_DOCK_BLOCKENTITY, blockPos, blockState);
        this.dock_id = 123;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt("dock_id", dock_id);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        dock_id = tag.getInt("dock_id");
    }
}
