package org.acli.modpackmenu.mixin;

import com.mojang.blaze3d.systems.RenderSystem;

import org.acli.modpackmenu.ACLITitleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    protected MinecraftClient minecraft;
    
    @Inject(method = "renderDirtBackground(I)V", at = @At(value = "INVOKE", target = "net/minecraft/client/render/BufferBuilder.begin(ILnet/minecraft/client/render/VertexFormat;)V"))
    private void changeDirtBackground(CallbackInfo info) {
        this.minecraft.getTextureManager().bindTexture(new Identifier(ACLITitleMod.config.backgroundTextureIdentifier));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}