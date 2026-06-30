package net.nimbu.fashionframework.item.custom;

import net.minecraft.world.item.Item;

import java.util.List;

public class StyleSchematicItem extends Item {

    private final List<Item> storedStyles;

    public StyleSchematicItem(Properties properties, List<Item> storedStyles) {
        super(properties.stacksTo(1));
        this.storedStyles = storedStyles;
    }

    public List<Item> getStoredStyles(){
        return storedStyles;
    }
}
