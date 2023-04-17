package su.a71.tardim_ic.tardim_ic;

import com.swdteam.tardim.common.command.*;
import com.swdteam.tardim.common.init.TRDDimensions;
import com.swdteam.tardim.main.Config;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimIDMap;
import com.swdteam.tardim.tardim.TardimManager;
import com.swdteam.tardim.tardim.TardimSaveHandler;
import com.swdteam.tardim.util.world.SchematicUtils;
import dan200.computercraft.api.ComputerCraftAPI;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;


import su.a71.tardim_ic.tardim_ic.digital_interface.DigitalInterfaceBlock;
import su.a71.tardim_ic.tardim_ic.digital_interface.DigitalInterfacePeripheralProvider;
import su.a71.tardim_ic.tardim_ic.digital_interface.DigitalInterfaceTileEntity;

import su.a71.tardim_ic.tardim_ic.redstone_input.RedstoneInputBlock;
import su.a71.tardim_ic.tardim_ic.redstone_input.RedstoneInputTileEntity;

import su.a71.tardim_ic.tardim_ic.Constants;
import su.a71.tardim_ic.tardim_ic.registration.CommandInit;
import su.a71.tardim_ic.tardim_ic.tardim_dock.DockManager;
import su.a71.tardim_ic.tardim_ic.tardim_dock.TardimDockBlock;
import su.a71.tardim_ic.tardim_ic.tardim_dock.TardimDockBlockEntity;

import com.swdteam.tardim.tileentity.TileEntityFuelStorage;
import com.swdteam.tardim.common.block.BlockFuelStorage;

import java.util.Iterator;
import java.util.Map;


public class Registration {
    // Blocks

    public static final Block DIGITAL_TARDIM_INTERFACE = new DigitalInterfaceBlock();
    public static final Block REDSTONE_TARDIM_INPUT = new RedstoneInputBlock();
    public static final Block TARDIM_DOCK = new TardimDockBlock();

    // Tile Entities
    public static final BlockEntityType<RedstoneInputTileEntity> REDSTONE_TARDIM_INPUT_TILEENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "redstone_tardim_input"),
            FabricBlockEntityTypeBuilder.create(RedstoneInputTileEntity::new, REDSTONE_TARDIM_INPUT).build()
    );

    public static final BlockEntityType<RedstoneInputTileEntity> DIGITAL_TARDIM_INTERFACE_TILEENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "digital_tardim_interface"),
            FabricBlockEntityTypeBuilder.create(RedstoneInputTileEntity::new, DIGITAL_TARDIM_INTERFACE).build()
    );

    public static final BlockEntityType<TardimDockBlockEntity> TARDIM_DOCK_BLOCKENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "tardim_dock"),
            FabricBlockEntityTypeBuilder.create(TardimDockBlockEntity::new, TARDIM_DOCK).build()
    );

    private static final CreativeModeTab TARDIM_IC_TAB = FabricItemGroupBuilder
            .create(new ResourceLocation("tardim_ic"))
            .icon(() -> new ItemStack(DIGITAL_TARDIM_INTERFACE))
            .build();

    // Cloister bell
    public static final ResourceLocation CLOISTER_SOUND = new ResourceLocation("tardim_ic:cloister");
    public static SoundEvent CLOISTER_SOUND_EVENT = new SoundEvent(CLOISTER_SOUND);


    // Register our stuff
    public static void register() {
        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), REDSTONE_TARDIM_INPUT);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), new BlockItem(REDSTONE_TARDIM_INPUT, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), DIGITAL_TARDIM_INTERFACE);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), new BlockItem(DIGITAL_TARDIM_INTERFACE, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "tardim_dock"), TARDIM_DOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "tardim_dock"), new BlockItem(TARDIM_DOCK, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.SOUND_EVENT, CLOISTER_SOUND, CLOISTER_SOUND_EVENT);

        ComputerCraftAPI.registerPeripheralProvider(new DigitalInterfacePeripheralProvider());
        CommandInit.init();

        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            DockManager.server = server;
            DockManager.clearCahce();

            try {
                DockManager.load();
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        });
        ServerWorldEvents.UNLOAD.register((server, world) -> {
            try {
                if (DockManager.server == null) {
                    return;
                }
                DockManager.save();
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        });
    }
}