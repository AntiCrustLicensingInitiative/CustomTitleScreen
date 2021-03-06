package org.acli.modpackmenu.mixin;

import org.acli.modpackmenu.ModpackMenuMod;
import org.acli.modpackmenu.buttons.LanguagesButton;
import org.acli.modpackmenu.buttons.RefreshButton;
import org.acli.modpackmenu.config.SingleButtonConfig;
import org.acli.modpackmenu.buttons.UrlButton;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.util.Random;

@Mixin(value = TitleScreen.class, priority = 1001)
class TitleScreenMixin extends Screen {
	@Shadow
	private final boolean isMinceraft;

	protected TitleScreenMixin(Text text) {
		super(text);
		this.isMinceraft = (double)(new Random()).nextFloat() < 1.0E-4D;
	}

	@ModifyArg(method="render(IIF)V",
			   at=@At(value="INVOKE",
					  target="Lnet/minecraft/client/gui/screen/TitleScreen;drawString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
					  ordinal = 0),
			   index=1)
	private String modifyEdition(TextRenderer textRenderer, String version, int x, int y, int color) {
		if (ModpackMenuMod.config.customEditionEnabled) {
			this.drawString(textRenderer, ModpackMenuMod.config.customEdition, x, y-10, color);
		}
		return version;
	}

	@ModifyArg(method="render(IIF)V",
			   at=@At(value="INVOKE",
					  target="Lnet/minecraft/client/gui/screen/TitleScreen;drawCenteredString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
					  ordinal = 0
			   ),
			   index=1)
	private String modifySplash(String splash) {
		if (ModpackMenuMod.config.removeSplash)
			return "";
		String[] splashes = ModpackMenuMod.config.customSplash;
		if (ModpackMenuMod.config.customSplashEnabled && splashes.length > 0) {
			Random r = new Random();
			r.setSeed(splash.length() + splash.codePointAt(0));
			return splashes[r.nextInt(splashes.length)];
		} else {
			return splash;
		}
	}

	@Inject(method="init()V", at=@At("HEAD"))
	private void postInit(CallbackInfo ci) {
		this.minecraft.options.realmsNotifications = false;
		ModpackMenuMod.screenWidth = width;
		ModpackMenuMod.screenHeight = height;
	}

	@Override
	public void setSize(int a, int b) {
		ModpackMenuMod.screenWidth = width;
		ModpackMenuMod.screenHeight = height;
		super.setSize(a, b);
	}

	@Inject(method="init()V", at=@At("RETURN"))
	private void renderCustomButtons(CallbackInfo ci) {
		ModpackMenuMod.buttonConfig.buttons.forEach((String key, SingleButtonConfig value) ->{
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

					if (!(this.buttons.get(3) instanceof TexturedButtonWidget)) {
						index += 1;
					}
					TexturedButtonWidget old = (TexturedButtonWidget) this.buttons.get(index);
					LanguagesButton button = new LanguagesButton(old, ModpackMenuMod.screenWidth / 2 + value.x, value.y - (360 - ModpackMenuMod.screenHeight), value.width, value.height, value.text);
					this.buttons.remove(index);
					this.children.remove(index);
					this.addButton(button);
					ModpackMenuMod.buttonCache.put(button, key);
					break;
				}
				case "url": {
					UrlButton button = new UrlButton(ModpackMenuMod.screenWidth / 2 + value.x, value.y - (360 - ModpackMenuMod.screenHeight), value.width, value.height, value.text, value.parameter);
					this.addButton(button);
					ModpackMenuMod.buttonCache.put(button, key);
					break;
				}
				case "refresh": {
					RefreshButton button = new RefreshButton(ModpackMenuMod.screenWidth / 2 + value.x, value.y - (360 - ModpackMenuMod.screenHeight), value.width, value.height, value.text);
					this.addButton(button);
					ModpackMenuMod.buttonCache.put(button, key);
					break;
				}
				default: {
					System.out.println("[MM] Error: Unknown button type: " + value.type);
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
	private Identifier modifyBackground(Identifier id) throws IOException {
		// We use ModifyArg since I couldn't find a different way to inject at this method call
		// while having access to the Identifier to register a custom texture.

		File f = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("modpackmenu/background.png").toFile();
		if (f.exists()) {
			TextureManager t = this.minecraft.getTextureManager();
			if (!(t.getTexture(id) instanceof NativeImageBackedTexture)) {
				if (this.isMinceraft) {
					File minceF = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("modpackmenu/backgorund.png").toFile();
					if (minceF.exists()) {
						NativeImageBackedTexture backgorundT = new NativeImageBackedTexture(NativeImage.read(new FileInputStream(minceF)));
						t.registerTexture(id, backgorundT);
						return id;
					}
				}
				
				NativeImageBackedTexture backgroundT = new NativeImageBackedTexture(NativeImage.read(new FileInputStream(f)));
				t.registerTexture(id, backgroundT);
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
	private Identifier modifyTitle(Identifier id) {
		if (ModpackMenuMod.config.removeMinecraftLogo) {
			return new Identifier("modpackmenu:textures/blank.png");
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
	private Identifier modifySubtitle(Identifier id) {
		if (ModpackMenuMod.config.removeMinecraftLogo) {
			return new Identifier("modpackmenu:textures/blank.png");
		} else {
			return id;
		}
	}
}