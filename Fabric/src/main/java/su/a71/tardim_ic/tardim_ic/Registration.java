package su.a71.tardim_ic.tardim_ic;

import com.swdteam.tardim.common.block.BlockTardimDoors;
import com.swdteam.tardim.common.block.BlockTardimFloor;
import com.swdteam.tardim.common.block.BlockTardimRoof;
import com.swdteam.tardim.common.init.TardimRegistry;
import com.swdteam.tardim.tileentity.TileEntityTardim;

import dan200.computercraft.api.ComputerCraftAPI;

import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import su.a71.tardim_ic.tardim_ic.digital_interface.DigitalInterfaceBlock;
import su.a71.tardim_ic.tardim_ic.digital_interface.DigitalInterfacePeripheralProvider;
import su.a71.tardim_ic.tardim_ic.digital_interface.DigitalInterfaceTileEntity;
import su.a71.tardim_ic.tardim_ic.redstone_input.RedstoneInputBlock;
import su.a71.tardim_ic.tardim_ic.redstone_input.RedstoneInputTileEntity;
import su.a71.tardim_ic.tardim_ic.Constants;
import su.a71.tardim_ic.tardim_ic.registration.CommandInit;
import su.a71.tardim_ic.tardim_ic.soviet_chronobox.SovietChronoboxTileEntity;

public class Registration {
    // Blocks
    public static Block DOOR_SOVIET_CHRONOBOX;
    public static Block ROOF_SOVIET_CHRONOBOX;
    public static Block FLOOR_SOVIET_CHRONOBOX;
    public static BlockEntityType<TileEntityTardim> TILE_SOVIET_CHRONOBOX;

    public static final Block DIGITAL_TARDIM_INTERFACE = new DigitalInterfaceBlock();
    public static final Block REDSTONE_TARDIM_INPUT = new RedstoneInputBlock();

    // Tile Entities
    public static final BlockEntityType<RedstoneInputTileEntity> REDSTONE_TARDIM_INPUT_TILEENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "redstone_tardim_input"),
            FabricBlockEntityTypeBuilder.create(RedstoneInputTileEntity::new, REDSTONE_TARDIM_INPUT).build()
    );

    public static final BlockEntityType<DigitalInterfaceTileEntity> DIGITAL_TARDIM_INTERFACE_TILEENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "digital_tardim_interface"),
            FabricBlockEntityTypeBuilder.create(DigitalInterfaceTileEntity::new, DIGITAL_TARDIM_INTERFACE).build()
    );

    private static final CreativeModeTab TARDIM_IC_TAB = FabricItemGroupBuilder
            .create(new ResourceLocation("tardim_ic"))
            .icon(() -> new ItemStack(DIGITAL_TARDIM_INTERFACE))
            .build();

    // Cloister bell
    public static final ResourceLocation CLOISTER_SOUND = new ResourceLocation("tardim_ic:cloister");
    public static SoundEvent CLOISTER_SOUND_EVENT = new SoundEvent(CLOISTER_SOUND);

    public static TardimRegistry.TardimBuilder TARDIM_TYPE_SOVIET;

    private static <T extends BlockEntity> BlockEntityType<T> createTardimTile(String string, FabricBlockEntityTypeBuilder<T> builder) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, string);
        return (BlockEntityType)Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Constants.MOD_ID, string), builder.build(type));
    }

    // Register our stuff
    public static void register() {
        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), REDSTONE_TARDIM_INPUT);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "redstone_tardim_input"), new BlockItem(REDSTONE_TARDIM_INPUT, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), DIGITAL_TARDIM_INTERFACE);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), new BlockItem(DIGITAL_TARDIM_INTERFACE, new FabricItemSettings().tab(TARDIM_IC_TAB)));

        Registry.register(Registry.SOUND_EVENT, CLOISTER_SOUND, CLOISTER_SOUND_EVENT);

        FLOOR_SOVIET_CHRONOBOX = Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "tardim_floor_soviet"), new BlockTardimFloor(FabricBlockSettings.of(Material.WOOD).sounds(SoundType.WOOD).strength(-1.0F, 3600000.0F).noLootTable().noOcclusion(), new BlockTardimFloor.TardimTileData() {
            public BlockEntityType<TileEntityTardim> getType() {
                return TILE_SOVIET_CHRONOBOX;
            }
            public BlockEntityTicker<? super TileEntityTardim> getTicker() {
                return TileEntityTardim::serverTick;
            }
            public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
                return new SovietChronoboxTileEntity(var1, var2);
            }
        }));
        TILE_SOVIET_CHRONOBOX = createTardimTile("tardim_soviet_chronobox", FabricBlockEntityTypeBuilder.create(SovietChronoboxTileEntity::new, new Block[]{FLOOR_SOVIET_CHRONOBOX}));
        DOOR_SOVIET_CHRONOBOX = Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "tardim_door_soviet"), new BlockTardimDoors(FabricBlockSettings.of(Material.WOOD).sounds(SoundType.WOOD).strength(-1.0F, 3600000.0F).noLootTable().noOcclusion()));
        ROOF_SOVIET_CHRONOBOX = Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "tardim_roof_soviet"), new BlockTardimRoof(FabricBlockSettings.of(Material.WOOD).sounds(SoundType.WOOD).strength(-1.0F, 3600000.0F).noLootTable().noOcclusion()));
        TARDIM_TYPE_SOVIET = new TardimRegistry.TardimBuilder(new ResourceLocation(Constants.MOD_ID, "tardim_soviet_chronobox"), "TARDIM Soviet Chronobox", ROOF_SOVIET_CHRONOBOX, DOOR_SOVIET_CHRONOBOX, FLOOR_SOVIET_CHRONOBOX);

        ComputerCraftAPI.registerPeripheralProvider(new DigitalInterfacePeripheralProvider());
        CommandInit.init();
    }
}