package net.nimbu.clothing.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.DyedItemColor;

public class ClothingItem extends ArmorItem {

    private final int defaultColor;

    public ClothingItem(Holder<ArmorMaterial> material, Type type, Properties properties, int defaultColor) {
        super(material, type, properties);
        this.defaultColor = defaultColor;
    }

    public int getDefaultColor(){
        return defaultColor;
    }
}
