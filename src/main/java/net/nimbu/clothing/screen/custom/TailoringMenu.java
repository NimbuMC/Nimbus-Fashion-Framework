package net.nimbu.clothing.screen.custom;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.nimbu.clothing.block.ModBlocks;
import net.nimbu.clothing.item.ModItems;
import net.nimbu.clothing.item.custom.ClothingItem;
import net.nimbu.clothing.item.custom.StyleSchematicItem;
import net.nimbu.clothing.screen.ModMenuTypes;

import java.util.List;

public class TailoringMenu extends AbstractContainerMenu {
    private static final int PATTERN_NOT_SET = -1;
    private static final int INV_SLOT_START = 4;
    private static final int INV_SLOT_END = 31;
    private static final int USE_ROW_SLOT_START = 31;
    private static final int USE_ROW_SLOT_END = 40;
    private final ContainerLevelAccess access;
    final DataSlot selectedStyleIndex;
    Runnable slotUpdateListener;
    private final Slot fabricSheetSlot;
    private final Slot schematicSlot;
    private final Slot resultSlot;
    long lastSoundTime;
    private final Container inputContainer;
    private final Container outputContainer;

    private List<Item> selectableStyles;

    public TailoringMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public TailoringMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(ModMenuTypes.TAILORING.get(), containerId);
        this.selectedStyleIndex = DataSlot.standalone();
        this.selectableStyles = List.of();
        this.slotUpdateListener = () -> {
        };
        this.inputContainer = new SimpleContainer(3) {
            @Override
            public void setChanged() {
                super.setChanged();
                TailoringMenu.this.slotsChanged(this);
                TailoringMenu.this.slotUpdateListener.run();
            }
        };
        this.outputContainer = new SimpleContainer(1) {
            @Override
            public void setChanged() {
                super.setChanged();
                TailoringMenu.this.slotUpdateListener.run();
            }
        };
        this.access = access;
        this.fabricSheetSlot = this.addSlot(new Slot(this.inputContainer, 0, 13, 25) {
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.FABRIC_SHEET.get());
            }
        });
        this.schematicSlot = this.addSlot(new Slot(this.inputContainer, 2, 13, 45) {
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof StyleSchematicItem;
            }
        });
        this.resultSlot = this.addSlot(new Slot(this.outputContainer, 0, 146, 57) {
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                TailoringMenu.this.fabricSheetSlot.remove(1);
                if (!TailoringMenu.this.fabricSheetSlot.hasItem()) {
                    TailoringMenu.this.selectedStyleIndex.set(-1);
                }

                access.execute((p_39952_, p_39953_) -> {
                    long l = p_39952_.getGameTime();
                    if (TailoringMenu.this.lastSoundTime != l) {
                        p_39952_.playSound((Player)null, p_39953_, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        TailoringMenu.this.lastSoundTime = l;
                    }

                });
                super.onTake(player, stack);
            }
        });


        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        //this.addDataSlot(this.selectedBannerPatternIndex);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.TAILORING_TABLE.get());
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < this.selectableStyles.size()) {
            this.selectedStyleIndex.set(id);
            this.setupResultSlot(this.selectableStyles.get(id));
            return true;
        } else {
            return false;
        }
    }

    public List<Item> getSelectableStyles() {
        return this.selectableStyles;
    }

    private List<Item> getSelectableStyles(ItemStack stack) {
        if (stack.isEmpty()) {
            return List.of();
        } else {
            Item item = stack.getItem();
            List list;
            if (item instanceof StyleSchematicItem) {
                StyleSchematicItem schematicItem = (StyleSchematicItem) item;
                list = schematicItem.getStoredStyles();
            } else {
                list = List.of();
            }

            return list;
        }
    }

    private boolean isValidStyleIndex(int index) {
        return index >= 0 && index < this.selectableStyles.size();
    }

    public void slotsChanged(Container inventory) {
        ItemStack itemstack = this.schematicSlot.getItem();
        ItemStack itemstack1 = this.fabricSheetSlot.getItem();
        if (!itemstack.isEmpty() && !itemstack1.isEmpty()) {  //if both items present
            int i = this.selectedStyleIndex.get();
            boolean flag = this.isValidStyleIndex(i);
            List<Item> list = this.selectableStyles;
            this.selectableStyles = this.getSelectableStyles(itemstack);
            Item item;
            if (this.selectableStyles.size() == 1) { //if only one option, set to it
                this.selectedStyleIndex.set(0);
                item = this.selectableStyles.get(0);
            } else if (!flag) { //if selection out of bounds, set to -1
                this.selectedStyleIndex.set(-1);
                item = null;
            } else { //if a valid selection
                Item item1 = list.get(i);
                int j = this.selectableStyles.indexOf(item1);
                if (j != -1) { //if item found
                    item = item1;
                    this.selectedStyleIndex.set(j);
                } else {
                    item = null;
                    this.selectedStyleIndex.set(-1);
                }
            }


            if (item == null) {
                this.resultSlot.set(ItemStack.EMPTY);
            }
            else{
                this.setupResultSlot(item);
            }

            this.broadcastChanges();
        } else { //if no schematic
            this.resultSlot.set(ItemStack.EMPTY);
            this.selectableStyles = List.of();
            this.selectedStyleIndex.set(-1);
        }

    }


    public int getSelectedStyleIndex() {
        return this.selectedStyleIndex.get();
    }

    public void registerUpdateListener(Runnable listener) {
        this.slotUpdateListener = listener;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == this.resultSlot.index) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != this.fabricSheetSlot.index && index != this.schematicSlot.index) { //if clicking inventory item / result item
                if (itemstack1.is(ModItems.FABRIC_SHEET.get())) { //move item to fabric slot
                    if (!this.moveItemStackTo(itemstack1, this.fabricSheetSlot.index, this.fabricSheetSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack1.getItem() instanceof StyleSchematicItem) { //move item to schematic slot
                    if (!this.moveItemStackTo(itemstack1, this.schematicSlot.index, this.schematicSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 31) { //regular inventory shift click
                    if (!this.moveItemStackTo(itemstack1, 31, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 39 && !this.moveItemStackTo(itemstack1, 4, 31, false)) { //regular hotbar shift click
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) { //if clicking item in table
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((p_39871_, p_39872_) -> {
            this.clearContainer(player, this.inputContainer);
        });
    }



    private void setupResultSlot(Item item) {
        ItemStack itemstack = this.fabricSheetSlot.getItem();
        ItemStack itemstack2 = ItemStack.EMPTY;
        if (!itemstack.isEmpty()) {
            itemstack2 = item.getDefaultInstance();
        }

        if (!ItemStack.matches(itemstack2, this.resultSlot.getItem())) {
            this.resultSlot.set(itemstack2);
        }

    }

    public Slot getFabricSheetSlot() {
        return this.fabricSheetSlot;
    }

    public Slot getSchematicSlot() {
        return this.schematicSlot;
    }

    public Slot getResultSlot() {
        return this.resultSlot;
    }
}