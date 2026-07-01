package net.nimbu.fashionframework.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.nimbu.fashionframework.FashionFramework;
import net.nimbu.fashionframework.item.custom.ClothingItem;
import net.nimbu.fashionframework.tags.ModTags;

public class ClothingLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private final PlayerModel<AbstractClientPlayer> model;
    private final boolean slim;

    public ClothingLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent,
                         EntityRendererProvider.Context context, boolean slim) {
        super(parent);
        this.model = new PlayerModel<>(
                context.bakeLayer(slim ? ModModelLayers.CLOTHING_PLAYER_SLIM : ModModelLayers.CLOTHING_PLAYER),
                slim);
        this.slim=slim;
    }

    @Override
    public void render(PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       AbstractClientPlayer player,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTicks,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {

        this.getParentModel().copyPropertiesTo(model);

        model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);
        model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        for (EquipmentSlot slot : new EquipmentSlot[] {
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.CHEST,
                EquipmentSlot.HEAD
        }) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.is(ModTags.Items.CLOTHING)) {
                if (stack.getItem() instanceof ClothingItem clothingItem){

                    String namespace = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
                    String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();

                    //Draw the dyed layer
                    ResourceLocation texture;
                    if(slim){
                        texture = ResourceLocation.fromNamespaceAndPath(FashionFramework.MOD_ID, "textures/models/clothes/slim/slim_"+path+"_layer_0.png");
                    }
                    else{
                        texture = ResourceLocation.fromNamespaceAndPath(namespace, "textures/models/clothes/wide/"+path+"_layer_0.png");
                    }

                    VertexConsumer vc = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
                    int color = stack.getOrDefault(
                            DataComponents.DYED_COLOR,
                            new DyedItemColor(clothingItem.getDefaultColor(), false)).rgb();
                    color |= 0xFF000000;

                    model.renderToBuffer(
                            poseStack,
                            vc,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            color
                    );

                    //Draw the non-dyed layer
                    if(slim){
                        texture = ResourceLocation.fromNamespaceAndPath(FashionFramework.MOD_ID, "textures/models/clothes/slim/slim_"+path+"_layer_1.png");
                    }
                    else{
                        texture = ResourceLocation.fromNamespaceAndPath(namespace, "textures/models/clothes/wide/"+path+"_layer_1.png");
                    }
                    vc = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));

                    model.renderToBuffer(
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
