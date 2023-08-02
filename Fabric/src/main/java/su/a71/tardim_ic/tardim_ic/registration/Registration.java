package su.a71.tardim_ic.tardim_ic.registration;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import su.a71.tardim_ic.Constants;
import su.a71.tardim_ic.blocks.food_machine.FoodMachineBlock;
import su.a71.tardim_ic.blocks.food_machine.FoodMachineBlockEntity;
import su.a71.tardim_ic.blocks.redstone_input.RedstoneInputBlock;
import su.a71.tardim_ic.blocks.redstone_input.RedstoneInputBlockEntity;
import su.a71.tardim_ic.computercraft_compat.digital_interface.DigitalInterfaceBlock;
import su.a71.tardim_ic.computercraft_compat.digital_interface.DigitalInterfaceTileEntity;
import su.a71.tardim_ic.platform.Services;

import java.util.function.Supplier;

public class Registration {
    public static final Block REDSTONE_INPUT = new RedstoneInputBlock();
    public static final BlockEntityType<RedstoneInputBlockEntity> REDSTONE_INPUT_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "redstone_tardim_input"),
            FabricBlockEntityTypeBuilder.create(RedstoneInputBlockEntity::new, REDSTONE_INPUT).build()
    );

    public static final Block FOOD_MACHINE = new FoodMachineBlock();
    public static final BlockEntityType<FoodMachineBlockEntity> FOOD_MACHINE_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "food_machine"),
            FabricBlockEntityTypeBuilder.create(FoodMachineBlockEntity::new, FOOD_MACHINE).build()
    );


    public static void registerBlock(String name, Supplier<? extends Block> supplier, CreativeModeTab tab)
    {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Constants.MOD_ID, name), supplier.get());
        BlockItem blockItem = new BlockItem(supplier.get(), new FabricItemSettings());
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, name), blockItem);

//        Optional<ResourceKey<CreativeModeTab>> key = Optional.ofNullable(BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab));
//        key.ifPresent(itemGroupRegistryKey -> ItemGroupEvents.modifyEntriesEvent(itemGroupRegistryKey).register(content -> {
//            content.add(blockItem);
//        }));
    }

    public static void register() {
        registerBlock("redstone_tardim_input", () -> REDSTONE_INPUT, null);
        registerBlock("food_machine", () -> FOOD_MACHINE, null);

        Exteriors.register();
        if (FabricLoader.getInstance().isModLoaded("computercraft")) {
            ComputerCraftCompat.register();
        }
        if (FabricLoader.getInstance().isModLoaded("create")) {
            CreateCompat.register();
        }
    }
}
