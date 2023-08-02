package su.a71.tardim_ic.computercraft_compat.entity;

import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class FakeTardimPeripheralTileEntity implements ITardimPeripheralTileEntity {
    public BlockPos blockPos;
    public Level level;
    public TardimData data;  // Our TARDIM

    public FakeTardimPeripheralTileEntity(BlockPos in_block, Level in_level) {
        this.blockPos = in_block;
        this.level = in_level;
        this.data = getTardimDataInitial();
    }

    @Override
    public BlockPos getPos() {
        return this.blockPos;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public TardimData getTardim() {
        return this.data;
    }

    @Override
    public Block getBlock() {
        return this.level.getBlockState(this.blockPos).getBlock();
    }

    public TardimData getTardimDataInitial() {
        int X = this.getPos().getX(), Z = this.getPos().getZ();

        int index = 0;
        int x = 0;
        int y = 0;
        int dx = 0;
        int dy = 1;
        int segment_length = 1;
        int segment_passed = 0;
        boolean found = false;
        long timecheck = System.currentTimeMillis();

        while(true) {
            if (System.currentTimeMillis() - timecheck > 10000L) {
                System.out.println("Finding ID from XZ Coordinates is taking too long!");
                break;
            }

            if (X >= x * TardimManager.INTERIOR_BOUNDS
                    && X <= TardimManager.INTERIOR_BOUNDS + x * TardimManager.INTERIOR_BOUNDS
                    && Z >= y * TardimManager.INTERIOR_BOUNDS
                    && Z <= TardimManager.INTERIOR_BOUNDS + y * TardimManager.INTERIOR_BOUNDS) {
                found = true;
                break;
            }

            x += dx;
            y += dy;
            if (++segment_passed == segment_length) {
                segment_passed = 0;
                int buffer = dy;
                dy = -dx;
                dx = buffer;
                if (buffer == 0) {
                    ++segment_length;
                }
            }

            ++index;
        }

        // We really don't want to access a ghost TARDIM, do we?
        // If we fail checks here are not inside a TARDIM
        if (!found) {
            return null;
        }
        TardimData T = TardimManager.getTardim(index);
        if (T.getCurrentLocation() == null || T.getOwnerName() == null) {
            return null;
        }

        return T;
    }
}
