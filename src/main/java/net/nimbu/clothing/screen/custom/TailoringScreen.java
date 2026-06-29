package net.nimbu.clothing.screen.custom;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.nimbu.clothing.Clothing;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class TailoringScreen extends AbstractContainerScreen<TailoringMenu> {

    private static final ResourceLocation GUI_TEXTURE=
            ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "textures/gui/tailoring/tailoring_menu.png");
    private static final ResourceLocation EMPTY_SLOT_FABRIC_SHEET =
            ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "container/tailoring/empty_slot_fabric_sheet");
    private static final ResourceLocation EMPTY_SCHEMATIC_SHEET =
            ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "container/tailoring/empty_slot_schematic");
    private float xMouse;
    private float yMouse;

    private static final int STYLE_COLUMNS = 2;
    private static final int STYLE_ROWS = 3;
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int PATTERN_IMAGE_SIZE = 18;
    private static final int SCROLLER_FULL_HEIGHT = 54;
    private static final int PATTERNS_X = 34;
    private static final int PATTERNS_Y = 16;

    private ItemStack styleSchematicStack;
    private ItemStack fabricSheetStack;

    private boolean displayStyles;
    private float scrollOffs;
    private boolean scrolling;
    private int startRow;


    private static final ResourceLocation STYLE_SELECTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "container/tailoring/style_selected");
    private static final ResourceLocation STYLE_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "container/tailoring/style_highlighted");
    private static final ResourceLocation STYLE_SPRITE = ResourceLocation.fromNamespaceAndPath(Clothing.MOD_ID, "container/tailoring/style");

    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.withDefaultNamespace("container/loom/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/loom/scroller_disabled");


    public TailoringScreen(TailoringMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.styleSchematicStack = ItemStack.EMPTY;
        this.fabricSheetStack = ItemStack.EMPTY;
        menu.registerUpdateListener(this::containerChanged);
        this.titleLabelY -= 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        int i = this.leftPos;
        int j = this.topPos;


        Slot fabricSheetSlot = (this.menu).getFabricSheetSlot();
        Slot schematicSlot = (this.menu).getSchematicSlot();
        if (!fabricSheetSlot.hasItem()) {
            guiGraphics.blitSprite(EMPTY_SLOT_FABRIC_SHEET, x+13, y+25, 16, 16);
        }
        if (!schematicSlot.hasItem()) {
            guiGraphics.blitSprite(EMPTY_SCHEMATIC_SHEET, x+13, y+45, 16, 16);
        }

        //player rendering
        renderEntityInInventoryFollowsMouse(guiGraphics, i + 89, j + 8, i + 138, j + 78, 30, 0.0625F, this.xMouse, this.yMouse, this.minecraft.player);

        //scroller rendering:
        int k = (int)(39.0F * this.scrollOffs); //actual size of scroller, is SCROLLER_FULL_HEIGHT - SCROLLER_HEIGHT
        ResourceLocation resourcelocation = this.displayStyles ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        guiGraphics.blitSprite(resourcelocation, i + 73, j + PATTERN_IMAGE_SIZE + k, SCROLLER_WIDTH, SCROLLER_HEIGHT);


        //Options
        if (this.displayStyles) {
            int j2 = i + 34;
            int k2 = j + 16;
            List<Item> list = (this.menu).getSelectableStyles();

            label63:
            for(int l = 0; l < STYLE_ROWS; ++l) {
                for(int i1 = 0; i1 < STYLE_COLUMNS; ++i1) {
                    int j1 = l + this.startRow;
                    int k1 = j1 * STYLE_COLUMNS + i1;
                    if (k1 >= list.size()) {
                        break label63;
                    }

                    int l1 = j2 + i1 * PATTERN_IMAGE_SIZE;
                    int i2 = k2 + l * PATTERN_IMAGE_SIZE;
                    boolean flag = pMouseX >= l1 && pMouseY >= i2 && pMouseX < l1 + PATTERN_IMAGE_SIZE && pMouseY < i2 + PATTERN_IMAGE_SIZE;
                    ResourceLocation resourcelocation1;
                    if (k1 == (this.menu).getSelectedStyleIndex()) {
                        resourcelocation1 = STYLE_SELECTED_SPRITE;
                    } else if (flag) {
                        resourcelocation1 = STYLE_HIGHLIGHTED_SPRITE;
                    } else {
                        resourcelocation1 = STYLE_SPRITE;
                    }



                    guiGraphics.blitSprite(resourcelocation1, l1, i2, PATTERN_IMAGE_SIZE, PATTERN_IMAGE_SIZE);

                    Item item = (this.menu).getSelectableStyles().get(k1);
                    ItemStack stack = item.getDefaultInstance();
                    stack.set(DataComponents.DYED_COLOR, new DyedItemColor(0xFFFFFF, false));
                    guiGraphics.renderItem(stack, l1+1, i2+1);}
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.xMouse = (float)pMouseX;
        this.yMouse = (float)pMouseY;

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    private int totalRowCount() {
        return Mth.positiveCeilDiv(((TailoringMenu)this.menu).getSelectableStyles().size(), STYLE_COLUMNS);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        if (this.displayStyles) {
            int leftCorner = this.leftPos + PATTERNS_X;
            int topCorner = this.topPos + PATTERNS_Y;

            for(int k = 0; k < STYLE_ROWS; ++k) {
                for(int l = 0; l < STYLE_COLUMNS; ++l) {
                    double d0 = mouseX - (double)(leftCorner + l * PATTERN_IMAGE_SIZE);
                    double d1 = mouseY - (double)(topCorner + k * PATTERN_IMAGE_SIZE);
                    int i1 = k + this.startRow;
                    int j1 = i1 * STYLE_COLUMNS + l;
                    if (d0 >= 0.0 && d1 >= 0.0 && d0 < PATTERN_IMAGE_SIZE && d1 < PATTERN_IMAGE_SIZE && (this.menu).clickMenuButton(this.minecraft.player, j1)) {
                        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0F));
                        this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, j1);
                        return true;
                    }
                }
            }

            leftCorner = this.leftPos + 73;
            topCorner = this.topPos + 16;
            if (mouseX >= (double)leftCorner && mouseX < (double)(leftCorner + 12) && mouseY >= (double)topCorner && mouseY < (double)(topCorner + SCROLLER_FULL_HEIGHT)) {
                this.scrolling = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }


    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.totalRowCount() - STYLE_ROWS;
        if (this.scrolling && this.displayStyles && i > 0) {
            int j = this.topPos + PATTERNS_Y;
            int k = j + SCROLLER_FULL_HEIGHT;
            this.scrollOffs = ((float)mouseY - (float)j - 7.5F) / ((float)(k - j) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startRow = Math.max((int)((double)(this.scrollOffs * (float)i) + 0.5), 0);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int i = this.totalRowCount() - STYLE_ROWS;
        if (this.displayStyles && i > 0) {
            float f = (float)scrollY / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startRow = Math.max((int)(this.scrollOffs * (float)i + 0.5F), 0);
        }

        return true;
    }

    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        return mouseX < (double)guiLeft || mouseY < (double)guiTop || mouseX >= (double)(guiLeft + this.imageWidth) || mouseY >= (double)(guiTop + this.imageHeight);
    }

    private void containerChanged() {



        ItemStack itemstack = (this.menu).getResultSlot().getItem();

        ItemStack itemstack1 = (this.menu).getSchematicSlot().getItem();
        ItemStack itemstack2 = (this.menu).getFabricSheetSlot().getItem();

        if (!ItemStack.matches(itemstack1, this.styleSchematicStack) || !ItemStack.matches(itemstack2, this.fabricSheetStack)) {
            this.displayStyles = !itemstack1.isEmpty() && !itemstack2.isEmpty() && !(this.menu).getSelectableStyles().isEmpty();
        }

        if (this.startRow >= this.totalRowCount()) {
            this.startRow = 0;
            this.scrollOffs = 0.0F;
        }

        this.styleSchematicStack = itemstack1.copy();
        this.fabricSheetStack = itemstack2.copy();

//        System.out.println("listener ran. Display styles = " + this.displayStyles);
//        System.out.println("Selectable styles = " + ((TailoringMenu)this.menu).getSelectableStyles().toString());
    }



    //Inventory player code:
    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int scale, float yOffset, float mouseX, float mouseY, LivingEntity entity) {
        float f = (float)(x1 + x2) / 2.0F;
        float f1 = (float)(y1 + y2) / 2.0F;
        float f2 = (float)Math.atan((double)((f - mouseX) / 40.0F));
        float f3 = (float)Math.atan((double)((f1 - mouseY) / 40.0F));
        renderEntityInInventoryFollowsAngle(guiGraphics, x1, y1, x2, y2, scale, yOffset, f2, f3, entity);
    }

    public static void renderEntityInInventoryFollowsAngle(GuiGraphics p_282802_, int p_275688_, int p_275245_, int p_275535_, int p_294406_, int p_294663_, float p_275604_, float angleXComponent, float angleYComponent, LivingEntity p_275689_) {
        float f = (float)(p_275688_ + p_275535_) / 2.0F;
        float f1 = (float)(p_275245_ + p_294406_) / 2.0F;
        p_282802_.enableScissor(p_275688_, p_275245_, p_275535_, p_294406_);
        float f2 = angleXComponent;
        float f3 = angleYComponent;
        Quaternionf quaternionf = (new Quaternionf()).rotateZ(3.1415927F);
        Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f3 * 20.0F * 0.017453292F);
        quaternionf.mul(quaternionf1);
        float f4 = p_275689_.yBodyRot;
        float f5 = p_275689_.getYRot();
        float f6 = p_275689_.getXRot();
        float f7 = p_275689_.yHeadRotO;
        float f8 = p_275689_.yHeadRot;
        p_275689_.yBodyRot = 180.0F + f2 * 20.0F;
        p_275689_.setYRot(180.0F + f2 * 40.0F);
        p_275689_.setXRot(-f3 * 20.0F);
        p_275689_.yHeadRot = p_275689_.getYRot();
        p_275689_.yHeadRotO = p_275689_.getYRot();
        float f9 = p_275689_.getScale();
        Vector3f vector3f = new Vector3f(0.0F, p_275689_.getBbHeight() / 2.0F + p_275604_ * f9, 0.0F);
        float f10 = (float)p_294663_ / f9;
        renderEntityInInventory(p_282802_, f, f1, f10, vector3f, quaternionf, quaternionf1, p_275689_);
        p_275689_.yBodyRot = f4;
        p_275689_.setYRot(f5);
        p_275689_.setXRot(f6);
        p_275689_.yHeadRotO = f7;
        p_275689_.yHeadRot = f8;
        p_282802_.disableScissor();
    }

    public static void renderEntityInInventory(GuiGraphics guiGraphics, float x, float y, float scale, Vector3f translate, Quaternionf pose, @Nullable Quaternionf cameraOrientation, LivingEntity entity) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((double)x, (double)y, 50.0);
        guiGraphics.pose().scale(scale, scale, -scale);
        guiGraphics.pose().translate(translate.x, translate.y, translate.z);
        guiGraphics.pose().mulPose(pose);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (cameraOrientation != null) {
            entityrenderdispatcher.overrideCameraOrientation(cameraOrientation.conjugate(new Quaternionf()).rotateY(3.1415927F));
        }

        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880);
        });
        guiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        guiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }

}
