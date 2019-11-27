package net.kyrptonaught.coloredcampfire;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;


public class ColoredCampfireMod implements ClientModInitializer {
    private static final String MOD_ID = "coloredcampfire";
    static HashMap<Integer, float[]> COLOR_CACHE = new HashMap<>();
    public static final ParticleType<BlockStateParticleEffect> COLOREDSMOKE = Registry.register(Registry.PARTICLE_TYPE, MOD_ID + ":coloredsmoke", FabricParticleTypes.complex(true, BlockStateParticleEffect.PARAMETERS_FACTORY));

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.PARTICLE_ATLAS_TEX).register((atlasTexture, registry) -> {
            for (int i = 0; i < 12; i++) {
                registry.register(new Identifier(MOD_ID, "big_smoke_" + i));
            }
        });
        ParticleFactoryRegistry.getInstance().register(COLOREDSMOKE, ColoredSmokeFactory::new);
    }
}
