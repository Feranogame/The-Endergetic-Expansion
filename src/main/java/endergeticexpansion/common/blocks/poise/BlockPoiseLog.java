package endergeticexpansion.common.blocks.poise;

import endergeticexpansion.core.registry.EEBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockPoiseLog extends RotatedPillarBlock {
	
	public BlockPoiseLog(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}
	
	@Override
	public boolean isFireSource(BlockState state, IBlockReader world, BlockPos pos, Direction side) {
		return false;
	}
	
	@Override
	public ToolType getHarvestTool(BlockState state) {
		return ToolType.AXE;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean onBlockActivated(BlockState state, World world, BlockPos p_220051_3_, PlayerEntity player, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
		if(state.getBlock() == EEBlocks.POISE_LOG && player.getHeldItemMainhand().getItem() instanceof AxeItem) {
			world.playSound(player, p_220051_3_, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 2.0F, 1.0F);
			world.setBlockState(p_220051_3_, EEBlocks.POISE_LOG_STRIPPED.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)));
			return true;
		}
		return super.onBlockActivated(state, world, p_220051_3_, player, p_220051_5_, p_220051_6_);
	}
	
}
