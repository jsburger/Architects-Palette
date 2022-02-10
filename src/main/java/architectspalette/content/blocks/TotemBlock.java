package architectspalette.content.blocks;

import architectspalette.core.integration.APCriterion;
import architectspalette.core.registry.APBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class TotemBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public final TotemWingBlock WING_BLOCK;
    public final TotemFace totemType;

    public TotemBlock(Properties properties, TotemWingBlock wingBlock, TotemFace face) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
        this.WING_BLOCK = wingBlock;
        this.totemType = face;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isClientSide) {
            BlockPos blockpos = pos.relative(state.getValue(FACING).getClockWise());
            boolean waterlogged = worldIn.getFluidState(blockpos).getType() == Fluids.WATER;
            if (worldIn.isEmptyBlock(blockpos) || waterlogged) {
                worldIn.setBlock(blockpos, this.WING_BLOCK.defaultBlockState()
                        .setValue(TotemWingBlock.FACING, state.getValue(FACING).getClockWise())
                        .setValue(TotemWingBlock.WATERLOGGED, waterlogged), 3);
            }
            blockpos = pos.relative(state.getValue(FACING).getCounterClockWise());
            waterlogged = worldIn.getFluidState(blockpos).getType() == Fluids.WATER;
            if (worldIn.isEmptyBlock(blockpos) || waterlogged) {
                worldIn.setBlock(blockpos, this.WING_BLOCK.defaultBlockState()
                        .setValue(TotemWingBlock.FACING, state.getValue(FACING).getCounterClockWise())
                        .setValue(TotemWingBlock.WATERLOGGED, waterlogged), 3);
            }
            worldIn.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(worldIn, pos, 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack playerItem = player.getItemInHand(handIn);
        if (playerItem.getItem() instanceof AxeItem) {
            BlockState newState = this.totemType.getStrip().defaultBlockState().setValue(FACING, state.getValue(FACING));
            worldIn.setBlock(pos, newState, 3);
            playerItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(handIn));
            worldIn.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, 1);
            if (player instanceof ServerPlayer) {
                APCriterion.CARVE_TOTEM.trigger((ServerPlayer) player);
            }
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return InteractionResult.FAIL;
    }

    public enum TotemFace {
        GRINNING,
        PLACID,
        SHOCKED,
        BLANK;

        public Block getStrip() {
            switch(this){
                case GRINNING: return APBlocks.PLACID_ACACIA_TOTEM.get();
                case PLACID: return APBlocks.SHOCKED_ACACIA_TOTEM.get();
                case SHOCKED: return APBlocks.BLANK_ACACIA_TOTEM.get();
                default: return APBlocks.GRINNING_ACACIA_TOTEM.get();
            }
        }
    }

}
