package de.srendi.cctutorial.cctutorial;

import com.swdteam.common.command.tardim.CommandTardimBase;
import com.swdteam.common.command.tardim.CommandTravel;
import com.swdteam.common.data.DimensionMapReloadListener;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.lua.ObjectLuaTable;
import dan200.computercraft.api.lua.LuaException;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;


// TODO: Fabric and Forge diffirence? (Bottom: Fabric)
import com.swdteam.tardim.TardimData;
import com.swdteam.tardim.TardimManager;
import com.swdteam.tardim.TardimData.Location;
//import com.swdteam.tardim.tardim.TardimManager;
//import com.swdteam.tardim.tardim.TardimData;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Our peripheral class, this is the class where we will register functions for our block.
 */
public class CCPeripheral implements IPeripheral {

    private final List<IComputerAccess> connectedComputers = new ArrayList<>();  // List of computers connected to the peripheral
    private final CCTileEntity tileEntity;  // Peripheral's BlockEntity, used for accessing coordinates

    /**
     * @param tileEntity the tile entity of this peripheral
     */
    public CCPeripheral(CCTileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    // Setting name for the peripheral. A computer will see it as "digital_tardim_interface_n"
    @Nonnull
    @Override
    public String getType() { return "digital_tardim_interface"; }

    // Apparently CC uses this to check if the peripheral in front of a modem is this one
    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) { return this == iPeripheral; }

    // Called when a computer disconnects from the peripheral
    @Override
    public void detach(@Nonnull IComputerAccess computer) { connectedComputers.remove(computer); }

    // Called when a computer connects to the peripheral
    // TODO: add a sound effect? Like a simple TARDIS beep?
    @Override
    public void attach(@Nonnull IComputerAccess computer) { connectedComputers.add(computer); }

    public CCTileEntity getTileEntity() {
        return tileEntity;
    }


    /* Get TARDIM's data, which we need for *every* function
    *
    * We can't do a simple
    *   TardimManager.getFromPos(getTileEntity().getPos())
    * because if someone attempts to call a method outside a TARDIM, this would create a new TARDIM/Point to the one with ID of 0 (Due to the way TardimSaveHandler.loadTardisData works).
    * Which is obviously not what we want.
    *
    * So instead we use this, and recieve ability to give user a LuaException if they think that fiddling with time devices is funny
    * This is mostly a copy of getIDForXZ function */
    public TardimData getTardimData() throws LuaException {
        int X = getTileEntity().getPos().getX(), Z = getTileEntity().getPos().getZ();

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
        if (!found) {
            throw new LuaException("Peripheral is not inside a TARDIM");
        }
        TardimData T = TardimManager.getTardim(index);
        if (T.getCurrentLocation() == null || T.getOwnerName() == null) {
            throw new LuaException("Peripheral is not inside a TARDIM");
        }

    	return T;
    }

    // Peripheral methods ===============================================================

    // Get amount of fuel we have (Out of 100)
    @LuaFunction(mainThread = true)
    public final double getFuel() throws LuaException {
        return getTardimData().getFuel();
    }

    @LuaFunction(mainThread = true)
    public final double calculateFuelForJourney() throws LuaException {
        TardimData data = getTardimData();

        if (data.getTravelLocation() == null) return 0;

        Location curr = data.getCurrentLocation();
        Location dest = data.getTravelLocation();

        double fuel = 0.0;

        if (curr.getLevel() != dest.getLevel())
        {
            fuel = 10.0;
        }

        return 100; //data.calculateFuelForJourney(((Level) curr.getLevel()), dest.getLevel().location(), curr.getPos(), dest.getPos());
    }

    // Check whether the TARDIM is locked
    @LuaFunction(mainThread = true)
    public final boolean isLocked() throws LuaException {
        return getTardimData().isLocked();
    }

    //  Check whether the TARDIM is in flight
    @LuaFunction(mainThread = true)
    public final boolean isInFlight() throws LuaException { return getTardimData().isInFlight(); }

    // Supposedly gets UNIX timestamp of when we entered flight
    @LuaFunction(mainThread = true)
    public final long getTimeEnteredFlight() throws LuaException {
        TardimData data = getTardimData();
        if (!data.isInFlight()) {
            return -1;
        }
        return data.getTimeEnteredFlight();
    }

    // Get username of the TARDIM's owner
    @LuaFunction(mainThread = true)
    public final String getOwnerName() throws LuaException {
        TardimData data = getTardimData();
        return data.getOwnerName();
    }

    // Lock/Unlock the TARDIM
    @LuaFunction(mainThread = true)
    public final void setLocked(boolean locked) throws LuaException {
        getTardimData().setLocked(locked);
    }

    // Returns table with current TARDIM location
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getCurrentLocation() throws LuaException {
    	Location loc = getTardimData().getCurrentLocation();
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

    // Returns flight destination (or null if there isn't one)
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getTravelLocation() throws LuaException {
    	TardimData data = getTardimData();
        if (data.getTravelLocation() != null) {
        	Location loc = data.getTravelLocation();
            return new ObjectLuaTable(Map.of(
                "dimension", loc.getLevel().location().toString(),
                "pos", new ObjectLuaTable(Map.of(
                    "x", loc.getPos().getX(),
                    "y", loc.getPos().getY(),
                    "z", loc.getPos().getZ()
                )),
            "facing", loc.getFacing().toString()
            ));
        } else {
        	return null;
        }
    }

    // Returns table with all companions of this TARDIM's owner
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getCompanions() throws LuaException {
    	TardimData data = getTardimData();
    	ObjectLuaTable companions = new ObjectLuaTable(Map.of());
    	for (int i = 0; i < data.getCompanions().size(); i++) {
    		companions.put(i + 1, data.getCompanions().get(i).getUsername());
    	}
    	return companions;
    }

    // Supposed to set dimension of the destination
    // TODO: This looks like a hazard if someone inserts a dimension that doesn't exist
    @LuaFunction(mainThread = true)
    public final void setDimension(String dimension) throws LuaException {
    	TardimData data = getTardimData();


        String key = dimension;
        dimension = DimensionMapReloadListener.toTitleCase(dimension);
        if (TardimManager.DIMENSION_MAP.containsKey(dimension)) {
            key = (String)TardimManager.DIMENSION_MAP.get(dimension);
        } else {
            dimension = dimension.toLowerCase();
        }

        if (!CommandTravel.isValidPath(key)) {
            throw new LuaException("Invalid dimension");
        } else {
            ResourceKey<Level> dim = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dimension));
            if (data.getTravelLocation() == null) {
                data.setTravelLocation(new Location(data.getCurrentLocation()));
            }

            data.getTravelLocation().setLocation(dim);
        }
    }

    // Set X, Y and Z of travel destination
    @LuaFunction(mainThread = true)
    public final void setTravelLocation(int x, int y, int z) throws LuaException {
        TardimData data = getTardimData();
        if (data.getTravelLocation() == null) {
            data.setTravelLocation(new Location(data.getCurrentLocation()));
        }

        data.getTravelLocation().setPosition(x, y, z);
    }

    /*
    @LuaFunction(mainThread = true)
    public final void demat() throws LuaException {
    	TardimData data = getTardimData();
    	data.setInFlight(true);
    }

    @LuaFunction(mainThread = true)
    public final void remat() throws LuaException {
        TardimData data = getTardimData();
        data.setInFlight(false);
    }

    */
}
