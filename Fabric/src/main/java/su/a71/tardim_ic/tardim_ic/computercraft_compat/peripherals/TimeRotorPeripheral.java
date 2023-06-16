package su.a71.tardim_ic.tardim_ic.computercraft_compat.peripherals;

import com.swdteam.tardim.tardim.TardimData;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.ObjectLuaTable;
import dan200.computercraft.api.peripheral.IPeripheral;
import su.a71.tardim_ic.tardim_ic.computercraft_compat.FakeTardimPeripheralTileEntity;

import javax.annotation.Nonnull;
import java.util.Map;

/*
 * CC Peripheral for TARDIM's time rotor block.
 * Only provides getters for the flight status for people who cannot afford or don't need the digital interface.
 */
public class TimeRotorPeripheral extends TardimPeripheral implements IPeripheral {
    /**
     * @param tileEntity the tile entity of this peripheral
     * @hidden
     */
    public TimeRotorPeripheral(FakeTardimPeripheralTileEntity tileEntity) {
        super(tileEntity);
    }

    /** Setting name for the peripheral. A computer will see it as "digital_tardim_interface_n"
     * @hidden
     */
    @Nonnull
    @Override
    public String getType() { return "tardim_time_rotor"; }


    // Peripheral methods ===============================================================

    /**
     * Check whether the TARDIM is in flight
     * @return true if in flight, false if not
     */
    @LuaFunction(mainThread = true)
    public final boolean isInFlight() throws LuaException { return getTardimData().isInFlight(); }

    /**
     * Supposedly gets UNIX timestamp of when we entered flight
     * @return UNIX timestamp if in flight, -1 if not
     */
    @LuaFunction(mainThread = true)
    public final long getTimeEnteredFlight() throws LuaException {
        TardimData data = getTardimData();
        if (!data.isInFlight()) {
            return -1;
        }
        return data.getTimeEnteredFlight();
    }

    /**
     * Get the current location of the TARDIM
     * @return ObjectLuaTable of the current location with the following keys:
     * <ul>
     *     <li>dimension - String of the dimension</li>
     *     <li>pos - table with the keys x, y, z that hold numbers</li>
     *     <li>facing - String of the facing</li>
     * </ul>
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getCurrentLocation() throws LuaException {
        TardimData.Location loc = getTardimData().getCurrentLocation();
        return new ObjectLuaTable(Map.of(
                "dimension", loc.getLevel().location().toString(),
                "pos", new ObjectLuaTable(Map.of(
                        "x", loc.getPos().getX(),
                        "y", loc.getPos().getY(),
                        "z", loc.getPos().getZ()
                )),
                "facing", loc.getFacing().toString()
        ));
    }

    /**
     * Get the current location of the TARDIM
     * @return if there is no destination returns null.
     * <p>
     * Otherwise, ObjectLuaTable of the current location with the following keys:
     *  <ul>
     *      <li>dimension - String of the dimension</li>
     *      <li>pos - table with the keys x, y, z that hold numbers</li>
     *      <li>facing - String of the facing</li>
     *  </ul>
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getTravelLocation() throws LuaException {
        TardimData data = getTardimData();
        if (data.getTravelLocation() == null) {
            data.setTravelLocation(data.getCurrentLocation());
        }
        TardimData.Location loc = data.getTravelLocation();
        return new ObjectLuaTable(Map.of(
                "dimension", loc.getLevel().location().toString(),
                "pos", new ObjectLuaTable(Map.of(
                        "x", loc.getPos().getX(),
                        "y", loc.getPos().getY(),
                        "z", loc.getPos().getZ()
                )),
                "facing", loc.getFacing().toString()
        ));
    }
}