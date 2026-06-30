package net.nimbu.fashionframework;

import net.minecraft.world.item.CreativeModeTabs;
import net.nimbu.fashionframework.block.ModBlocks;
import net.nimbu.fashionframework.item.ModItems;
import net.nimbu.fashionframework.screen.ModMenuTypes;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FashionFramework.MOD_ID)
public class FashionFramework {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fashionframework";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FashionFramework(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.FABRIC_SHEET);
            event.accept(ModBlocks.TAILORING_TABLE);
            event.accept(ModItems.HERO_STYLE_SCHEMATIC);
            event.accept(ModItems.SUMMER_STYLE_SCHEMATIC);
            event.accept(ModItems.MODERN_STYLE_SCHEMATIC);
            event.accept(ModItems.KITTY_STYLE_SCHEMATIC);
            event.accept(ModItems.CREATOR_STYLE_SCHEMATIC);
        }
        else if(event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ModItems.HOODIE);
            event.accept(ModItems.SWEATPANTS);
            //event.accept(ModItems.HOODIE_HOOD);
            event.accept(ModItems.SNEAKERS);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
