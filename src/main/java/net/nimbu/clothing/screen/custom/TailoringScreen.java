package net.nimbu.clothing.screen.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.nimbu.clothing.Clothing;

public class TailoringScreen extends ItemCombinerScreen<TailoringMenu> {

    private static final ResourceLocation GUI_TEXTURE=
            ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "textures/gui/tailoring/tailoring_menu.png");
    static final ResourceLocation EMPTY_SLOT_FABRIC_SHEET = ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "item/empty_slot_fabric_sheet");
    // ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "item/empty_slot_fabric_sheet");

    public TailoringScreen(TailoringMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, GUI_TEXTURE);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(EMPTY_SLOT_FABRIC_SHEET);
        guiGraphics.blit(this.leftPos + 13, this.topPos + 25, 0, 16, 16, textureatlassprite, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void renderErrorIcon(GuiGraphics guiGraphics, int i, int i1) {

    }
}
