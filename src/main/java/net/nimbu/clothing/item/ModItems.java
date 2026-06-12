package net.nimbu.clothing.item;

import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nimbu.clothing.Clothing;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Clothing.MOD_ID);

    public static final DeferredItem<Item> JACKET = ITEMS.register("jacket",
            () -> new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.BODY, new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
