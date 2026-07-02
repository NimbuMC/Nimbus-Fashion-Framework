package net.nimbu.fashionframework;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.nimbu.fashionframework.item.ModItems;
import net.nimbu.fashionframework.item.custom.ClothingItem;
import net.nimbu.fashionframework.model.ClothingLayer;
import net.nimbu.fashionframework.model.ClothingModel;
import net.nimbu.fashionframework.model.ModModelLayers;
import net.nimbu.fashionframework.screen.ModMenuTypes;
import net.nimbu.fashionframework.screen.custom.TailoringScreen;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = FashionFramework.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = FashionFramework.MOD_ID, value = Dist.CLIENT)
public class FashionFrameworkClient {


    public static PlayerModel slimHandClothingModel;
    public static PlayerModel wideHandClothingModel;

    public FashionFrameworkClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {

        event.getSkins().forEach(skin -> {
            PlayerRenderer renderer = event.getSkin(skin);

            renderer.addLayer(new ClothingLayer(
                    renderer,
                    event.getContext(),
                    skin == PlayerSkin.Model.SLIM
            ));
        });

        slimHandClothingModel = new PlayerModel<>(
                event.getContext().bakeLayer(ModModelLayers.CLOTHING_PLAYER_SLIM),
                true);
        wideHandClothingModel = new PlayerModel<>(
                event.getContext().bakeLayer(ModModelLayers.CLOTHING_PLAYER),
                true);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                ModModelLayers.CLOTHING_PLAYER,
                () -> LayerDefinition.create(
                        ClothingModel.createMesh(new CubeDeformation(0.05F), false),
                        64, 64
                )
        );

        event.registerLayerDefinition(
                ModModelLayers.CLOTHING_PLAYER_SLIM,
                () -> LayerDefinition.create(
                        ClothingModel.createMesh(new CubeDeformation(0.01F), true),
                        64, 64
                )
        );
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        event.register(ModMenuTypes.TAILORING.get(), TailoringScreen::new);
    }
}
