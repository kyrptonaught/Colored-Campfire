package net.kyrptonaught.coloredcampfire.mixin;

import net.kyrptonaught.coloredcampfire.ColoredCampfireMod;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireSmokeParticle.class)
public abstract class MixinCampfireParticle extends SpriteBillboardParticle {
    protected MixinCampfireParticle(ClientWorld world, double d, double e, double f) {
        super(world, d, e, f);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void CC$setColor(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, boolean bl, CallbackInfo ci) {
        BlockPos pos = new BlockPos(velocityX, velocityY, velocityZ);
        if (world.getBlockState(pos).getBlock() instanceof CampfireBlock && world.isReceivingRedstonePower(pos)) {
            int color = world.getBlockState(pos.down()).getTopMaterialColor(world, null).color;
            float[] rgb = ColoredCampfireMod.getColorForBlock(color);
            setColor(rgb[0], rgb[1], rgb[2]);
            this.velocityX = 0;
            this.velocityY = .07D + (double) (this.random.nextFloat() / 500.0F);
            this.velocityZ = 0;
        } else {
            setColor(158 / 255f, 156 / 255f, 153 / 255f);
        }
    }
}
