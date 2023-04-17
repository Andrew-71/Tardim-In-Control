package su.a71.tardim_ic.tardim_ic.tardim_dock;

import com.swdteam.tardim.common.init.TRDDimensions;
import com.swdteam.tardim.common.init.TRDSounds;
import com.swdteam.tardim.network.NetworkHandler;
import com.swdteam.tardim.network.PacketOpenEditGui;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import com.swdteam.tardim.tileentity.TileEntityBaseTardimPanel;
import com.swdteam.tardim.tileentity.TileEntityTardim;
import com.swdteam.tardim.tileentity.tardim.TardimType96TileEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.a71.tardim_ic.tardim_ic.Registration;
import su.a71.tardim_ic.tardim_ic.redstone_input.RedstoneInputTileEntity;
import su.a71.tardim_ic.tardim_ic.utils.FakePlayer;

import net.minecraft.world.level.block.RedstoneLampBlock;

public class TardimDockBlock extends Block implements EntityBlock {
    public TardimDockBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(2, 4).noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level w, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if (!w.isClientSide) {
            BlockEntity be = w.getBlockEntity(blockPos);
            if (be instanceof TardimDockBlockEntity) {

                player.displayClientMessage(
                        Component.literal("Dock name: '" + ((TardimDockBlockEntity) be).data.name + "'").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.BOLD), true
                );
            }
        }

        return InteractionResult.CONSUME;
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return true;
    }

    // Un-register the dock when breaking
    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        super.destroy(levelAccessor, blockPos, blockState);
        BlockEntity be = levelAccessor.getBlockEntity(blockPos);
        if (be instanceof TardimDockBlockEntity) {
            DockManager.removeDock(((TardimDockBlockEntity) be).data.name);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return Registration.TARDIM_DOCK_BLOCKENTITY.create(blockPos, blockState);
    }


    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos fromPos, boolean isMoving) {
        DebugPackets.sendNeighborsUpdatePacket(level, blockPos);

        BlockEntity be = level.getBlockEntity(blockPos);
        if (!(be instanceof TardimDockBlockEntity)) {
            return;
        }

        // get redstone signal
        if (level.hasNeighborSignal(blockPos)) {
            if (!((TardimDockBlockEntity) be).isPowered) {
                ((TardimDockBlockEntity) be).isPowered = true;
            }
        }
        else if (((TardimDockBlockEntity) be).isPowered) {
            ((TardimDockBlockEntity) be).isPowered = false;
        }
        ((TardimDockBlockEntity) be).updateActive();

        // Check stuff
        ((TardimDockBlockEntity) be).data.occupied = (level.getBlockEntity(blockPos.above()) instanceof TileEntityTardim);
        if ((level.getBlockEntity(blockPos.above()) instanceof TileEntityTardim)) {
            System.out.println("Oooo TARDIM docked!!!!");
        }
    }
}
