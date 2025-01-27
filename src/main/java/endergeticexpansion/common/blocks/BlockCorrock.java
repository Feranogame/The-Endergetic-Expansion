package endergeticexpansion.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import endergeticexpansion.core.registry.EEBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.dimension.NetherDimension;
import net.minecraft.world.dimension.OverworldDimension;

public class BlockCorrock extends Block {
	
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);
	
	public BlockCorrock(Properties properties) {
		super(properties);
	}
	
	@Override
	public SoundType getSoundType(BlockState state, IWorldReader world, BlockPos pos, Entity entity) {
		return SoundType.CORAL;
	}
	
	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if(!this.isInProperDimension(worldIn)) {
			worldIn.setBlockState(pos, this.getCorrockBlockForDimension(worldIn.getDimension()));
		}
	}
	
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos)) {
			return Blocks.AIR.getDefaultState();
		} else {
			if (!this.isInProperDimension(worldIn.getWorld())) {
				worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 60 + worldIn.getRandom().nextInt(40));
			}
			return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isSolid();
	}
	
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if (!this.isInProperDimension(context.getWorld())) {
			context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 60 + context.getWorld().getRandom().nextInt(40));
		}
		
		return this.getDefaultState();
	}

	public boolean isInProperDimension(World world) {
		if(this.getDefaultState().getBlock() == EEBlocks.CORROCK_OVERWORLD) {
			return (world.getDimension() instanceof OverworldDimension);
		}
		else if(this.getDefaultState().getBlock() == EEBlocks.CORROCK_NETHER) {
			return (world.getDimension() instanceof NetherDimension);
		}
		else if(this.getDefaultState().getBlock() == EEBlocks.CORROCK_END) {
			return (world.getDimension() instanceof EndDimension);
		}
		return false;
	}
	
	public BlockState getCorrockBlockForDimension(Dimension dimension) {
		switch(dimension.getType().getId()) {
			case 0:
			return EEBlocks.CORROCK_OVERWORLD.getDefaultState();
			case 1:
			return EEBlocks.CORROCK_END.getDefaultState();
			case -1:
			return EEBlocks.CORROCK_NETHER.getDefaultState();
		}
		return null;
	}
	
	@Override
	public OffsetType getOffsetType() {
		return OffsetType.NONE;
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
