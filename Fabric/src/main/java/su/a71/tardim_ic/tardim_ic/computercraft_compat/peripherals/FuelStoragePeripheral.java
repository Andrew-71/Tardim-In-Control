package su.a71.tardim_ic.tardim_ic.computercraft_compat.peripherals;

import com.swdteam.tardim.tardim.TardimData;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.phys.Vec3;
import su.a71.tardim_ic.tardim_ic.computercraft_compat.FakeTardimPeripheralTileEntity;

import javax.annotation.Nonnull;


/*
* CC Peripheral for TARDIM's fuel storage block.
* Only provides getters for the fuel parts for people who cannot afford or don't need the digital interface.
 */
public class FuelStoragePeripheral extends TardimPeripheral implements IPeripheral {
    /**
     * @param tileEntity the tile entity of this peripheral
     * @hidden
     */
    public FuelStoragePeripheral(FakeTardimPeripheralTileEntity tileEntity) {
        super(tileEntity);
    }

    /** Setting name for the peripheral. A computer will see it as "digital_tardim_interface_n"
     * @hidden
     */
    @Nonnull
    @Override
    public String getType() { return "tardim_fuel_storage"; }


    // Peripheral methods ===============================================================

    /**
     * Return how much fuel is left in the TARDIM
     *
     * @return Fuel left (Out of 100)
     */
    @LuaFunction(mainThread = true)
    public final double getFuel() throws LuaException {
        return getTardimData().getFuel();
    }

    /**
     * Get how much fuel it would take to travel to the destination
     * @return Amount of fuel needed (Out of 100)
     */
    @LuaFunction(mainThread = true)
    public final double calculateFuelForJourney() throws LuaException {
        TardimData data = getTardimData();

        if (data.getTravelLocation() == null) return 0;

        TardimData.Location curr = data.getCurrentLocation();
        TardimData.Location dest = data.getTravelLocation();

        double fuel = 0.0;

        if (curr.getLevel() != dest.getLevel())
        {
            fuel = 10.0;
        }

        Vec3 posA = new Vec3(curr.getPos().getX(), curr.getPos().getY(), curr.getPos().getZ());
        Vec3 posB = new Vec3(dest.getPos().getX(), dest.getPos().getY(), dest.getPos().getZ());
        fuel += posA.distanceTo(posB) / 100.0;
        if (fuel > 100.0) fuel = 100.0;

        return fuel;
    }
}