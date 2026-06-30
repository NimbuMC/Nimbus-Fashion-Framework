package net.nimbu.fashionframework.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.nimbu.fashionframework.FashionFramework;

public class ModModelLayers {

    public static final ModelLayerLocation CLOTHING_PLAYER =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(FashionFramework.MOD_ID, "clothing_player"),
                    "main"
            );

    public static final ModelLayerLocation CLOTHING_PLAYER_SLIM =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(FashionFramework.MOD_ID, "clothing_player_slim"),
                    "main"
            );

    private ModModelLayers() {}
}