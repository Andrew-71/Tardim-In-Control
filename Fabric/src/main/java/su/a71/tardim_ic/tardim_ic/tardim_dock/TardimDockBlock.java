package su.a71.tardim_ic.tardim_ic.tardim_dock;

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
                        Component.literal("Dock ID: " + ((TardimDockBlockEntity) be).dock_id).withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD), true
                );
            }
        }

        return InteractionResult.CONSUME;
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return true;
    }

    // Un-register the dock
    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        super.destroy(levelAccessor, blockPos, blockState);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return Registration.TARDIM_DOCK_BLOCKENTITY.create(blockPos, blockState);
    }
}
