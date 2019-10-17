package endergeticexpansion.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockStairsBase extends StairsBlock {

	@SuppressWarnings("deprecation") //No, :gru_no_face:
	public BlockStairsBase(BlockState state, Properties properties) {
		super(state, properties);
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return state.getBlock().isIn(BlockTags.LOGS) ? 60 : 0;
	}
	
}
