package su.a71.tardim_ic.computercraft_compat.entity;

import com.swdteam.tardim.tardim.TardimData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

// This is used to getPost(), getLevel() and getTardim() nicely without refactoring code to account for PeripheralProvider
// At least I believe so. Otherwise, don't really remember why I don't just pass these methods to the peripherals.
public interface ITardimPeripheralTileEntity {
    public BlockPos getPos();
    public Level getLevel();

    public TardimData getTardim();

    public Block getBlock();
}
