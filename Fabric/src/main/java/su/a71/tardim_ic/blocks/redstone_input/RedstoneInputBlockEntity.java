package su.a71.tardim_ic.blocks.redstone_input;

import com.swdteam.tardim.tileentity.TileEntityBaseTardimPanel;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import su.a71.tardim_ic.tardim_ic.registration.Registration;

import java.util.UUID;


public class RedstoneInputBlockEntity extends TileEntityBaseTardimPanel {
    public boolean isPowered = false;
    public UUID lastPlayer = null;

    public RedstoneInputBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.REDSTONE_INPUT_BE, pos, state);
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
