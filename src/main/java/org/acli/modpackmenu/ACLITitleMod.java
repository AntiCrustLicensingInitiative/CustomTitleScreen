package org.acli.modpackmenu;

import org.acli.modpackmenu.config.ButtonConfig;
import org.acli.modpackmenu.config.CTSConfig;
import io.github.cottonmc.cotton.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.ArrayList;
import java.util.IdentityHashMap;

public class ACLITitleMod implements ClientModInitializer {
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
		ACLITitleMod.allButtons = new ArrayList<>();
		ACLITitleMod.buttonCache = new IdentityHashMap<>();
		ACLITitleMod.config = ConfigManager.loadConfig(CTSConfig.class);
		ACLITitleMod.buttonConfig = ConfigManager.loadConfig(ButtonConfig.class);
	}
}
