package com.martmists.customtitlescreen.mixin;

import com.martmists.customtitlescreen.CustomTitleScreenMod;
import com.martmists.customtitlescreen.config.SingleButtonConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Mixin(AbstractButtonWidget.class)
public class AbstractButtonWidgetMixin {
    @Shadow
    int x;
    @Shadow
    int y;
    @Shadow
    int width;
    @Shadow
    int height;
    @Shadow
    String message;

    @ModifyArg(
            method="renderButton(IIF)V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V",
                    ordinal = 0
            ),
            index=0)
    public Identifier modifyButtons(Identifier id) throws IOException {
        File f = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("cmm/buttons.png").toFile();
        if (f.exists()) {
            TextureManager t = MinecraftClient.getInstance().getTextureManager();
            if (!(t.getTexture(id) instanceof NativeImageBackedTexture)) {
                t.registerTexture(id,
                        new NativeImageBackedTexture(NativeImage.read(new FileInputStream(f)))
                );
            }
        }
        return id;
    }

    @Inject(method="<init>(IIIILjava/lang/String;)V",
            at=@At(value="RETURN"))
    public void getX(int x, int y, int width, int height, String message, CallbackInfo ci){
        int i = CustomTitleScreenMod.screenWidth / 2 - 100;
        int j = CustomTitleScreenMod.screenHeight / 4 + 48;
        int xScale = 12;
        int yScale = 24;

        if (CustomTitleScreenMod.buttonCache.containsKey(this)){
            this.x = CustomTitleScreenMod.screenWidth / 2 + this.x;
            this.y = CustomTitleScreenMod.screenHeight / 4 + 48 + this.y;
            SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get(CustomTitleScreenMod.buttonCache.get(this));
            if (!config.text.equals("<default>"))
                this.message = config.text;
        } else if (x == i) {
            if (y == j) {
                // Singleplayer
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("minecraft.singleplayer");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            } else if (y == j+yScale) {
                // Multiplayer
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("minecraft.multiplayer");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            } else if (y == j+yScale*2) {
                // Realms
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("minecraft.realms");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            } else if (y == j+yScale*3) {
                // Mod menu
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("modmenu.modlist");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            } else if (y == j+yScale*3.5) {
                // Options
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("minecraft.options");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            }
        } else if (y == j+yScale*3.5) {
            if (x == i - 2*xScale) {
                // Language
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("minecraft.language");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            } else if (x == i + xScale*8.5) {
                // Quit
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("minecraft.quit");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            } else if (x == i + xScale*17) {
                // Accessibility
                SingleButtonConfig config = CustomTitleScreenMod.buttonConfig.buttons.get("minecraft.accessibility");
                if (config == null) return;
                this.x = CustomTitleScreenMod.screenWidth / 2 + config.x;
                this.y = j + config.y;
                this.width = config.width;
                this.height = config.height;
                if (!config.text.equals("<default>"))
                    this.message = config.text;
            }
        }
    }
}
