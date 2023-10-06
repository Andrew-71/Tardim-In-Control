package su.a71.tardim_ic.computercraft_compat.digital_interface;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import su.a71.tardim_ic.tardim_ic.registration.ComputerCraftCompat;

import javax.annotation.Nullable;


public class DigitalInterfaceBlock extends Block implements EntityBlock {

    public DigitalInterfaceBlock() {
        super(Properties.of().strength(2, 4).noOcclusion().mapColor(MapColor.METAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ComputerCraftCompat.DIGITAL_INTERFACE_BE.create(pos, state);
    }
}
