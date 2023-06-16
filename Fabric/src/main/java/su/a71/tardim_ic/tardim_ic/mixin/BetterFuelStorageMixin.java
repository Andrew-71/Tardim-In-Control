package su.a71.tardim_ic.tardim_ic.mixin;

import com.swdteam.tardim.common.block.BlockFuelStorage;
import com.swdteam.tardim.common.init.TRDDimensions;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import com.swdteam.tardim.tileentity.TileEntityFuelStorage;
import net.fabricmc.loader.impl.util.log.Log;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static su.a71.tardim_ic.tardim_ic.Constants.LOG;

@Mixin(value = TileEntityFuelStorage.class, remap = false)
public class BetterFuelStorageMixin {

    // This is rather inefficient as we iterate 2 times
    // However, the hoppers are so small and this method is called so rarely that it should be fine.
    @Inject(method = "serverTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/swdteam/tardim/tileentity/TileEntityFuelStorage;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;removeItem(II)Lnet/minecraft/world/item/ItemStack;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private static void saveLavaBuckets(Level world, BlockPos pos, BlockState state, TileEntityFuelStorage blockEntity, CallbackInfo ci) {
        //CAPTURE_FAILHARD: If the calculated locals are different from the expected values, throws an error.
        HopperBlockEntity mixin_hopper = (HopperBlockEntity)world.getBlockEntity(blockEntity.getBlockPos().above());
        for(int j = 0; j < mixin_hopper.getContainerSize(); ++j) {
            ItemStack stack = mixin_hopper.getItem(j);
            double fuel = TardimManager.getFuel(stack.getItem());
            if (fuel > 0.0) {
                if (stack.getItem() instanceof BucketItem) {
                    LOG.info("THIS IS A BUCKET");
                    mixin_hopper.setItem(j, new ItemStack(stack.getItem().getCraftingRemainingItem(), 2));
                } else {
                    mixin_hopper.removeItem(j, 1);
                }
            }
        }
    }
}