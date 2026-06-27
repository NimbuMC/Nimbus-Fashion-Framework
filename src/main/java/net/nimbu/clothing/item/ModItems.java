package net.nimbu.clothing.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nimbu.clothing.Clothing;
import net.nimbu.clothing.item.custom.ClothingItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Clothing.MOD_ID);

    public static final DeferredItem<Item> CLOTH = ITEMS.register("cloth",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> HOODIE = ITEMS.register("hoodie",
            () -> new ClothingItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(10)),
                    0x9999FF));

    public static final DeferredItem<Item> SWEATPANTS = ITEMS.register("sweatpants",
            () -> new ClothingItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(10)),
                    0x111111));

    public static final DeferredItem<Item> SNEAKERS = ITEMS.register("sneakers",
            () -> new ClothingItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.BOOTS.getDurability(10)),
                    0xFFFFFFF));

    public static final DeferredItem<Item> SUNGLASSES = ITEMS.register("sunglasses",
            () -> new ClothingItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .durability(ArmorItem.Type.HELMET.getDurability(10)),
                    0xFFFFFFF));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
