package net.kyrptonaught.coloredcampfire;

import net.fabricmc.api.ClientModInitializer;

import java.util.HashMap;


public class ColoredCampfireMod implements ClientModInitializer {
    private static final String MOD_ID = "coloredcampfire";
    public static HashMap<Integer, float[]> COLOR_CACHE = new HashMap<>();

    @Override
    public void onInitializeClient() {

    }

    public static float[] getColorForBlock(int color) {
        if (!COLOR_CACHE.containsKey(color)) {
            int l = (color & 16711680) >> 16;
            int m = (color & '\uff00') >> 8;
            int n = (color & 255);
            COLOR_CACHE.put(color, new float[]{(float) l / 255.0F, (float) m / 255.0F, (float) n / 255.0F});
        }
        return COLOR_CACHE.get(color);
    }
}
