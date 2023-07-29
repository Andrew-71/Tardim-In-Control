package su.a71.tardim_ic.tardim_ic.registration;

import com.swdteam.tardim.common.init.CommandManager;
import dan200.computercraft.api.ComputerCraftAPI;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import su.a71.tardim_ic.tardim_ic.Constants;
import su.a71.tardim_ic.tardim_ic.Registration;
import su.a71.tardim_ic.command.CommandModemTransmit;
import su.a71.tardim_ic.computercraft_compat.TardimPeripheralProvider;
import su.a71.tardim_ic.computercraft_compat.blocks.digital_interface.DigitalInterfaceBlock;
import su.a71.tardim_ic.computercraft_compat.blocks.digital_interface.DigitalInterfaceTileEntity;

public class ComputerCraftCompat {
    public static final Block DIGITAL_TARDIM_INTERFACE = new DigitalInterfaceBlock();

    public static final BlockEntityType<DigitalInterfaceTileEntity> DIGITAL_TARDIM_INTERFACE_TILEENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new ResourceLocation("tardim_ic", "digital_tardim_interface"),
            FabricBlockEntityTypeBuilder.create(DigitalInterfaceTileEntity::new, DIGITAL_TARDIM_INTERFACE).build()
    );

    public static void register() {
        Constants.LOG.info("Loaded ComputerCraft compatibility!");

        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), DIGITAL_TARDIM_INTERFACE);
        Registry.register(Registry.ITEM, new ResourceLocation(Constants.MOD_ID, "digital_tardim_interface"), new BlockItem(DIGITAL_TARDIM_INTERFACE, new FabricItemSettings().tab(Registration.TARDIM_IC_TAB)));

        CommandManager.register(new CommandModemTransmit());
        ComputerCraftAPI.registerPeripheralProvider(new TardimPeripheralProvider());
    }
}
