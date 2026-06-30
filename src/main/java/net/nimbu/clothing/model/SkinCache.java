package net.nimbu.clothing.model;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinCache {
    private static final Map<UUID, ResourceLocation> CACHE = new HashMap<>();

    public static void set(UUID id, ResourceLocation rl) {
        CACHE.put(id, rl);
    }

    public static ResourceLocation get(UUID id) {
        return CACHE.get(id);
    }
}