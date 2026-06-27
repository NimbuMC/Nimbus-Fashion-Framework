package net.nimbu.clothing;

import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.nimbu.clothing.item.ModItems;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Clothing.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Clothing.MOD_ID, value = Dist.CLIENT)
public class ClothingClient {
    public ClothingClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();

            minecraft.getItemColors().register(
                    (stack, tintIndex) -> {
                        if (tintIndex == 0) {
                            return 0xFF000000 | //ORs in the alpha value
                                    stack.getOrDefault(
                                            DataComponents.DYED_COLOR,
                                            new DyedItemColor(0xd37d19, false)
                                    ).rgb();
                        }
                        return 0xFFFFFFFF;
                    },
                    ModItems.HOODIE.get()
            );
        });

    }
}
