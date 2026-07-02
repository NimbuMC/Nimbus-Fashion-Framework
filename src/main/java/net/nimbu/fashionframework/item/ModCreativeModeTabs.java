package net.nimbu.fashionframework.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nimbu.fashionframework.FashionFramework;
import net.nimbu.fashionframework.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FashionFramework.MOD_ID);

    public static final Supplier<CreativeModeTab> FASHION_FRAMEWORK_TAB = CREATIVE_MODE_TAB.register("fashion_framework_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FABRIC_SHEET.get()))
                    .title(Component.translatable("creativetab.fashionframework.fashion_framework_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.FABRIC_SHEET);
                        output.accept(ModBlocks.TAILORING_TABLE);
                    })

                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
