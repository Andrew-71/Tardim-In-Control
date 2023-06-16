package su.a71.tardim_ic.tardim_ic.blocks.food_machine;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import su.a71.tardim_ic.tardim_ic.Registration;

import java.util.UUID;

public class FoodMachineTileEntity extends BlockEntity {
    public boolean isPowered = false;
    public UUID lastPlayer = null;

    public FoodMachineTileEntity(BlockPos pos, BlockState state) {
        super(Registration.REDSTONE_TARDIM_INPUT_TILEENTITY, pos, state);
    }

    public BlockPos getPos() {
        return this.worldPosition;
    }


    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putBoolean("is_powered", isPowered);
        if (lastPlayer != null) {
            tag.putUUID("last_player", lastPlayer);
        }
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        isPowered = tag.getBoolean("is_powered");
        lastPlayer = tag.getUUID("last_player");
    }
}
