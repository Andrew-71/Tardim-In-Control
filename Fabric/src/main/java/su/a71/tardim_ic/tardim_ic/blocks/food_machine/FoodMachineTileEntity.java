package su.a71.tardim_ic.tardim_ic.blocks.food_machine;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import su.a71.tardim_ic.tardim_ic.Registration;

import java.util.UUID;

public class FoodMachineTileEntity extends BlockEntity {
    public int curr_food_index;

    public FoodMachineTileEntity(BlockPos pos, BlockState state) {
        super(Registration.FOOD_MACHINE_TILEENTITY, pos, state);
        this.curr_food_index = 0;
    }

    public BlockPos getPos() {
        return this.worldPosition;
    }


    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt("curr_food_index", curr_food_index);
        //tag.putBoolean("is_powered", isPowered);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        curr_food_index = tag.getInt("curr_food_index");
        //lastPlayer = tag.getUUID("last_player");
        //event.addListener(new FuelMapReloadListener(GSON, "tardim_fuel"));
    }
}
