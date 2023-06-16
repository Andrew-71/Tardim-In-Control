package su.a71.tardim_ic.tardim_ic;

import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import su.a71.tardim_ic.tardim_ic.blocks.food_machine.FoodMachineBlock;
import su.a71.tardim_ic.tardim_ic.blocks.food_machine.FoodMachineTileEntity;
import su.a71.tardim_ic.tardim_ic.jammer.LocationJammerMaterial;
import su.a71.tardim_ic.tardim_ic.blocks.redstone_input.RedstoneInputBlock;
import su.a71.tardim_ic.tardim_ic.blocks.redstone_input.RedstoneInputTileEntity;
import su.a71.tardim_ic.tardim_ic.registration.CommandInit;
import su.a71.tardim_ic.tardim_ic.registration.ComputerCraftCompat;
import su.a71.tardim_ic.tardim_ic.registration.CreateCompat;
import su.a71.tardim_ic.tardim_ic.registration.Exteriors;

public class Registration {
    // Blocks
    public static final Block REDSTONE_TARDIM_INPUT = new RedstoneInputBlock();
    public static final Block FOOD_MACHINE = new FoodMachineBlock();

    // Tile Entities
    public static final BlockEntityType<RedstoneInputTileEntity> REDSTONE_TARDIM_INPUT_TILEENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "redstone_tardim_input"),
            FabricBlockEntityTypeBuilder.create(RedstoneInputTileEntity::new, REDSTONE_TARDIM_INPUT).build()
    );

    public static final BlockEntityType<FoodMachineTileEntity> FOOD_MACHINE_TILEENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "food_machine"),
            FabricBlockEntityTypeBuilder.create(FoodMachineTileEntity::new, FOOD_MACHINE).build()
    );

    public static final CreativeModeTab TARDIM_IC_TAB = FabricItemGroupBuilder
            .create(new ResourceLocation("tardim_ic"))
            .icon(() -> new ItemStack(REDSTONE_TARDIM_INPUT))
            .build();

    // Cloister bell
    public static final ResourceLocation CLOISTER_SOUND = new ResourceLocation("tardim_ic:cloister");
    public static SoundEvent CLOISTER_SOUND_EVENT = new SoundEvent(CLOISTER_SOUND);

    public static final ArmorMaterial LOCATION_JAMMER_MATERIAL = new LocationJammerMaterial();
    public static final Item LOCATION_JAMMER = new ArmorItem(LOCATION_JAMMER_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(TARDIM_IC_TAB));

    // Register our stuff
    public static void register() {
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "location_jammer"), LOCATION_JAMMER);

        if (FabricLoader.getInstance().isModLoaded("computercraft")) {
            ComputerCraftCompat.register();  // Register ComputerCraft-related features
        }
        if (FabricLoader.getInstance().isModLoaded("create")) {
            CreateCompat.register();  // Register Create-related features
        }
        Exteriors.register();  // Register custom TARDIM exteriors

        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), REDSTONE_TARDIM_INPUT);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), new BlockItem(REDSTONE_TARDIM_INPUT, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "food_machine"), FOOD_MACHINE);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "food_machine"), new BlockItem(FOOD_MACHINE, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.SOUND_EVENT, CLOISTER_SOUND, CLOISTER_SOUND_EVENT);

        CommandInit.init();
    }
}