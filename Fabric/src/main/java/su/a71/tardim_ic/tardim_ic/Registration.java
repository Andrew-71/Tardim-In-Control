package su.a71.tardim_ic.tardim_ic;

import dan200.computercraft.api.ComputerCraftAPI;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.resources.ResourceLocation;
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

public class Registration {
    // Blocks

    public static final Block DIGITAL_TARDIM_INTERFACE = new DigitalInterfaceBlock();
    public static final Block REDSTONE_TARDIM_INPUT = new RedstoneInputBlock();

    // Tile Entities
    //public static final RegistryObject<BlockEntityType<DigitalInterfaceTileEntity>> DIGITAL_TARDIM_INTERFACE_TILEENTITY = Registration.BLOCK_ENTITIES.register("digital_tardim_interface", () -> new BlockEntityType<>(DigitalInterfaceTileEntity::new, Sets.newHashSet(DIGITAL_TARDIM_INTERFACE.get()), null));
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

    private static final CreativeModeTab TARDIM_IC_TAB = FabricItemGroupBuilder
            .create(new ResourceLocation("tardim_ic"))
            .icon(() -> new ItemStack(DIGITAL_TARDIM_INTERFACE))
            .build();


    // Register our stuff
    public static void register() {
        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), REDSTONE_TARDIM_INPUT);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), new BlockItem(REDSTONE_TARDIM_INPUT, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), DIGITAL_TARDIM_INTERFACE);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), new BlockItem(DIGITAL_TARDIM_INTERFACE, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        ComputerCraftAPI.registerPeripheralProvider(new DigitalInterfacePeripheralProvider());
    }
}