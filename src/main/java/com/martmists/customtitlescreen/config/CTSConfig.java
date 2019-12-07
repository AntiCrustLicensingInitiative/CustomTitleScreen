package com.martmists.customtitlescreen.config;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;

@ConfigFile(name = "cts/config")
public class CTSConfig {
    public boolean customEditionEnabled = false;
    public String customEdition = "";
    public boolean customSplashEnabled = false;
    public boolean removeSplash = false;
    public String[] customSplash = new String[0];
    public boolean removeMinecraftLogo = false;
}
