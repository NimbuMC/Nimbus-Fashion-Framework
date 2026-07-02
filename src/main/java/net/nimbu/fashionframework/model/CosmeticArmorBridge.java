package net.nimbu.fashionframework.model;


import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.lang.reflect.Method;
import java.util.UUID;

public final class CosmeticArmorBridge {

    private static final boolean LOADED =
            ModList.get().isLoaded("cosmeticarmorreworked");

    private static Class<?> apiClass;
    private static Method getCAStacks;

    static {
        if (LOADED) {
            try {
                apiClass = Class.forName("lain.mods.cos.api.CosArmorAPI");
                getCAStacks = apiClass.getMethod("getCAStacks", UUID.class);
            } catch (Throwable ignored) {
                apiClass = null;
                getCAStacks = null;
            }
        }
    }

    private static Object getCA(UUID uuid) {
        if (!LOADED || getCAStacks == null) return null;

        try {
            return getCAStacks.invoke(null, uuid);
        } catch (Throwable t) {
            return null;
        }
    }

    public static boolean headArmorVisible(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return false;

        try {
            Method isSkinArmor = ca.getClass().getMethod("isSkinArmor", int.class);
            return (boolean) isSkinArmor.invoke(ca, 3);
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean chestArmorVisible(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return false;

        try {
            Method isSkinArmor = ca.getClass().getMethod("isSkinArmor", int.class);
            return (boolean) isSkinArmor.invoke(ca, 2);
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean legArmorVisible(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return false;

        try {
            Method isSkinArmor = ca.getClass().getMethod("isSkinArmor", int.class);
            return (boolean) isSkinArmor.invoke(ca, 1);
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean feetArmorVisible(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return false;

        try {
            Method isSkinArmor = ca.getClass().getMethod("isSkinArmor", int.class);
            return (boolean) isSkinArmor.invoke(ca, 0);
        } catch (Throwable t) {
            return false;
        }
    }




    public static ItemStack getHeadArmorPiece(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return ItemStack.EMPTY;

        try {
            Method isSkinArmor = ca.getClass().getMethod("getStackInSlot", int.class);
            return (ItemStack) isSkinArmor.invoke(ca, 3);
        } catch (Throwable t) {
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack getChestArmorPiece(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return ItemStack.EMPTY;

        try {
            Method isSkinArmor = ca.getClass().getMethod("getStackInSlot", int.class);
            return (ItemStack) isSkinArmor.invoke(ca, 2);
        } catch (Throwable t) {
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack getLegArmorPiece(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return ItemStack.EMPTY;

        try {
            Method isSkinArmor = ca.getClass().getMethod("getStackInSlot", int.class);
            return (ItemStack) isSkinArmor.invoke(ca, 1);
        } catch (Throwable t) {
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack getFeetArmorPiece(UUID uuid) {
        Object ca = getCA(uuid);
        if (ca == null) return ItemStack.EMPTY;

        try {
            Method isSkinArmor = ca.getClass().getMethod("getStackInSlot", int.class);
            return (ItemStack) isSkinArmor.invoke(ca, 0);
        } catch (Throwable t) {
            return ItemStack.EMPTY;
        }
    }
}
