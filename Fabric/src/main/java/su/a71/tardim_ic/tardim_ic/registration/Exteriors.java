package su.a71.tardim_ic.tardim_ic.registration;

import com.mojang.datafixers.types.Type;
import com.swdteam.tardim.common.block.BlockTardimDoors;
import com.swdteam.tardim.common.block.BlockTardimFloor;
import com.swdteam.tardim.common.block.BlockTardimRoof;
import com.swdteam.tardim.common.init.TardimRegistry;
import com.swdteam.tardim.tileentity.TileEntityTardim;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import su.a71.tardim_ic.tardim_ic.Constants;
import su.a71.tardim_ic.tardim_ic.soviet_chronobox.SovietChronoboxTileEntity;

public class Exteriors {
    // Soviet Chronobox
    public static TardimRegistry.TardimBuilder TARDIM_TYPE_SOVIET;
    public static Block DOOR_SOVIET_CHRONOBOX;
    public static Block ROOF_SOVIET_CHRONOBOX;
    public static Block FLOOR_SOVIET_CHRONOBOX;
    public static BlockEntityType<TileEntityTardim> TILE_SOVIET_CHRONOBOX;

    private static <T extends BlockEntity> BlockEntityType<T> createTardimTile(String string, FabricBlockEntityTypeBuilder<T> builder) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, string);
        return (BlockEntityType) Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Constants.MOD_ID, string), builder.build(type));
    }

    public static void register() {
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

    }
}
