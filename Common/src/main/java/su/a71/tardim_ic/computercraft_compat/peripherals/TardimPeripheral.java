package su.a71.tardim_ic.computercraft_compat.peripherals;

import com.swdteam.tardim.tardim.TardimData;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import su.a71.tardim_ic.computercraft_compat.entity.ITardimPeripheralTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

// Base CC peripheral for whn you need something with access to a TARDIM
public abstract class TardimPeripheral<BL extends Block> implements IPeripheral {

    private final List<IComputerAccess> connectedComputers = new ArrayList<>();  // List of computers connected to the peripheral
    public final ITardimPeripheralTileEntity tileEntity;  // Peripheral's BlockEntity, used for accessing coordinates
    public final String name;
    private final BL block;

    /**
     * @param tileEntity the tile entity of this peripheral
     * @hidden
     */
    public TardimPeripheral(ITardimPeripheralTileEntity tileEntity, String name, BL block) {
        this.tileEntity = tileEntity;
        this.name = name;
        this.block = block;
    }

    @Override
    public BL getTarget() {
        return this.block;
    }

    /** Setting name for the peripheral. A computer will see it as "digital_tardim_interface_n"
     * @hidden
     */
    @Nonnull
    @Override
    public String getType() { return this.name; }

    /** Apparently CC uses this to check if the peripheral in front of a modem is this one
     * @hidden
     * @param iPeripheral The peripheral to compare against. This may be {@code null}.
     * @return {@code true} if the peripheral is the same as this one.
     */
    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) { return this == iPeripheral; }

    /** Called when a computer disconnects from the peripheral
     * @hidden
     * @param computer The interface to the computer that is being detached. Remember that multiple computers can be
     *                 attached to a peripheral at once.
     */
    @Override
    public void detach(@Nonnull IComputerAccess computer) { connectedComputers.remove(computer); }

    /** Called when a computer connects to the peripheral
     * @hidden
     * @param computer The interface to the computer that is being attached. Remember that multiple computers can be
     *                 attached to a peripheral at once.
     */
    @Override
    public void attach(@Nonnull IComputerAccess computer) { connectedComputers.add(computer); }

    /**
     * I *think* I use this to get peripheral's world position
     * @hidden
     * @return
     */
    public ITardimPeripheralTileEntity getTileEntity() {
        return tileEntity;
    }


    public TardimData getTardimData() throws LuaException {
        TardimData data = this.getTileEntity().getTardim();
        if (data == null || data.getCurrentLocation() == null || data.getOwnerName() == null) {
            throw new LuaException("Peripheral is not inside a TARDIM");
        }
        return data;
    }
}
