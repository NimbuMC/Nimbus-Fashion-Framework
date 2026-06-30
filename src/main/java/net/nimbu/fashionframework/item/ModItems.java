package net.nimbu.fashionframework.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nimbu.fashionframework.FashionFramework;
import net.nimbu.fashionframework.item.custom.ClothingItem;
import net.nimbu.fashionframework.item.custom.StyleSchematicItem;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FashionFramework.MOD_ID);

    public static final DeferredItem<Item> FABRIC_SHEET = ITEMS.register("fabric_sheet",
            () -> new Item(new Item.Properties()));



    //Default Clothing:
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
    public static final DeferredItem<Item> LOW_SUNGLASSES = ITEMS.register("low_sunglasses",
            () -> new ClothingItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .durability(ArmorItem.Type.HELMET.getDurability(10)),
                    0xFFFFFFF));


    //Kitty clothing:
    public static final DeferredItem<Item> CAT_EARS = ITEMS.register("cat_ears",
            () -> new ClothingItem(ModArmorMaterials.CLOTH_ARMOUR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .durability(ArmorItem.Type.HELMET.getDurability(10)),
                    0xFFFFFFF));


    //Schematic:
    public static final DeferredItem<Item> MODERN_STYLE_SCHEMATIC = ITEMS.register("modern_style_schematic",
            () -> new StyleSchematicItem(new Item.Properties(),
                    List.of(ModItems.HOODIE.get(),
                            ModItems.CAT_EARS.get(),
                            ModItems.SWEATPANTS.get(),
                            ModItems.SNEAKERS.get(),
                            ModItems.SUNGLASSES.get(),
                            ModItems.LOW_SUNGLASSES.get()
                    )));

    public static final DeferredItem<Item> HERO_STYLE_SCHEMATIC = ITEMS.register("hero_style_schematic",
            () -> new StyleSchematicItem(new Item.Properties(),
                    List.of(ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),
                            ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get(),ModItems.HOODIE.get()
                    )));

    public static final DeferredItem<Item> SUMMER_STYLE_SCHEMATIC = ITEMS.register("summer_style_schematic",
            () -> new StyleSchematicItem(new Item.Properties(),
                    List.of(ModItems.HOODIE.get(),
                            ModItems.CAT_EARS.get()
                    )));

    public static final DeferredItem<Item> CREATOR_STYLE_SCHEMATIC = ITEMS.register("creator_style_schematic",
            () -> new StyleSchematicItem(new Item.Properties(),
                    List.of(ModItems.HOODIE.get(),
                            ModItems.CAT_EARS.get()
                    )));

    public static final DeferredItem<Item> KITTY_STYLE_SCHEMATIC = ITEMS.register("kitty_style_schematic",
            () -> new StyleSchematicItem(new Item.Properties(),
                    List.of(ModItems.HOODIE.get(),
                            ModItems.CAT_EARS.get()
                    )));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
