package org.acli.modpackmenu.mixin;

import com.google.common.collect.Lists;
import org.acli.modpackmenu.ModpackMenuMod;
import org.acli.modpackmenu.config.SingleButtonConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.stb.STBTTFontinfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@Mixin(AbstractButtonWidget.class)
public class AbstractButtonWidgetMixin extends DrawableHelper {
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

	@ModifyVariable(method = "renderButton(IIF)V", at = @At(value = "STORE", ordinal = 0))
	private TextRenderer modifyTextRenderer(TextRenderer renderer) {
		if (!(MinecraftClient.getInstance().currentScreen instanceof TitleScreen))
			return renderer;
		if (!ModpackMenuMod.allButtons.contains(this))
			return renderer;
		File f = new File(FabricLoader.getInstance().getConfigDirectory().toString() + "/modpackmenu/custom_font.ttf");
		if (!f.exists())
			return renderer;
		Identifier id = new Identifier("cts:font");
		TextRenderer render = MinecraftClient.getInstance().getFontManager().getTextRenderer(id);
		assert render != null;
		ByteBuffer byteBuffer;
		try {
			byteBuffer = TextureUtil.readResource(new FileInputStream(f));
			render.setFonts(Lists.newArrayList(new Font[]{
					new TrueTypeFont(byteBuffer, STBTTFontinfo.malloc(), 11.0f, 1.0f, 0f, 0f, "")
			}));
			return render;
		} catch (IOException e) {
			return renderer;
		}
	}

	@ModifyArg(
			method = "renderButton(IIF)V",
			at = @At(
					value="INVOKE",
					target="Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V",
					ordinal = 0
			),
			index = 0)
	private Identifier modifyButtonImages(Identifier id) throws IOException {
		File f = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("modpackmenu/buttons.png").toFile();
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

	@Inject(method="<init>(IIIILjava/lang/String;)V", at = @At(value="RETURN"))
	private void modifyButtonLocations(int x, int y, int width, int height, String message, CallbackInfo ci) {
		if (!(MinecraftClient.getInstance().currentScreen instanceof TitleScreen))
			return;
		if (!ModpackMenuMod.allButtons.contains(this)) {
			ModpackMenuMod.allButtons.add(this);
		}
		int i = ModpackMenuMod.screenWidth / 2 - 100;
		int j = ModpackMenuMod.screenHeight / 4 + 48;
		int xScale = 12;
		int yScale = 24;

		if (ModpackMenuMod.buttonCache.containsKey(this)) {
			this.x = ModpackMenuMod.screenWidth / 2 + this.x;
			this.y = this.y - (360 - ModpackMenuMod.screenHeight);
			SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get(ModpackMenuMod.buttonCache.get(this));
			if (!config.text.equals("<default>"))
				this.message = config.text;
		} else if (x == i) {
			if (y == j) {
				// Singleplayer
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("minecraft.singleplayer");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			} else if (y == j+yScale) {
				// Multiplayer
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("minecraft.multiplayer");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			} else if (y == j+yScale*2) {
				// Realms
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("minecraft.realms");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			} else if (y == j+yScale*3) {
				// Mod menu
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("modmenu.modlist");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			} else if (y == j+yScale*3.5) {
				// Options
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("minecraft.options");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			}
		} else if (y == j+yScale*3.5) {
			if (x == i - 2*xScale) {
				// Language
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("minecraft.language");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			} else if (x == i + xScale*8.5) {
				// Quit
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("minecraft.quit");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			} else if (x == i + xScale*17) {
				// Accessibility
				SingleButtonConfig config = ModpackMenuMod.buttonConfig.buttons.get("minecraft.accessibility");
				if (config == null) return;
				this.x = ModpackMenuMod.screenWidth / 2 + config.x;
				this.y = config.y - (360 - ModpackMenuMod.screenHeight);
				this.width = config.width;
				this.height = config.height;
				if (!config.text.equals("<default>"))
					this.message = config.text;
			}
		}
	}
}
