package org.acli.modpackmenu;

import org.acli.modpackmenu.config.ButtonConfig;
import org.acli.modpackmenu.config.CTSConfig;
import io.github.cottonmc.cotton.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.ArrayList;
import java.util.IdentityHashMap;

public class ModpackMenuMod implements ClientModInitializer {
	public static CTSConfig config;
	public static ButtonConfig buttonConfig;
	public static int screenWidth;
	public static int screenHeight;
	public static IdentityHashMap<AbstractButtonWidget, String> buttonCache = new IdentityHashMap<>();
	public static ArrayList<DrawableHelper> allButtons = new ArrayList<>();

	@Override
	public void onInitializeClient() {
		loadConfig();
	}

	public static void loadConfig() {
		ModpackMenuMod.allButtons = new ArrayList<>();
		ModpackMenuMod.buttonCache = new IdentityHashMap<>();
		ModpackMenuMod.config = ConfigManager.loadConfig(CTSConfig.class);
		ModpackMenuMod.buttonConfig = ConfigManager.loadConfig(ButtonConfig.class);
	}
}
