package net.nimbu.clothing.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.nimbu.clothing.Clothing;
import net.nimbu.clothing.item.ModItems;
import net.nimbu.clothing.tags.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Clothing.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.SUNGLASSES.get());

        this.tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.HOODIE.get());

        this.tag(ItemTags.LEG_ARMOR)
                .add(ModItems.SWEATPANTS.get());

        this.tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.SNEAKERS.get());

        this.tag(ModTags.Items.CLOTHING)
                .add(ModItems.HOODIE.get())
                .add(ModItems.SWEATPANTS.get())
                .add(ModItems.SNEAKERS.get())
                .add(ModItems.SUNGLASSES.get());

        this.tag(ItemTags.DYEABLE)
                .add(ModItems.HOODIE.get())
                .add(ModItems.SWEATPANTS.get())
                .add(ModItems.SNEAKERS.get());


    }
}
