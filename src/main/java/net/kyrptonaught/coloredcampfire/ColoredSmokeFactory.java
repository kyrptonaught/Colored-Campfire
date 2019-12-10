package net.kyrptonaught.coloredcampfire;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.world.World;


@Environment(EnvType.CLIENT)
public class ColoredSmokeFactory implements ParticleFactory<BlockStateParticleEffect> {
    private final FabricSpriteProvider sprites;

    ColoredSmokeFactory(FabricSpriteProvider sprites) {
        this.sprites = sprites;
    }

    public Particle createParticle(BlockStateParticleEffect blockStateParticleEffect, World world, double x, double y, double z, double vX, double vY, double vZ) {
        BlockState blockState = blockStateParticleEffect.getBlockState();
        int color = blockState.getTopMaterialColor(world, null).color;
        if (!ColoredCampfireMod.COLOR_CACHE.containsKey(color)) {
            int l = (color & 16711680) >> 16;
            int m = (color & '\uff00') >> 8;
            int n = (color & 255);
            ColoredCampfireMod.COLOR_CACHE.put(color, new float[]{(float) l / 255.0F, (float) m / 255.0F, (float) n / 255.0F});
        }
        CampfireSmokeParticle particle = (CampfireSmokeParticle) new CampfireSmokeParticle.SignalSmokeFactory(sprites).createParticle(null, world, x, y, z, vX, vY, vZ);
        float[] rgb = ColoredCampfireMod.COLOR_CACHE.get(color);
        particle.setColor(rgb[0], rgb[1], rgb[2]);
        return particle;
    }
}
