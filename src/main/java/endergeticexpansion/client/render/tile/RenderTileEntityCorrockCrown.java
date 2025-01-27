package endergeticexpansion.client.render.tile;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import endergeticexpansion.client.model.ModelCorrockCrownStanding;
import endergeticexpansion.client.model.ModelCorrockCrownWall;
import endergeticexpansion.common.blocks.BlockCorrockCrownStanding;
import endergeticexpansion.common.blocks.BlockCorrockCrownWall;
import endergeticexpansion.common.tileentities.TileEntityCorrockCrown;
import endergeticexpansion.core.EndergeticExpansion;
import endergeticexpansion.core.registry.EEBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;


public class RenderTileEntityCorrockCrown extends TileEntityRenderer<TileEntityCorrockCrown> {
	public ModelCorrockCrownStanding standingModel;
	public ModelCorrockCrownWall wallModel;	
	private static final ResourceLocation[] TEXTURES = {
		new ResourceLocation(EndergeticExpansion.MOD_ID + ":textures/tile/corrock_crown_end.png"),
		new ResourceLocation(EndergeticExpansion.MOD_ID + ":textures/tile/corrock_crown_nether.png"),
		new ResourceLocation(EndergeticExpansion.MOD_ID + ":textures/tile/corrock_crown_overworld.png")
	};
	
	public RenderTileEntityCorrockCrown() {
		this.standingModel = new ModelCorrockCrownStanding();
		this.wallModel = new ModelCorrockCrownWall();
	}
	
	@Override
	public void render(TileEntityCorrockCrown tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
		BlockState BlockState = tileEntityIn.getBlockState();
		GlStateManager.pushMatrix();
		
		if (BlockState.getBlock() instanceof BlockCorrockCrownStanding) {
			GlStateManager.translatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			GlStateManager.rotatef(-((float)(BlockState.get(BlockCorrockCrownStanding.ROTATION) * 360) / 16.0F), 0.0F, 1.0F, 0.0F);
			if(BlockState.get(BlockCorrockCrownStanding.UPSIDE_DOWN)) {
				GlStateManager.rotatef((-90 * 360) / 16.0F, 0.0F, 0.0F, 0.0F);
				GlStateManager.translatef(0, 3F, 0);
			}
		} else {
			GlStateManager.translatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			
			if(BlockState.get(BlockCorrockCrownWall.FACING) == Direction.NORTH || BlockState.get(BlockCorrockCrownWall.FACING) == Direction.SOUTH) {
				GlStateManager.rotatef(BlockState.get(BlockCorrockCrownWall.FACING).getOpposite().getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
			} else {
				GlStateManager.rotatef(BlockState.get(BlockCorrockCrownWall.FACING).getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
			}
			GlStateManager.translatef(0.0F, -0.2F, 0.05F);
		}
		
		this.bindTexture(TEXTURES[this.getTexture(tileEntityIn)]);
		
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		if(BlockState.getBlock() instanceof BlockCorrockCrownStanding) {
			if(BlockState.get(BlockCorrockCrownStanding.UPSIDE_DOWN)) {
				GlStateManager.scalef(1.5F, -1.5F, -1.5F);
			} else {
				GlStateManager.scalef(1.0F, -1.0F, -1.0F);
			}
		} else {
			GlStateManager.scalef(1.0F, -1.0F, -1.0F);
		}
		
		GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
		if(BlockState.getBlock() instanceof BlockCorrockCrownStanding) {
			standingModel.renderAll();
			GlStateManager.disableLighting();
		} else {
			wallModel.renderAll();
			GlStateManager.disableLighting();
		}
		
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
	
	public int getTexture(TileEntityCorrockCrown te) {
		BlockState BlockState = te.getBlockState();
		if(BlockState.getBlock() == EEBlocks.CORROCK_CROWN_END_STANDING | BlockState.getBlock() == EEBlocks.CORROCK_CROWN_END_WALL) {
			return 0;
		} else if(BlockState.getBlock() == EEBlocks.CORROCK_CROWN_NETHER_STANDING | BlockState.getBlock() == EEBlocks.CORROCK_CROWN_NETHER_WALL) {
			return 1;
		} else {
			return 2;
		}
	}
}
