package net.nimbu.clothing.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.nimbu.clothing.Clothing;
import net.nimbu.clothing.model.ClothingLayer;
import net.nimbu.clothing.tags.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    //Add clothing layer
    @Inject(method = "<init>", at = @At("TAIL"))
    private void addCustomArmorLayer(EntityRendererProvider.Context context,
                                     boolean useSlimModel,
                                     CallbackInfo ci) {

        PlayerRenderer self = (PlayerRenderer)(Object)this;
        self.addLayer(new ClothingLayer(self, context, useSlimModel));

    }

    //Handle player skin layers:
    @Inject(
            method = "setModelProperties",
            at = @At("TAIL"),
            cancellable = true
    )
    private void setModelProperties(
            AbstractClientPlayer clientPlayer,
            CallbackInfo ci
    ) {
        PlayerModel<AbstractClientPlayer> model =
                ((PlayerRenderer)(Object)this).getModel();

        boolean hasChestClothing = clientPlayer.getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.CLOTHING);
        boolean hasLegClothing  = clientPlayer.getItemBySlot(EquipmentSlot.LEGS).is(ModTags.Items.CLOTHING);

        if(hasChestClothing) {
            model.leftSleeve.visible = false;
            model.rightSleeve.visible = false;
            model.jacket.visible = false;
        }
        if(hasLegClothing) {
            model.leftPants.visible = false;
            model.rightPants.visible = false;
        }
    }






    //    @Inject(method = "getTextureLocation", at = @At("HEAD"), cancellable = true)
//    private void overrideTexture(AbstractClientPlayer player, CallbackInfoReturnable<ResourceLocation> cir) {
//            cir.setReturnValue(
//                    ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "textures/models/armor/hoodie_layer.png")
//            );
//
//    }
}
