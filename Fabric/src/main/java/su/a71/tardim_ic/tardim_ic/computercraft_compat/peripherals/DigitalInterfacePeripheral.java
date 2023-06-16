package su.a71.tardim_ic.tardim_ic.computercraft_compat.peripherals;

import com.swdteam.tardim.common.command.tardim.CommandTravel;
import com.swdteam.tardim.common.data.DimensionMapReloadListener;
import com.swdteam.tardim.common.init.TRDSounds;
import com.swdteam.tardim.common.init.TardimRegistry;
import com.swdteam.tardim.common.item.ItemTardim;
import com.swdteam.tardim.main.Tardim;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimData.Location;
import com.swdteam.tardim.tardim.TardimManager;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.ObjectLuaTable;
import dan200.computercraft.api.peripheral.IPeripheral;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import su.a71.tardim_ic.tardim_ic.Registration;
import su.a71.tardim_ic.tardim_ic.computercraft_compat.FakeTardimPeripheralTileEntity;
import su.a71.tardim_ic.tardim_ic.utils.FakePlayer;
import static su.a71.tardim_ic.tardim_ic.Registration.LOCATION_JAMMER;

import javax.annotation.Nonnull;
import java.util.*;


public class DigitalInterfacePeripheral extends TardimPeripheral implements IPeripheral {
    /**
     * @param tileEntity the tile entity of this peripheral
     * @hidden
     */
    public DigitalInterfacePeripheral(FakeTardimPeripheralTileEntity tileEntity) {
        super(tileEntity);
    }

    /** Setting name for the peripheral. A computer will see it as "digital_tardim_interface_n"
     * @hidden
     */
    @Nonnull
    @Override
    public String getType() { return "digital_tardim_interface"; }

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
        if (data.getTravelLocation() == null) {
            data.setTravelLocation(data.getCurrentLocation());
        }
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
    }

    /**
     * Get list of the TARDIM owner's companions
     * @return ObjectLuaTable containing the usernames of the companions
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getCompanions() throws LuaException {
    	TardimData data = getTardimData();
        Map<Integer, String> companions = new HashMap<>();
    	for (int i = 0; i < data.getCompanions().size(); i++) {
    		companions.put(i + 1, data.getCompanions().get(i).getUsername());
    	}
    	return new ObjectLuaTable(companions);
    }

    /**
     * CommandTravel.isValidPath is for some reason private on Fabric so we reverse engineer it :/
     * SWDteam pls fix
     * @hidden
     */
    private static boolean isValidPathTemp(String s) {
        for(int i = 0; i < s.length(); ++i) {
            if (!CommandTravel.validPathChar(s.charAt(i))) {
                return false;
            }
        }
        return true;
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

        if (!isValidPathTemp(key)) {
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
        if (this.getTileEntity().getLevel().isClientSide()) {
            return;
        }
    	TardimData data = getTardimData();

        UUID uuid = data.getOwner();
        String username = data.getOwnerName();
        if (uuid == null || username == null) {
            throw new LuaException("TARDIM has no owner");
        }

        PlayerList playerList = this.getTileEntity().getLevel().getServer().getPlayerList();
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
        if (this.getTileEntity().getLevel().isClientSide()) {
            return;
        }

        PlayerList playerList = this.getTileEntity().getLevel().getServer().getPlayerList();

        ServerPlayer player = playerList.getPlayerByName(username);
    	if (player == null) {
    		throw new LuaException("Player not found");
    	}

        for (ItemStack armour : player.getArmorSlots()) {
            if (armour.is(LOCATION_JAMMER)) {
                throw new LuaException("Player location jammed");
            };
        }

    	ResourceKey<Level> dim = player.getCommandSenderWorld().dimension();
    	BlockPos pos = player.blockPosition();

    	setDimension(dim.location().toString());
    	setTravelLocation(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Get online players. Useful for making a GUI for the locate function or just a nice dashboard.
     *
     * @return ObjectLuaTable of the online players
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getOnlinePlayers() throws LuaException {
        if (this.getTileEntity().getLevel().isClientSide()) {
            return null;
        }

    	PlayerList playerList = this.getTileEntity().getLevel().getServer().getPlayerList();
        Map<Integer, String> players = new HashMap<>();
        for (int i = 0; i < playerList.getPlayers().size(); i++) {
            players.put(i + 1, playerList.getPlayers().get(i).getGameProfile().getName());
        }

    	return new ObjectLuaTable(players);
    }

    /**
     * Get the rotation of the TARDIM's door
     * @return String of the door rotation ("north", "south", "east", "west")
     */
    @LuaFunction(mainThread = true)
    public final String getDoorRotation() throws LuaException {
    	TardimData data = getTardimData();
    	Direction rotation = data.getTravelLocation().getFacing();
        switch (rotation) {
            case NORTH -> {
                return "north";
            }
            case EAST -> {
                return "east";
            }
            case SOUTH -> {
                return "south";
            }
            case WEST -> {
                return "west";
            }
            default -> {
                throw new LuaException("Invalid door rotation");
            }
        }
    }

    /**
     * Set the rotation of the TARDIM's door
     * @param rotation  String of the door rotation ("north", "south", "east", "west")
     */
    @LuaFunction(mainThread = true)
    public final void setDoorRotation(String rotation) throws LuaException {
    	TardimData data = getTardimData();
        switch (rotation) {
            case "north" -> data.getTravelLocation().setFacing(Direction.NORTH);
            case "east" -> data.getTravelLocation().setFacing(Direction.EAST);
            case "south" -> data.getTravelLocation().setFacing(Direction.SOUTH);
            case "west" -> data.getTravelLocation().setFacing(Direction.WEST);
            default -> throw new LuaException("Invalid door rotation");
        }

        data.save();
    }

    /**
     * Toggle the rotation of the TARDIM's door (north -> east -> south -> west -> north)
     */
    @LuaFunction(mainThread = true)
    public final void toggleDoorRotation() throws LuaException {
    	TardimData data = getTardimData();
        if (data.getTravelLocation() == null) {
            data.setTravelLocation(new Location(data.getCurrentLocation()));
        }

        if (data.getTravelLocation().getFacing() == null) {
            data.getTravelLocation().setFacing(Direction.NORTH);
        }

        switch (data.getTravelLocation().getFacing()) {
            case NORTH -> data.getTravelLocation().setFacing(Direction.EAST);
            case EAST -> data.getTravelLocation().setFacing(Direction.SOUTH);
            case SOUTH -> data.getTravelLocation().setFacing(Direction.WEST);
            case WEST -> data.getTravelLocation().setFacing(Direction.NORTH);
            default -> data.getTravelLocation().setFacing(Direction.NORTH);
        }

        data.save();
    }

    /**
     * Add a number to the destination's coordinates
     * @param axis  String of the axis ("x", "y", "z")
     * @param amount    Number to add to the axis
     */
    @LuaFunction(mainThread = true)
    public final void coordAdd(String axis, int amount) throws LuaException {
    	TardimData data = getTardimData();
    	if (data.getTravelLocation() == null) {
    		data.setTravelLocation(new Location(data.getCurrentLocation()));
    	}

    	Location location = data.getTravelLocation();
    	switch (axis) {
    		case "x" -> location.addPosition(amount, 0, 0);
            case "y" -> location.addPosition(0, amount, 0);
            case "z" -> location.addPosition(0, 0, amount);
            default -> throw new LuaException("Invalid axis");
    	}
    }

    /**
     * Dematerialize the TARDIM
     */
    @LuaFunction(mainThread = true)
    public final void demat() throws LuaException {
        if (this.tileEntity.getLevel().isClientSide()) {
            return;
        }

    	TardimData data = getTardimData();

        if (data.isInFlight()) {
            throw new LuaException("TARDIM is already in flight");
        }
        Location loc = data.getCurrentLocation();
        ServerLevel level = this.tileEntity.getLevel().getServer().getLevel(loc.getLevel());
        ItemTardim.destroyTardim(level, loc.getPos(), Direction.NORTH);
        data.setInFlight(true);
    	if (data.getTravelLocation() == null) {
            data.setTravelLocation(new Location(data.getCurrentLocation()));
        }

        // TODO: This is a horrendous way of doing this. Please fix.
        String level_str = "tardim:tardis_dimension";
        this.tileEntity.getLevel().getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(level_str))).playSound(null, this.tileEntity.getPos(), (SoundEvent) TRDSounds.TARDIM_TAKEOFF, SoundSource.AMBIENT, 1.0F, 1.0F);

        data.save();
    }

    /**
     * Materialize the TARDIM at the destination
     * <p>
     * Has a LOT of checks to make sure the TARDIM can materialize, so please implement error handling if you use this.
     */
    @LuaFunction(mainThread = true)
    public final void remat() throws LuaException {
        if (this.tileEntity.getLevel().isClientSide()) {
            return;
        }

        TardimData data = getTardimData();

        if (data.isInFlight()) {
            if (data.getTimeEnteredFlight() < System.currentTimeMillis() / 1000L - 10L) {
                Location loc = data.getTravelLocation();
                ServerLevel level = this.tileEntity.getLevel().getServer().getLevel(loc.getLevel());
                double fuel = data.calculateFuelForJourney(
                        this.tileEntity.getLevel().getServer().getLevel(data.getCurrentLocation().getLevel()), level, data.getCurrentLocation().getPos(), loc.getPos()
                );
                if (data.getFuel() >= fuel) {
                    level.getChunk(loc.getPos());
                    BlockPos landingPosButBetter = CommandTravel.getLandingPosition(level, loc.getPos());
                    boolean recalc = false;

                    for(int jj = 0; jj < 32; ++jj) {
                        if (!Block.canSupportRigidBlock(level, landingPosButBetter.below())) {
                            BlockPos pos2 = landingPosButBetter.offset(
                                    level.random.nextInt(10) * (level.random.nextBoolean() ? 1 : -1),
                                    0,
                                    level.random.nextInt(10) * (level.random.nextBoolean() ? 1 : -1)
                            );
                            landingPosButBetter = CommandTravel.getLandingPosition(level, pos2);
                            if (Block.canSupportRigidBlock(level, landingPosButBetter.below())) {
                                recalc = true;
                                break;
                            }
                        }
                    }

                    if (!recalc) {
                        for(int jj = 0; jj < 32; ++jj) {
                            if (!Block.canSupportRigidBlock(level, landingPosButBetter.below())) {
                                BlockPos pos2 = landingPosButBetter.offset(
                                        level.random.nextInt(30) * (level.random.nextBoolean() ? 1 : -1),
                                        0,
                                        level.random.nextInt(30) * (level.random.nextBoolean() ? 1 : -1)
                                );
                                landingPosButBetter = CommandTravel.getLandingPosition(level, pos2);
                                if (Block.canSupportRigidBlock(level, landingPosButBetter.below())) {
                                    recalc = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!recalc) {
                        for(int jj = 0; jj < 32; ++jj) {
                            if (!Block.canSupportRigidBlock(level, landingPosButBetter.below())) {
                                BlockPos pos2 = landingPosButBetter.offset(
                                        level.random.nextInt(50) * (level.random.nextBoolean() ? 1 : -1),
                                        0,
                                        level.random.nextInt(50) * (level.random.nextBoolean() ? 1 : -1)
                                );
                                landingPosButBetter = CommandTravel.getLandingPosition(level, pos2);
                                if (Block.canSupportRigidBlock(level, landingPosButBetter.below())) {
                                    recalc = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (Block.canSupportRigidBlock(level, landingPosButBetter.below())) {
                        loc.setPosition(landingPosButBetter.getX(), landingPosButBetter.getY(), landingPosButBetter.getZ());
                        if (Tardim.isPosValid(level, loc.getPos())) {
                            TardimRegistry.TardimBuilder builder = TardimRegistry.getTardimBuilder(data.getTardimID());
                            builder.buildTardim(level, loc.getPos(), data.getTravelLocation().getFacing(), data.getId());
                            data.setCurrentLocation(data.getTravelLocation());
                            data.setTravelLocation(null);
                            data.setInFlight(false);
                            data.addFuel(-fuel);
                            data.save();

//                            if (!recalc) {
//                                sendResponse(player, "TARDIM is landing", CommandTardimBase.ResponseType.COMPLETE, source);
//                            } else {
//                                sendResponse(player, "Landing recalculated due to obstruction", CommandTardimBase.ResponseType.INFO, source);
//                                sendResponse(player, "TARDIM is landing", CommandTardimBase.ResponseType.COMPLETE, source);
//                            }

                            String level_str = "tardim:tardis_dimension";
                            this.tileEntity.getLevel().getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(level_str))).playSound(null, this.tileEntity.getPos(), (SoundEvent) TRDSounds.TARDIM_LANDING, SoundSource.AMBIENT, 1.0F, 1.0F);

                        } else {
                            throw new LuaException("TARDIM landing obstructed. Aborting...");
                        }
                    } else {
                        throw new LuaException("TARDIM landing obstructed. Aborting...");
                    }
                } else {
                    throw new LuaException("Not enough fuel for journey");
                }
            } else {
                throw new LuaException("TARDIM is still taking off");
            }
        } else {
            throw new LuaException("TARDIM has already landed");
        }
    }

    /**
     * Locate a biome
     * @param biome_str String of the biome e.g. "minecraft:plains"
     */
    @LuaFunction(mainThread = true)
    public final void locateBiome(String biome_str) throws LuaException {
        if (this.tileEntity.getLevel().isClientSide()) {
            return;
        }

        TardimData data = getTardimData();
        if (data.getTravelLocation() == null) {
            data.setTravelLocation(new Location(data.getCurrentLocation()));
        }

        Optional<Biome> biome = this.tileEntity.getLevel().getServer()
                .registryAccess()
                .registryOrThrow(Registry.BIOME_REGISTRY)
                .getOptional(new ResourceLocation(biome_str));
        if (biome != null && biome.isPresent()) {
            if (data.getTravelLocation() == null) {
                data.setTravelLocation(new Location(data.getCurrentLocation()));
            }

            ServerLevel level = this.tileEntity.getLevel().getServer().getLevel(data.getTravelLocation().getLevel());
            BlockPos blockpos = new BlockPos(
                    data.getTravelLocation().getPos().getX(),
                    level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, data.getTravelLocation().getPos()).getY(),
                    data.getTravelLocation().getPos().getZ()
            );
            BlockPos blockpos1 = this.findNearestBiome(level, (Biome)biome.get(), blockpos, 6400, 8);
            if (blockpos1 != null) {
                data.getTravelLocation().setPosition(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
                data.save();
            } else {
                throw new LuaException("Biome not found");
            }
        } else {
            throw new LuaException("Unknown biome");
        }
    }

    /**
     * Helper method to find a biome
     * @param level ServerLevel to search
     * @param biome Biome to find
     * @param pos   BlockPos to start from
     * @param i Idk what this is, likely a radius
     * @param j No idea about this either
     * @return BlockPos of the biome
     * @hidden
     */
    public BlockPos findNearestBiome(ServerLevel level, Biome biome, BlockPos pos, int i, int j) {
        Pair<BlockPos, Holder<Biome>> bb = level.getChunkSource()
                .getGenerator()
                .getBiomeSource()
                .findBiomeHorizontal(
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        i,
                        j,
                        b_val -> b_val.value() == biome,
                        level.random,
                        true,
                        level.getChunkSource().randomState().sampler()
                );
        return bb != null && bb.getFirst() != null ? (BlockPos)bb.getFirst() : null;
    }

    /**
     * Set the skin of the TARDIM
     * @param skin Skin name to change to
     * @hidden
     */
    @LuaFunction(mainThread = true)
    public final void setSkin(String skin) throws LuaException {
        if (this.tileEntity.getLevel().isClientSide()) {
            return;
        }

        TardimData data = getTardimData();

        ResourceLocation skinToApply = null;
        Iterator var13 = TardimRegistry.getRegistry().keySet().iterator();

        label39: {
            ResourceLocation builder;
            TardimRegistry.TardimBuilder b;
            do {
                if (!var13.hasNext()) {
                    break label39;
                }

                builder = (ResourceLocation)var13.next();
                b = TardimRegistry.getTardimBuilder(builder);
            } while(!b.getDisplayName().equalsIgnoreCase(skin) && !builder.toString().equalsIgnoreCase(skin));

            skinToApply = builder;
        }

        if (skinToApply == null) {
            throw new LuaException("Skin '" + skin + "' not found");
        }

        TardimData.Location loc = data.getCurrentLocation();
        ServerLevel level = this.tileEntity.getLevel().getServer().getLevel(loc.getLevel());
        data.setIdentifier(skinToApply);

        // FakePlayer...
        TardimRegistry.getTardimBuilder(skinToApply).changeTardimSkin(data, level, loc.getPos(), loc.getFacing(), new FakePlayer(this.tileEntity.getLevel(), this.tileEntity.getPos()));
    }

    /**
     * Get all available TARDIM skins. Useful for making a GUI skin selection.
     *
     * @return ObjectLuaTable of the available skins
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getSkins() throws LuaException {
        if (this.tileEntity.getLevel().isClientSide()) {
            return null;
        }

        Map<Integer, String> skins = new HashMap<>();

        Iterator var5 = TardimRegistry.getRegistry().keySet().iterator();
        int i = 0;
        while(var5.hasNext()) {
            ResourceLocation builder = (ResourceLocation)var5.next();
            TardimRegistry.TardimBuilder b = TardimRegistry.getTardimBuilder(builder);
            skins.put(i + 1, b.getDisplayName());
            i++;
        }

        return new ObjectLuaTable(skins);
    }

    /**
     * Play cloister bell sound.
     */
    @LuaFunction(mainThread = true)
    public final void cloisterBell() throws LuaException {
        if (this.tileEntity.getLevel().isClientSide()) {
            return;
        }
        try {
            Level lvl = this.tileEntity.getLevel();
            if (!lvl.isClientSide) {
                lvl.playSound(
                        null,
                        this.tileEntity.getPos(),
                        Registration.CLOISTER_SOUND_EVENT,
                        SoundSource.BLOCKS,
                        1.5f,
                        1f
                );
            }
        } catch (Exception var9) {
            throw new LuaException("There was an error trying to play the sound");
        }
    }

    /**
     * Get a table with all registered biomes' names.
     * Useful for creating advanced navigation systems.
     * @return ObjectLuaTable with all biomes' technical names
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getBiomes() throws LuaException {
        Map<Integer, String> biomes = new HashMap<>();
        Registry<Biome> biomeRegistry = tileEntity.getLevel().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
        Iterator<ResourceLocation> biome_it = biomeRegistry.keySet().iterator();
        int i = 0;
        while (biome_it.hasNext()) {
            biomes.put(i + 1, biome_it.next().toString());
            i++;
        }

        return new ObjectLuaTable(biomes);
    }

    /**
     * Get a table with all registered dimensions' names.
     * Useful for creating advanced navigation systems.
     * @return ObjectLuaTable with all dimensions' technical names
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getDimensions() throws LuaException {
        Iterator<ServerLevel> dim_it = this.tileEntity.getLevel().getServer().getAllLevels().iterator();  // TODO: Does this really work?
        Map<Integer, String> dimensions = new HashMap<>();
        int i = 0;
        while (dim_it.hasNext()) {
            dimensions.put(i + 1, dim_it.next().dimension().location().toString());
            i++;
        }
        return new ObjectLuaTable(dimensions);
    }
}
