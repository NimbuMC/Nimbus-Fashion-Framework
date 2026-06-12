package net.nimbu.clothing.mixin;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(
            method = "setModelProperties",
            at = @At("TAIL"),
            cancellable = true
    )
    private void setModelProperties(
            AbstractClientPlayer clientPlayer,
            CallbackInfo ci
    ) {
        PlayerModel<AbstractClientPlayer> model =
                ((PlayerRenderer)(Object)this).getModel();

        model.leftArm.visible=false;
        model.rightArm.visible=false;
        model.leftSleeve.visible=false;
        model.rightSleeve.visible=false;
        model.jacket.visible=false;
    }
}
