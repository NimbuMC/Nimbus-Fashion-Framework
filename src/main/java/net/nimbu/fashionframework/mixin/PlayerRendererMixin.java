package net.nimbu.fashionframework.mixin;

import lain.mods.cos.api.CosArmorAPI;
import lain.mods.cos.api.inventory.CAStacksBase;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.nimbu.fashionframework.model.CosmeticArmorBridge;
import net.nimbu.fashionframework.tags.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

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

        boolean chestArmorVisible =
                CosmeticArmorBridge.chestArmorVisible(clientPlayer.getUUID());
        boolean legArmorVisible =
                CosmeticArmorBridge.legArmorVisible(clientPlayer.getUUID());
        ItemStack chestArmorPiece =
                CosmeticArmorBridge.getChestArmorPiece(clientPlayer.getUUID());
        ItemStack legArmorPiece =
                CosmeticArmorBridge.getLegArmorPiece(clientPlayer.getUUID());

        boolean hasChestClothing = !chestArmorVisible && (clientPlayer.getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.CLOTHING) || chestArmorPiece.is(ModTags.Items.CLOTHING));
        boolean hasLegClothing  = !legArmorVisible && (clientPlayer.getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.CLOTHING) || legArmorPiece.is(ModTags.Items.CLOTHING));

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

//    //Handle player skin layers:
//    @Inject(
//            method = "setModelProperties",
//            at = @At("TAIL"),
//            cancellable = true
//    )
//    private void setModelProperties(
//            AbstractClientPlayer clientPlayer,
//            CallbackInfo ci
//    ) {
//        PlayerModel<AbstractClientPlayer> model =
//                ((PlayerRenderer)(Object)this).getModel();
//
//
//        CAStacksBase ca = CosArmorAPI.getCAStacks(clientPlayer.getUUID());
//
//        boolean hasChestClothing = !ca.isSkinArmor(2) && (clientPlayer.getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.CLOTHING) || ca.getStackInSlot(2).is(ModTags.Items.CLOTHING));
//        boolean hasLegClothing  = !ca.isSkinArmor(1) && (clientPlayer.getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.CLOTHING) || ca.getStackInSlot(1).is(ModTags.Items.CLOTHING));
//
//        if(hasChestClothing) {
//            model.leftSleeve.visible = false;
//            model.rightSleeve.visible = false;
//            model.jacket.visible = false;
//        }
//        if(hasLegClothing) {
//            model.leftPants.visible = false;
//            model.rightPants.visible = false;
//        }
//    }
}







//
//    //Add clothing layer
//    @Inject(method = "<init>", at = @At("TAIL"))
//    private void addCustomArmorLayer(EntityRendererProvider.Context context,
//                                     boolean useSlimModel,
//                                     CallbackInfo ci) {
//
//        PlayerRenderer self = (PlayerRenderer)(Object)this;
//        self.addLayer(new ClothingLayer(self, context, useSlimModel));
//
//    }