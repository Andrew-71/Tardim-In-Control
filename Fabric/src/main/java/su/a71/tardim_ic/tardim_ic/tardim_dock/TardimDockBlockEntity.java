package su.a71.tardim_ic.tardim_ic.tardim_dock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import su.a71.tardim_ic.tardim_ic.Registration;

public class TardimDockBlockEntity extends BlockEntity {
    public boolean isPowered = false;
    public DockData data;
    private BlockPos blockPos;

    public TardimDockBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(Registration.TARDIM_DOCK_BLOCKENTITY, blockPos, blockState);
        this.blockPos = blockPos;
        this.data = new DockData(blockPos);
    }

    public void updateActive() {
        this.data.setActive(this.isPowered);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putString("data_name", data.name);
        tag.putBoolean("data_active", data.active);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
    }
}
