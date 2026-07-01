package net.nimbu.fashionframework.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.nimbu.fashionframework.FashionFramework;
import net.nimbu.fashionframework.FashionFrameworkClient;
import net.nimbu.fashionframework.item.custom.ClothingItem;
import net.nimbu.fashionframework.tags.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderPlayerArm", at = @At("TAIL"))
    private void renderClothingArm(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            float equippedProgress,
            float swingProgress,
            HumanoidArm side,
            CallbackInfo ci
    ) {

        //Duplicated clothing layer code for first person hand rendering:


        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayer player = mc.player;
        if (player == null) return;

        boolean slim = player.getSkin().model() == PlayerSkin.Model.SLIM;

        PlayerRenderer renderer =
                (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);

        PlayerModel<AbstractClientPlayer> vanilla = renderer.getModel();

        PlayerModel clothingModel;
        if (slim){
            clothingModel = FashionFrameworkClient.slimHandClothingModel;
        }
        else{
            clothingModel = FashionFrameworkClient.wideHandClothingModel;
        }


        vanilla.copyPropertiesTo(clothingModel);
        clothingModel.rightSleeve.copyFrom(clothingModel.rightArm);
        clothingModel.leftSleeve.copyFrom(clothingModel.leftArm);


        for (EquipmentSlot slot : EquipmentSlot.values()) {


            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.is(ModTags.Items.CLOTHING)) {
                if (stack.getItem() instanceof ClothingItem clothingItem) {

                    if (!stack.is(ModTags.Items.CLOTHING))
                        continue;

                    String namespace = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
                    String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();

                    //Draw the dyed layer
                    ResourceLocation texture;
                    if (slim) {
                        texture = ResourceLocation.fromNamespaceAndPath(FashionFramework.MOD_ID, "textures/models/clothes/slim/slim_" + path + "_layer_0.png");
                    } else {
                        texture = ResourceLocation.fromNamespaceAndPath(namespace, "textures/models/clothes/wide/" + path + "_layer_0.png");
                    }

                    VertexConsumer vc = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
                    int color = stack.getOrDefault(
                            DataComponents.DYED_COLOR,
                            new DyedItemColor(clothingItem.getDefaultColor(), false)).rgb();

                    if (side == HumanoidArm.RIGHT) {
                        clothingModel.rightArm.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                color
                        );

                        clothingModel.rightSleeve.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                color
                        );
                    } else {
                        clothingModel.leftArm.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                color
                        );

                        clothingModel.leftSleeve.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                color
                        );
                    }

                    //Draw the non-dyed layer
                    if (slim) {
                        texture = ResourceLocation.fromNamespaceAndPath(FashionFramework.MOD_ID, "textures/models/clothes/slim/slim_" + path + "_layer_1.png");
                    } else {
                        texture = ResourceLocation.fromNamespaceAndPath(namespace, "textures/models/clothes/wide/" + path + "_layer_1.png");
                    }
                    vc = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));

                    if (side == HumanoidArm.RIGHT) {
                        clothingModel.rightArm.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY
                        );

                        clothingModel.rightSleeve.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY
                        );
                    } else {
                        clothingModel.leftArm.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY
                        );

                        clothingModel.leftSleeve.render(
                                poseStack,
                                vc,
                                packedLight,
                                OverlayTexture.NO_OVERLAY
                        );
                    }
                }
                }
        }
    }
}