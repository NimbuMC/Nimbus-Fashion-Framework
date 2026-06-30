package net.nimbu.clothing.model;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class PlayerSkinComposer {

    public static ResourceLocation mergeTextures(
            Minecraft mc,
            ResourceLocation base,
            ResourceLocation overlay,
            String outName
    ) throws IOException {

        TextureManager texManager = mc.getTextureManager();

        NativeImage baseImg = NativeImage.read(
                mc.getResourceManager().getResource(base).get().open()
        );

        NativeImage overlayImg = NativeImage.read(
                mc.getResourceManager().getResource(overlay).get().open()
        );

        int width = baseImg.getWidth();
        int height = baseImg.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int o = overlayImg.getPixelRGBA(x, y);
                float alpha = ((o >> 24) & 0xFF) / 255f;

                if (alpha == 0) continue;

                int b = baseImg.getPixelRGBA(x, y);

                baseImg.setPixelRGBA(x, y, blend(b, o));
            }
        }

        DynamicTexture dyn = new DynamicTexture(baseImg);
        ResourceLocation rl = texManager.register(outName, dyn);

        return rl;
    }

    private static int blend(int base, int overlay) {
        int ba = (base >> 24) & 0xFF;
        int br = (base >> 16) & 0xFF;
        int bg = (base >> 8) & 0xFF;
        int bb = base & 0xFF;

        int oa = (overlay >> 24) & 0xFF;
        int or = (overlay >> 16) & 0xFF;
        int og = (overlay >> 8) & 0xFF;
        int ob = overlay & 0xFF;

        float a = oa / 255f;

        int r = (int)(br * (1 - a) + or * a);
        int g = (int)(bg * (1 - a) + og * a);
        int b = (int)(bb * (1 - a) + ob * a);

        return (0xFF << 24) | (r << 16) | (g << 8) | b;
    }
}