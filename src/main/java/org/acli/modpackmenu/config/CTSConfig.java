package org.acli.modpackmenu.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;

@ConfigFile(name = "modpackmenu/config")
public class CTSConfig {
	public boolean customEditionEnabled = false;
	public String customEdition = "";
	public boolean customSplashEnabled = false;
	public boolean removeSplash = false;
	public String[] customSplash = new String[0];
	public boolean removeMinecraftLogo = false;
}
