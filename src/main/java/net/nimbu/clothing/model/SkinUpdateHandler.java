package net.nimbu.clothing.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.nimbu.clothing.Clothing;

import java.io.IOException;

public class SkinUpdateHandler {

    public static void onEquipmentChanged(AbstractClientPlayer player) throws IOException {
        Minecraft mc = Minecraft.getInstance();

        ResourceLocation base = player.getSkin().texture();

        ResourceLocation overlay = ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "textures/models/clothes/test.png");

        ResourceLocation merged =
                PlayerSkinComposer.mergeTextures(mc, base, overlay, "skin/" + player.getUUID());

        SkinCache.set(player.getUUID(), merged);
    }
}
