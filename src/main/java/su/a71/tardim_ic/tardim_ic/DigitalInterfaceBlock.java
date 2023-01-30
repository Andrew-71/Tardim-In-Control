package su.a71.tardim_ic.tardim_ic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public class DigitalInterfaceBlock extends Block implements EntityBlock {

    public DigitalInterfaceBlock() {
        super(Properties.of(Material.METAL).strength(5, 5).noOcclusion());
    }

    /**
     * This is the method from {@link EntityBlock} to create a new block entity for our block
     *
     * @return A new block entity from our registry object
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return Registration.DIGITAL_TARDIM_INTERFACE_TILEENTITY.get().create(pos, state);
    }
}
