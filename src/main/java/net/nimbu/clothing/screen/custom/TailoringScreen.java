package net.nimbu.clothing.screen.custom;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.nimbu.clothing.Clothing;

public class TailoringScreen extends ItemCombinerScreen<TailoringMenu> {

    private static final ResourceLocation GUI_TEXTURE=
            ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "textures/gui/tailoring/tailoring_menu.png");

    public TailoringScreen(TailoringMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, GUI_TEXTURE);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
    }

    @Override
    protected void renderErrorIcon(GuiGraphics guiGraphics, int i, int i1) {

    }
}
