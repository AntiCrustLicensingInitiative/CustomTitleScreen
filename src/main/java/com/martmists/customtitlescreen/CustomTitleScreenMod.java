package com.martmists.customtitlescreen;

import com.martmists.customtitlescreen.config.ButtonConfig;
import com.martmists.customtitlescreen.config.CTSConfig;
import io.github.cottonmc.cotton.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.ArrayList;
import java.util.IdentityHashMap;

public class CustomTitleScreenMod implements ClientModInitializer {
    public static CTSConfig config;
    public static ButtonConfig buttonConfig;
    public static int screenWidth;
    public static int screenHeight;
    public static IdentityHashMap<AbstractButtonWidget, String> buttonCache = new IdentityHashMap<>();
    public static ArrayList<DrawableHelper> allButtons = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        loadConfig();
        System.out.println("CustomMainMenu initialized!");
    }

    public static void loadConfig(){
        CustomTitleScreenMod.allButtons = new ArrayList<>();
        CustomTitleScreenMod.buttonCache = new IdentityHashMap<>();
        CustomTitleScreenMod.config = ConfigManager.loadConfig(CTSConfig.class);
        CustomTitleScreenMod.buttonConfig = ConfigManager.loadConfig(ButtonConfig.class);
    }
}
