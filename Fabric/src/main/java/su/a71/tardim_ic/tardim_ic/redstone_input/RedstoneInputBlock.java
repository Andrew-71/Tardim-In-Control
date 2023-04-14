package su.a71.tardim_ic.tardim_ic.redstone_input;

import com.swdteam.tardim.common.block.BlockBaseTardimPanel;
import com.swdteam.tardim.common.init.TRDDimensions;
import com.swdteam.tardim.common.init.TRDSounds;
import com.swdteam.tardim.network.NetworkHandler;
import com.swdteam.tardim.network.PacketOpenEditGui;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import com.swdteam.tardim.tileentity.TileEntityBaseTardimPanel;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.NotNull;
import su.a71.tardim_ic.tardim_ic.Registration;

import javax.annotation.Nullable;

public class RedstoneInputBlock extends BlockBaseTardimPanel implements EntityBlock {
    private boolean isPowered = false;
    private Player lastPlayer = null;
    public RedstoneInputBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(2, 4));  // No occlusion?
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return Registration.REDSTONE_TARDIM_INPUT_TILEENTITY.create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level w, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if (!w.isClientSide) {

            w.playSound(null, blockPos, TRDSounds.TARDIM_BEEP, SoundSource.BLOCKS, 0.3F, 0.5F);

            BlockEntity be = w.getBlockEntity(blockPos);
            if (be instanceof TileEntityBaseTardimPanel && w.dimension() == TRDDimensions.TARDIS) {
                TardimData data = TardimManager.getFromPos(blockPos);
                if (data != null && data.hasPermission(player)) {
                    this.lastPlayer = player;
                    NetworkHandler.sendTo((ServerPlayer)player, new PacketOpenEditGui(blockPos, 1));
                    return InteractionResult.CONSUME;
                }

                player.displayClientMessage(
                        Component.literal("You do not have permission").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD), true
                );
            }
        }

        return InteractionResult.CONSUME;
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return true;
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos fromPos, boolean isMoving) {
        DebugPackets.sendNeighborsUpdatePacket(level, blockPos);

        // get redstone signal
        Direction direction = blockState.getValue(FACING);
        int redstoneSignal = level.getSignal(blockPos, direction);
        if (redstoneSignal > 0 && !isPowered) {
            isPowered = true;
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof TileEntityBaseTardimPanel && level.dimension() == TRDDimensions.TARDIS) {
                TardimData data = TardimManager.getFromPos(blockPos);
                if (data != null && !level.isClientSide && this.lastPlayer != null) {
                    if (((TileEntityBaseTardimPanel)be).hasCommand()) {
                        ((TileEntityBaseTardimPanel)be).execute(this.lastPlayer);
                    }
                }
            }

        } else if (redstoneSignal == 0 && isPowered)
            isPowered = false;
    }
}
