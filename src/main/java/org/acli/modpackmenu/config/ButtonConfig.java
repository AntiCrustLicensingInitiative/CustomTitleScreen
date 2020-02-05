package org.acli.modpackmenu.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;

import java.util.HashMap;

@ConfigFile(name = "modpackmenu/buttons")
public class ButtonConfig {
	public HashMap<String, SingleButtonConfig> buttons = new HashMap<>();
}
