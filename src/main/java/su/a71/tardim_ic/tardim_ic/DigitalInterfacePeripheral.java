package su.a71.tardim_ic.tardim_ic;

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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

// TODO: Fabric and Forge diffirence? (Bottom: Fabric)
import com.swdteam.tardim.TardimData;
import com.swdteam.tardim.TardimManager;
import com.swdteam.tardim.TardimData.Location;
//import com.swdteam.tardim.tardim.TardimManager;
//import com.swdteam.tardim.tardim.TardimData;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;


public class DigitalInterfacePeripheral implements IPeripheral {

    private final List<IComputerAccess> connectedComputers = new ArrayList<>();  // List of computers connected to the peripheral
    private final DigitalInterfaceTileEntity tileEntity;  // Peripheral's BlockEntity, used for accessing coordinates

    /**
     * @param tileEntity the tile entity of this peripheral
     */
    public DigitalInterfacePeripheral(DigitalInterfaceTileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    // Setting name for the peripheral. A computer will see it as "digital_tardim_interface_n"
    @Nonnull
    @Override
    public String getType() { return "digital_tardim_interface"; }

    // Apparently CC uses this to check if the peripheral in front of a modem is this one
    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) { return this == iPeripheral; }

    // Called when a computer connects/disconnects from the peripheral
    @Override
    public void detach(@Nonnull IComputerAccess computer) { connectedComputers.remove(computer); }
    @Override
    public void attach(@Nonnull IComputerAccess computer) { connectedComputers.add(computer); }

    public DigitalInterfaceTileEntity getTileEntity() {
        return tileEntity;
    }


    /**
     *  Get TARDIM's data, which we need for *every* function
     * <p>
     * We can't do a simple
     * <code>TardimManager.getFromPos(getTileEntity().getPos())</code>
     * <p>
     * because if someone attempts to call a method outside a TARDIM, this would create a new TARDIM/Point to the one with ID of 0 (Due to the way TardimSaveHandler.loadTardisData works).
     * Which is obviously not what we want.
     * <p>
     * So instead we use this, and get the ability to give user a LuaException if they think that fiddling with time is funny
     * This is mostly a copy of getIDForXZ function with some added checks
     *
     * @return TardimData of the TARDIM that the peripheral is in
     * @throws LuaException if the peripheral is not in a TARDIM
     * */
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

        Location curr = data.getCurrentLocation();
        Location dest = data.getTravelLocation();

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

    /**
     * Check whether the TARDIM is locked
     * @return true if locked, false if not
     */
    @LuaFunction(mainThread = true)
    public final boolean isLocked() throws LuaException {
        return getTardimData().isLocked();
    }

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
     * Get username of the TARDIM's owner
     * @return String of the owner's username
     */
    @LuaFunction(mainThread = true)
    public final String getOwnerName() throws LuaException {
        TardimData data = getTardimData();
        return data.getOwnerName();
    }

    /**
     * Lock/unlock the TARDIM
     * @param locked    true to lock, false to unlock
     */
    @LuaFunction(mainThread = true)
    public final void setLocked(boolean locked) throws LuaException {
        getTardimData().setLocked(locked);
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

    /**
     * Get list of the TARDIM owner's companions
     * @return ObjectLuaTable containing the usernames of the companions
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getCompanions() throws LuaException {
    	TardimData data = getTardimData();
    	ObjectLuaTable companions = new ObjectLuaTable(Map.of());
    	for (int i = 0; i < data.getCompanions().size(); i++) {
    		companions.put(i + 1, data.getCompanions().get(i).getUsername());
    	}
    	return companions;
    }

    /**
     * Set dimension for the TARDIM to travel to
     * <p>
     * This is a serious hazard right now due to the fact that I am unable to check if the dimension is valid.
     * <p>
     * TODO: If invalid dimension is given, the TARDIM is unable to land until the dimension is changed. Add proper checks.
     * @param dimension String of the dimension e.g. "minecraft:overworld"
     */
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

    /**
     * Set the destination's coordinates
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    @LuaFunction(mainThread = true)
    public final void setTravelLocation(int x, int y, int z) throws LuaException {
        TardimData data = getTardimData();
        if (data.getTravelLocation() == null) {
            data.setTravelLocation(new Location(data.getCurrentLocation()));
        }

        data.getTravelLocation().setPosition(x, y, z);
    }


    /**
     * Set destination to the TARDIM's owner's home (Must be online)
     */
    @LuaFunction(mainThread = true)
    public final void home() throws LuaException {
    	TardimData data = getTardimData();

        UUID uuid = data.getOwner();
        String username = data.getOwnerName();
        if (uuid == null || username == null) {
            throw new LuaException("TARDIM has no owner");
        }

        PlayerList playerList = ServerLifecycleHooks.getCurrentServer().getPlayerList();
        ServerPlayer player = playerList.getPlayer(uuid);
        if (player == null) {
            throw new LuaException("TARDIM owner is not online");
        }

        ResourceKey<Level> dim = player.getRespawnDimension();
        BlockPos pos = player.getRespawnPosition();
        if (pos == null) {
            throw new LuaException("TARDIM owner has no home");
        }

        setDimension(dim.location().toString());
        setTravelLocation(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Set destination for a player's location (Player must be online)
     * @param username - String of the username of the player
     */
    @LuaFunction(mainThread = true)
    public final void locatePlayer(String username) throws LuaException {
    	PlayerList playerList = ServerLifecycleHooks.getCurrentServer().getPlayerList();
    	ServerPlayer player = playerList.getPlayerByName(username);
    	if (player == null) {
    		throw new LuaException("Player not found");
    	}

    	ResourceKey<Level> dim = player.getCommandSenderWorld().dimension();
    	BlockPos pos = player.blockPosition();

    	setDimension(dim.location().toString());
    	setTravelLocation(pos.getX(), pos.getY(), pos.getZ());
    }

    // I would love to add this, however it requires TARDIM source code.
    // TODO: If I am ever part of the TARDIM team, I will add this.
    // TODO: locateBiome, demat, remat, setFacing, toggleFacing
}
