package net.stehschnitzel.shutter.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GoldShutter extends Shutter {

	public GoldShutter(Settings settings) {
		super(settings, true);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.isSneaking()
				&& hand.equals(hand.MAIN_HAND)
				&& state.get(Shutter.POWERED)) {
			this.update(world, pos, state.get(OPEN) + 1, false);
			this.playSound(world, pos, world.getBlockState(pos).get(OPEN));
			return ActionResult.success(!world.isClient);
		}
		return ActionResult.FAIL;
	}

	@Override
	public void update(World pLevel, BlockPos pPos, int state, boolean first) {
		if (!pLevel.getBlockState(pPos).get(Shutter.POWERED)) {
			super.update(pLevel, pPos, state, first);
		}
	}

	@Override
	public void redstoneUpdate(World world, BlockPos neighborPos, BlockPos pos) {
		if (world.isReceivingRedstonePower(pos)) {
			world.setBlockState(pos,
					world.getBlockState(pos).with(Shutter.POWERED, true));
		} else if (!world.isReceivingRedstonePower(pos)
				&& world.getBlockState(pos).get(POWERED)) {
			world.setBlockState(pos, world.getBlockState(pos)
					.with(Shutter.POWERED, false));
		}
	}
}
