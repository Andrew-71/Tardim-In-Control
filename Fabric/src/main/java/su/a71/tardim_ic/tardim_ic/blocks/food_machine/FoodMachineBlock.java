package su.a71.tardim_ic.tardim_ic.blocks.food_machine;

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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import su.a71.tardim_ic.tardim_ic.Registration;
import su.a71.tardim_ic.tardim_ic.blocks.redstone_input.RedstoneInputTileEntity;
import su.a71.tardim_ic.tardim_ic.utils.FakePlayer;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class FoodMachineBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public FoodMachineBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(2, 4).noOcclusion());  // No occlusion?
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        //this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        return this.defaultBlockState().setValue(FACING, $$0.getHorizontalDirection().getOpposite());
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        $$0.add(FACING);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return Registration.FOOD_MACHINE_TILEENTITY.create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level w, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if (!w.isClientSide) {
            w.playSound(null, blockPos, TRDSounds.TARDIM_BEEP, SoundSource.BLOCKS, 0.3F, 0.5F);
            BlockEntity be = w.getBlockEntity(blockPos);
            if (be instanceof FoodMachineTileEntity && w.dimension() == TRDDimensions.TARDIS) {
                TardimData data = TardimManager.getFromPos(blockPos);
                if (data != null && data.hasPermission(player)) {
                    if (data.getFuel() >= 0.05) {
                        data.setFuel(data.getFuel() - 0.05);  // Remove some fuel in exchange for food
                        ItemEntity food = new ItemEntity(EntityType.ITEM, w);

                        // Select type of food here
                        food.setItem(new ItemStack(Items.BREAD, 1));


                        food.setPos(Vec3.atCenterOf(blockPos).add(new Vec3(0, 0.2, 0)));
                        w.addFreshEntity(food);
                    } else {
                        player.displayClientMessage(
                                Component.literal("You do not have enough fuel").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD), true
                        );
                    }
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
}
