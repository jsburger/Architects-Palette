package architectspalette.common.blocks;

import architectspalette.core.integration.APCriterion;
import architectspalette.core.registry.APBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TotemBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public final TotemWingBlock WING_BLOCK;
    public final TotemFace totemType;

    public TotemBlock(Properties properties, TotemWingBlock wingBlock, TotemFace face) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
        this.WING_BLOCK = wingBlock;
        this.totemType = face;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            BlockPos blockpos = pos.offset(state.get(FACING).rotateY());
            boolean waterlogged = worldIn.getFluidState(blockpos).getFluid() == Fluids.WATER;
            if (worldIn.isAirBlock(blockpos) || waterlogged) {
                worldIn.setBlockState(blockpos, this.WING_BLOCK.getDefaultState()
                        .with(TotemWingBlock.FACING, state.get(FACING).rotateY())
                        .with(TotemWingBlock.WATERLOGGED, waterlogged), 3);
            }
            blockpos = pos.offset(state.get(FACING).rotateYCCW());
            waterlogged = worldIn.getFluidState(blockpos).getFluid() == Fluids.WATER;
            if (worldIn.isAirBlock(blockpos) || waterlogged) {
                worldIn.setBlockState(blockpos, this.WING_BLOCK.getDefaultState()
                        .with(TotemWingBlock.FACING, state.get(FACING).rotateYCCW())
                        .with(TotemWingBlock.WATERLOGGED, waterlogged), 3);
            }
            worldIn.updateBlock(pos, Blocks.AIR);
            state.updateNeighbours(worldIn, pos, 3);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack playerItem = player.getHeldItem(handIn);
        if (playerItem.getItem() instanceof AxeItem) {
            BlockState newState = this.totemType.getStrip().getDefaultState().with(FACING, state.get(FACING));
            worldIn.setBlockState(pos, newState, 3);
            playerItem.damageItem(1, player, (p) -> p.sendBreakAnimation(handIn));
            worldIn.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1, 1);
            if (player instanceof ServerPlayerEntity) {
                APCriterion.CARVE_TOTEM.trigger((ServerPlayerEntity) player);
            }
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        return ActionResultType.FAIL;
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
