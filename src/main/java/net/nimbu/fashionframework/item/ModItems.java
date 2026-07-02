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

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
