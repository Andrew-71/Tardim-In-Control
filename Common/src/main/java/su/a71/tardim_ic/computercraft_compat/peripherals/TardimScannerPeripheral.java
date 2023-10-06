package su.a71.tardim_ic.computercraft_compat.peripherals;

import com.swdteam.tardim.common.block.BlockTardimScanner;
import com.swdteam.tardim.common.init.TardimRegistry;
import com.swdteam.tardim.tardim.TardimData;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.ObjectLuaTable;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.biome.Biome;
import su.a71.tardim_ic.computercraft_compat.entity.FakeTardimPeripheralTileEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/*
 * CC Peripheral for TARDIM's scanner block.
 * Only provides getters for data-related (mostly table output) methods e.g. biome or companion list
 * for people who cannot afford or don't need the digital interface.
 */
public class TardimScannerPeripheral extends TardimPeripheral<BlockTardimScanner> implements IPeripheral {
    /**
     * @param tileEntity the tile entity of this peripheral
     * @hidden
     */
    public TardimScannerPeripheral(FakeTardimPeripheralTileEntity tileEntity) {
        super(tileEntity, "tardim_scanner", (BlockTardimScanner) tileEntity.getBlock());
    }


    // Peripheral methods ===============================================================

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
     * Get online players. Useful for making a GUI for the locate function or just a nice dashboard.
     *
     * @return ObjectLuaTable of the online players
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getOnlinePlayers() throws LuaException {
        if (this.tileEntity.getLevel().isClientSide()) {
            return null;
        }

        PlayerList playerList = this.tileEntity.getLevel().getServer().getPlayerList();
        Map<Integer, String> players = new HashMap<>();
        for (int i = 0; i < playerList.getPlayers().size(); i++) {
            players.put(i + 1, playerList.getPlayers().get(i).getGameProfile().getName());
        }

        return new ObjectLuaTable(players);
    }

    /**
     * Get all available TARDIM skins. Useful for making a GUI skin selection.
     *
     * @return ObjectLuaTable of the available skins
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getSkins() throws LuaException {
        if (this.getTileEntity().getLevel().isClientSide()) {
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
     * Get a table with all registered biomes' names.
     * Useful for creating advanced navigation systems.
     * @return ObjectLuaTable with all biomes' technical names
     */
    @LuaFunction(mainThread = true)
    public final ObjectLuaTable getBiomes() throws LuaException {
        Map<Integer, String> biomes = new HashMap<>();
        Registry<Biome> biomeRegistry = tileEntity.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
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
        Iterator<ServerLevel> dim_it = this.getTileEntity().getLevel().getServer().getAllLevels().iterator();  // TODO: Does this really work?
        Map<Integer, String> dimensions = new HashMap<>();
        int i = 0;
        while (dim_it.hasNext()) {
            dimensions.put(i + 1, dim_it.next().dimension().location().toString());
            i++;
        }
        return new ObjectLuaTable(dimensions);
    }
}