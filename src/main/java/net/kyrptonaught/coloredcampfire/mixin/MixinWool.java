package net.kyrptonaught.coloredcampfire.mixin;

import net.kyrptonaught.coloredcampfire.ColoredCampfireMod;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CampfireBlock.class)
public class MixinWool extends Block {
    protected MixinWool(Settings settings) {
        super(settings);
    }

    @Inject(method = "Lnet/minecraft/block/CampfireBlock;spawnSmokeParticle(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;ZZ)V", at = @At(value = "HEAD"), cancellable = true)
    private static void spawnColoredParticle(World world, BlockPos pos, boolean isSignal, boolean lotsOfSmoke, CallbackInfo cbi) {
        if (world.isReceivingRedstonePower(pos)) {
            Random random = world.getRandom();
            world.addImportantParticle(new BlockStateParticleEffect(ColoredCampfireMod.COLOREDSMOKE, world.getBlockState(pos.down())), true, (double) pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble(), (double) pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            cbi.cancel();
        }
    }
/*
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if (world.isReceivingRedstonePower(pos))
            world.setBlockState(pos, state.with(CampfireBlock.SIGNAL_FIRE, true), 2);
        else if (state.get(CampfireBlock.SIGNAL_FIRE)) {
            BlockState newState = getStateForNeighborUpdate(state, Direction.DOWN, world.getBlockState(pos.down()), world, pos, pos.down());
            if (!newState.get(CampfireBlock.SIGNAL_FIRE))
                world.setBlockState(pos, newState);
        }
    }

    @Inject(method = "getPlacementState", at = @At(value = "RETURN"), cancellable = true)
    private void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> returnable) {
        BlockState state = returnable.getReturnValue();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        if (world.isReceivingRedstonePower(pos))
            returnable.setReturnValue(state.with(CampfireBlock.SIGNAL_FIRE, true));
    }

    @Inject(method = "doesBlockCauseSignalFire", at = @At(value = "RETURN"), cancellable = true)
    private void doesBlockCauseSignalFire(BlockState state, CallbackInfoReturnable<Boolean> returnable) {
        returnable.setReturnValue(returnable.getReturnValue() || state.emitsRedstonePower());
    }
 */
}