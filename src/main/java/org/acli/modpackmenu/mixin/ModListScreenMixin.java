package org.acli.modpackmenu.mixin;

import io.github.prospector.modmenu.gui.ModListScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import com.mojang.blaze3d.systems.RenderSystem;

import org.acli.modpackmenu.ModpackMenuMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModListScreen.class)
public class ModListScreenMixin {
    @Inject(method = "overlayBackground(IIIIIIIIII)V", at = @At(value = "INVOKE", target = "net/minecraft/client/render/BufferBuilder.begin(ILnet/minecraft/client/render/VertexFormat;)V"))
    private static void changeBackDirtBackground(CallbackInfo info) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier(ModpackMenuMod.config.backgroundTextureIdentifier));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}