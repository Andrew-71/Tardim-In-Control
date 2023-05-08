package su.a71.tardim_ic.tardim_ic.digital_interface;

import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import su.a71.tardim_ic.tardim_ic.Registration;


public class DigitalInterfaceTileEntity extends BlockEntity {//implements IDigitalInterfaceEntity {
    //public TardimData data;  // Our TARDIM


    public DigitalInterfaceTileEntity(BlockPos pos, BlockState state) {
        super(Registration.DIGITAL_TARDIM_INTERFACE_TILEENTITY, pos, state);
        //this.data = getTardimDataInitial();
    }

//    public BlockPos getPos() {
//        return this.worldPosition;
//    }
//
//    @Override
//    public TardimData getTardim() {
//        return null;
//    }

//    /**
//     * The peripheral
//     */
//    protected DigitalInterfacePeripheral peripheral = new DigitalInterfacePeripheral(this);

//    public TardimData getTardimDataInitial() {
//        int X = this.getPos().getX(), Z = this.getPos().getZ();
//
//        int index = 0;
//        int x = 0;
//        int y = 0;
//        int dx = 0;
//        int dy = 1;
//        int segment_length = 1;
//        int segment_passed = 0;
//        boolean found = false;
//        long timecheck = System.currentTimeMillis();
//
//        while(true) {
//            if (System.currentTimeMillis() - timecheck > 10000L) {
//                System.out.println("Finding ID from XZ Coordinates is taking too long!");
//                break;
//            }
//
//            if (X >= x * TardimManager.INTERIOR_BOUNDS
//                    && X <= TardimManager.INTERIOR_BOUNDS + x * TardimManager.INTERIOR_BOUNDS
//                    && Z >= y * TardimManager.INTERIOR_BOUNDS
//                    && Z <= TardimManager.INTERIOR_BOUNDS + y * TardimManager.INTERIOR_BOUNDS) {
//                found = true;
//                break;
//            }
//
//            x += dx;
//            y += dy;
//            if (++segment_passed == segment_length) {
//                segment_passed = 0;
//                int buffer = dy;
//                dy = -dx;
//                dx = buffer;
//                if (buffer == 0) {
//                    ++segment_length;
//                }
//            }
//
//            ++index;
//        }
//
//        // We really don't want to access a ghost TARDIM, do we?
//        // If we fail checks here are not inside a TARDIM
//        if (!found) {
//            return null;
//        }
//        TardimData T = TardimManager.getTardim(index);
//        if (T.getCurrentLocation() == null || T.getOwnerName() == null) {
//            return null;
//        }
//
//        return T;
//    }
//
//    @Override
//    public void load(CompoundTag tag) {
//        super.load(tag);
//        this.data = getTardimDataInitial();
//    }
}
