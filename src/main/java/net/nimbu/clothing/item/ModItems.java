package net.nimbu.clothing.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nimbu.clothing.Clothing;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Clothing.MOD_ID);

    public static final DeferredItem<Item> CLOTH = ITEMS.register("cloth",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> HOODIE = ITEMS.register("hoodie",
            () -> new ArmorItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));

    public static final DeferredItem<Item> SWEATPANTS = ITEMS.register("sweatpants",
            () -> new ArmorItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(10))));

    public static final DeferredItem<Item> SNEAKERS = ITEMS.register("sneakers",
            () -> new ArmorItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.BOOTS.getDurability(10))));

//    public static final DeferredItem<Item> HOODIE_HOOD = ITEMS.register("hoodie_hood",
//            () -> new ArmorItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.HELMET,
//                    new Item.Properties()
//                            .durability(ArmorItem.Type.HELMET.getDurability(10))));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
