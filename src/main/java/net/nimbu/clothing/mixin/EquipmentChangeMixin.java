package net.nimbu.clothing.mixin;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.nimbu.clothing.model.SkinUpdateHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(LivingEntity.class)
public class EquipmentChangeMixin {

    @Inject(method = "setItemSlot", at = @At("RETURN"))
    private void onEquip(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) throws IOException {
        if (!((Object)this instanceof AbstractClientPlayer player)) return;
        if (!player.level().isClientSide) return;

        SkinUpdateHandler.onEquipmentChanged(player);
    }
}