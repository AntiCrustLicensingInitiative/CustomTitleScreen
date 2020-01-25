package com.martmists.customtitlescreen.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;

import java.util.HashMap;

@ConfigFile(name = "cts/buttons")
public class ButtonConfig {
	public HashMap<String, SingleButtonConfig> buttons = new HashMap<>();
}
