package architectspalette.common.tileentity;

import architectspalette.common.blocks.ChiseledAbyssalineBlock;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ChiseledAbyssalineTileEntity extends TileEntity implements ITickableTileEntity {
	private boolean decrementing;
	private int frame;
	private int pausedTicks;
	
	public ChiseledAbyssalineTileEntity() {
		super(APTileEntities.CHISELED_ABYSSALINE.get());
	}

	@Override
	public void tick() {
		BlockState state = this.getBlockState();
		World world = this.getWorld();
		if(!world.isRemote && state != null && state.getBlock() == APBlocks.CHISELED_ABYSSALINE_BRICKS.get()) {
			if(state.get(ChiseledAbyssalineBlock.CHARGED)) {
				if(this.pausedTicks > 0) {
					this.pausedTicks--;
				} else {
					if(this.decrementing && this.frame > 0) {
						this.frame--;
						
						if(this.frame == 0) {
							this.pausedTicks = 10;
							this.decrementing = false;
						}
					} else if(!this.decrementing && this.frame < 20) {
						this.frame++;
						
						if(this.frame == 20) {
							this.pausedTicks = 10;
							this.decrementing = true;
						}
					}
				}
				world.setBlockState(this.pos, state.with(ChiseledAbyssalineBlock.LIGHT, this.frame), 2);
			} else {
				world.setBlockState(this.pos, state.with(ChiseledAbyssalineBlock.LIGHT, 0), 2);
				this.decrementing = false;
				this.frame = 0;
				this.pausedTicks = 0;
			}
		}
	}
}