package net.nimbu.fashionframework.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.nimbu.fashionframework.screen.custom.TailoringMenu;

public class TailoringTableBlock extends CraftingTableBlock {
    public static final MapCodec<TailoringTableBlock> CODEC = simpleCodec(TailoringTableBlock::new);
    private static final Component CONTAINER_TITLE = Component.translatable("container.tailor"); //what

    public MapCodec<TailoringTableBlock> codec() {
        return CODEC;
    }

    public TailoringTableBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player) -> {
            return new TailoringMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos));
        }, CONTAINER_TITLE);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            //player.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
            return InteractionResult.CONSUME;
        }
    }
}
