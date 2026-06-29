package net.nimbu.clothing.screen.custom;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.nimbu.clothing.block.ModBlocks;
import net.nimbu.clothing.item.ModItems;
import net.nimbu.clothing.item.custom.SchematicItem;
import net.nimbu.clothing.screen.ModMenuTypes;

import java.util.List;

public class TailoringMenu extends AbstractContainerMenu {
    private static final int PATTERN_NOT_SET = -1;
    private static final int INV_SLOT_START = 4;
    private static final int INV_SLOT_END = 31;
    private static final int USE_ROW_SLOT_START = 31;
    private static final int USE_ROW_SLOT_END = 40;
    private final ContainerLevelAccess access;
//    final DataSlot selectedBannerPatternIndex;
    private List<Holder<BannerPattern>> selectablePatterns;
    Runnable slotUpdateListener;
//    private final HolderGetter<BannerPattern> patternGetter;
    private final Slot fabricSheetSlot;
    private final Slot schematicSlot;
//    private final Slot resultSlot;
    long lastSoundTime;
    private final Container inputContainer;
    private final Container outputContainer;

    public TailoringMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public TailoringMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(ModMenuTypes.TAILORING.get(), containerId);
//        this.selectedBannerPatternIndex = DataSlot.standalone();
//        this.selectablePatterns = List.of();
//        this.slotUpdateListener = () -> {
//        };
        this.inputContainer = new SimpleContainer(3) {
//            public void setChanged() {
//                super.setChanged();
//                TailoringMenu.this.slotsChanged(this);
//                TailoringMenu.this.slotUpdateListener.run();
//            }
        };
        this.outputContainer = new SimpleContainer(1) {
//            public void setChanged() {
//                super.setChanged();
//                TailoringMenu.this.slotUpdateListener.run();
//            }
        };
        this.access = access;
        this.fabricSheetSlot = this.addSlot(new Slot(this.inputContainer, 0, 13, 25) {
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.FABRIC_SHEET.get());
            }
        });
        this.schematicSlot = this.addSlot(new Slot(this.inputContainer, 2, 13, 45) {
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof SchematicItem;
            }
        });
//        this.resultSlot = this.addSlot(new Slot(this.outputContainer, 0, 143, 57) {
//            public boolean mayPlace(ItemStack stack) {
//                return false;
//            }
//
////            public void onTake(Player player, ItemStack stack) {
////                TailoringMenu.this.fabricSheetSlot.remove(1);
////                if (!TailoringMenu.this.fabricSheetSlot.hasItem()) {
////                    TailoringMenu.this.selectedBannerPatternIndex.set(-1);
////                }
////
////                access.execute((p_39952_, p_39953_) -> {
////                    long l = p_39952_.getGameTime();
////                    if (TailoringMenu.this.lastSoundTime != l) {
////                        p_39952_.playSound((Player)null, p_39953_, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
////                        TailoringMenu.this.lastSoundTime = l;
////                    }
////
////                });
////                super.onTake(player, stack);
////            }
//        });


        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        //this.addDataSlot(this.selectedBannerPatternIndex);
        //this.patternGetter = playerInventory.player.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.TAILORING_TABLE.get());
    }

//    @Override
//    public boolean clickMenuButton(Player player, int id) {
//        if (id >= 0 && id < this.selectablePatterns.size()) {
//            this.selectedBannerPatternIndex.set(id);
//            this.setupResultSlot((Holder)this.selectablePatterns.get(id));
//            return true;
//        } else {
//            return false;
//        }
//    }


//    private List<Holder<BannerPattern>> getSelectablePatterns(ItemStack stack) {
//        if (stack.isEmpty()) {
//            return (List)this.patternGetter.get(BannerPatternTags.NO_ITEM_REQUIRED).map(ImmutableList::copyOf).orElse(ImmutableList.of());
//        } else {
//            Item var3 = stack.getItem();
//            List var10000;
//            if (var3 instanceof BannerPatternItem) {
//                BannerPatternItem bannerpatternitem = (BannerPatternItem)var3;
//                var10000 = (List)this.patternGetter.get(bannerpatternitem.getBannerPattern()).map(ImmutableList::copyOf).orElse(ImmutableList.of());
//            } else {
//                var10000 = List.of();
//            }
//
//            return var10000;
//        }
//    }

//    private boolean isValidPatternIndex(int index) {
//        return index >= 0 && index < this.selectablePatterns.size();
//    }

    public void slotsChanged(Container inventory) {
//        ItemStack itemstack = this.fabricSheetSlot.getItem();
//        ItemStack itemstack2 = this.patternSlot.getItem();
//        if (!itemstack.isEmpty()) {
//            int i = this.selectedBannerPatternIndex.get();
//            boolean flag = this.isValidPatternIndex(i);
//            List<Holder<BannerPattern>> list = this.selectablePatterns;
//            this.selectablePatterns = this.getSelectablePatterns(itemstack2);
//            Holder holder;
//            if (this.selectablePatterns.size() == 1) {
//                this.selectedBannerPatternIndex.set(0);
//                holder = (Holder)this.selectablePatterns.get(0);
//            } else if (!flag) {
//                this.selectedBannerPatternIndex.set(-1);
//                holder = null;
//            } else {
//                Holder<BannerPattern> holder1 = (Holder)list.get(i);
//                int j = this.selectablePatterns.indexOf(holder1);
//                if (j != -1) {
//                    holder = holder1;
//                    this.selectedBannerPatternIndex.set(j);
//                } else {
//                    holder = null;
//                    this.selectedBannerPatternIndex.set(-1);
//                }
//            }
//
//            if (holder != null) {
//                BannerPatternLayers bannerpatternlayers = (BannerPatternLayers)itemstack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
//                boolean flag1 = bannerpatternlayers.layers().size() >= 6;
//                if (flag1) {
//                    this.selectedBannerPatternIndex.set(-1);
//                    this.resultSlot.set(ItemStack.EMPTY);
//                } else {
//                    this.setupResultSlot(holder);
//                }
//            } else {
//                this.resultSlot.set(ItemStack.EMPTY);
//            }
//
//            this.broadcastChanges();
//        } else {
//            this.resultSlot.set(ItemStack.EMPTY);
//            this.selectablePatterns = List.of();
//            this.selectedBannerPatternIndex.set(-1);
//        }

    }

//    public List<Holder<BannerPattern>> getSelectablePatterns() {
//        return this.selectablePatterns;
//    }
//
//    public int getSelectedBannerPatternIndex() {
//        return this.selectedBannerPatternIndex.get();
//    }
//
//    public void registerUpdateListener(Runnable listener) {
//        this.slotUpdateListener = listener;
//    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = (Slot)this.slots.get(index);
//        if (slot != null && slot.hasItem()) {
//            ItemStack itemstack1 = slot.getItem();
//            itemstack = itemstack1.copy();
//            if (index == this.resultSlot.index) {
//                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
//                    return ItemStack.EMPTY;
//                }
//
//                slot.onQuickCraft(itemstack1, itemstack);
//            } else if (index != this.fabricSheetSlot.index && index != this.patternSlot.index) {
//                if (itemstack1.getItem() instanceof BannerItem) {
//                    if (!this.moveItemStackTo(itemstack1, this.fabricSheetSlot.index, this.fabricSheetSlot.index + 1, false)) {
//                        return ItemStack.EMPTY;
//                    }
//                } else if (itemstack1.getItem() instanceof BannerPatternItem) {
//                    if (!this.moveItemStackTo(itemstack1, this.patternSlot.index, this.patternSlot.index + 1, false)) {
//                        return ItemStack.EMPTY;
//                    }
//                } else if (index >= 4 && index < 31) {
//                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
//                        return ItemStack.EMPTY;
//                    }
//                } else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty()) {
//                slot.setByPlayer(ItemStack.EMPTY);
//            } else {
//                slot.setChanged();
//            }
//
//            if (itemstack1.getCount() == itemstack.getCount()) {
//                return ItemStack.EMPTY;
//            }
//
//            slot.onTake(player, itemstack1);
//        }

        return itemstack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((p_39871_, p_39872_) -> {
            this.clearContainer(player, this.inputContainer);
        });
    }

    private void setupResultSlot(Holder<BannerPattern> pattern) {
//        ItemStack itemstack = this.fabricSheetSlot.getItem();
//        ItemStack itemstack2 = ItemStack.EMPTY;
//        if (!itemstack.isEmpty()) {
//            itemstack2 = itemstack.copyWithCount(1);
//            itemstack2.update(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY, (p_330070_) -> {
//                return (new BannerPatternLayers.Builder()).addAll(p_330070_).add(pattern, dyecolor).build();
//            });
//        }
//
//        if (!ItemStack.matches(itemstack2, this.resultSlot.getItem())) {
//            this.resultSlot.set(itemstack2);
//        }

    }

    public Slot getFabricSheetSlot() {
        return this.fabricSheetSlot;
    }

    public Slot getSchematicSlot() {
        return this.schematicSlot;
    }
//
//    public Slot getResultSlot() {
//        return this.resultSlot;
//    }
}