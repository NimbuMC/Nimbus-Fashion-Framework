package net.nimbu.clothing.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.nimbu.clothing.Clothing;

public class TailoringScreen extends AbstractContainerScreen<TailoringMenu> {

    private static final ResourceLocation GUI_TEXTURE=
            ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "textures/gui/tailoring/tailoring_menu.png");
    static final ResourceLocation EMPTY_SLOT_FABRIC_SHEET =
            ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "item/empty_slot_fabric_sheet");
    // ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "item/empty_slot_fabric_sheet");

    public TailoringScreen(TailoringMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        guiGraphics.blit(EMPTY_SLOT_FABRIC_SHEET, x+13, y+25, 0, 0, 16, 16);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

}
