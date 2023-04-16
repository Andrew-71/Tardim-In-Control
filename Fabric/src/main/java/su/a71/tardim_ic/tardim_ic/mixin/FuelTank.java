package su.a71.tardim_ic.tardim_ic.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.swdteam.tardim.tileentity.TileEntityFuelStorage;

//@Mixin(TileEntityFuelStorage.class)
//public class FuelTank extends BlockEntity, ComparatorBlockEntity {
//
//    public ExampleMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
//        super(blockEntityType, blockPos, blockState);
//    }
//
//    @Inject(at = @At("HEAD"), method = "init()V")
//    private void init(CallbackInfo info) {
//
//        Constants.LOG.info("This line is printed by an example mod mixin from Fabric!");
//        Constants.LOG.info("MC Version: {}", Minecraft.getInstance().getVersionType());
//        Constants.LOG.info("Classloader: {}", this.getClass().getClassLoader());
//    }
//}
//public class FuelTank {
//}
