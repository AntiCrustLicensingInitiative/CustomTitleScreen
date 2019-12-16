package com.martmists.customtitlescreen.mixin;

import com.martmists.customtitlescreen.CustomTitleScreenMod;
import com.martmists.customtitlescreen.buttons.LanguagesButton;
import com.martmists.customtitlescreen.config.SingleButtonConfig;
import com.martmists.customtitlescreen.buttons.UrlButton;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.util.Random;

@Mixin(TitleScreen.class)
class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text text) {
        super(text);
    }

    @ModifyArg(method="render(IIF)V",
               at=@At(value="INVOKE",
                      target="Lnet/minecraft/client/gui/screen/TitleScreen;drawString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                      ordinal = 0),
               index=1)
    public String modifyEdition(TextRenderer textRenderer, String version, int x, int y, int color){
        if (CustomTitleScreenMod.config.customEditionEnabled) {
            this.drawString(textRenderer, CustomTitleScreenMod.config.customEdition, x, y-10, color);
        }
        return version;
    }

    @ModifyArg(method="render(IIF)V",
               at=@At(value="INVOKE",
                      target="Lnet/minecraft/client/gui/screen/TitleScreen;drawCenteredString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                      ordinal = 0
               ),
               index=1)
    public String modifySplash(String splash){
        if (CustomTitleScreenMod.config.removeSplash)
            return "";
        String[] splashes = CustomTitleScreenMod.config.customSplash;
        if (CustomTitleScreenMod.config.customSplashEnabled && splashes.length > 0) {
            Random r = new Random();
            r.setSeed(splash.length() + splash.codePointAt(0));
            return splashes[r.nextInt(splashes.length)];
        } else {
            return splash;
        }
    }

    @Inject(method="init()V", at=@At("HEAD"))
    public void postInit(CallbackInfo ci){
        this.minecraft.options.realmsNotifications = false;
        CustomTitleScreenMod.screenWidth = width;
        CustomTitleScreenMod.screenHeight = height;
    }
    @Override
    public void setSize(int a, int b){
        super.setSize(a, b);
        CustomTitleScreenMod.screenWidth = width;
        CustomTitleScreenMod.screenHeight = height;
    }

    @Inject(method="init()V", at=@At("RETURN"))
    public void renderCustomButtons(CallbackInfo ci){
        CustomTitleScreenMod.buttonConfig.buttons.forEach((String key, SingleButtonConfig value) ->{
            switch (value.type) {
                case "modify": {
                    break;
                }
                case "modifyTextured": {
                    // Hardcoded for Languages right now
                    int index;
                    switch (key) {
                        case "minecraft.language": {
                            index = 3;
                            break;
                        }
                        case "minecraft.accessibility": {
                            index = 5;
                        }
                        default: {
                            index = 0;
                        }
                    }

                    if (!(this.buttons.get(3) instanceof TexturedButtonWidget)){
                        index += 1;
                    }
                    TexturedButtonWidget old = (TexturedButtonWidget) this.buttons.get(index);
                    LanguagesButton button = new LanguagesButton(old, CustomTitleScreenMod.screenWidth / 2 + value.x, CustomTitleScreenMod.screenHeight / 4 + 48 + value.y, value.width, value.height, value.text);
                    this.buttons.remove(index);
                    this.children.remove(index);
                    this.addButton(button);
                    CustomTitleScreenMod.buttonCache.put(button, key);
                    break;
                }
                case "url": {
                    UrlButton button = new UrlButton(CustomTitleScreenMod.screenWidth / 2 + value.x, CustomTitleScreenMod.screenHeight / 4 + 48 + value.y, value.width, value.height, value.text, value.parameter);
                    this.addButton(button);
                    CustomTitleScreenMod.buttonCache.put(button, key);
                    break;
                }
                default: {
                    System.out.println("[CMM] Error: Unknown button type: " + value.type);
                }
            }
        });
    }

    @ModifyArg(
            method="render(IIF)V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V",
                    ordinal = 0
            ),
            index=0)
    public Identifier modifyBackground(Identifier id) throws IOException {
        // We use ModifyArg since I couldn't find a different way to inject at this method call
        // while having access to the Identifier to register a custom texture.

        File f = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("cts/background.png").toFile();
        if (f.exists()) {
            TextureManager t = this.minecraft.getTextureManager();
            if (!(t.getTexture(id) instanceof NativeImageBackedTexture)) {
                t.registerTexture(id,
                        new NativeImageBackedTexture(NativeImage.read(new FileInputStream(f)))
                );
            }
        }
        return id;
    }

    @ModifyArg(
            method="render(IIF)V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V",
                    ordinal = 1
            ),
            index=0)
    public Identifier modifyTitle(Identifier id) {
        if (CustomTitleScreenMod.config.removeMinecraftLogo) {
            return new Identifier("customtitlescreen:textures/blank.png");
        } else {
            return id;
        }
    }

    @ModifyArg(
            method="render(IIF)V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V",
                    ordinal = 2
            ),
            index=0)
    public Identifier modifySubtitle(Identifier id) {
        if (CustomTitleScreenMod.config.removeMinecraftLogo) {
            return new Identifier("customtitlescreen:textures/blank.png");
        } else {
            return id;
        }
    }
}