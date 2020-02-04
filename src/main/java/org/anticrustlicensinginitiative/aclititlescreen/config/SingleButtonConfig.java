package org.anticrustlicensinginitiative.aclititlescreen.config;

import blue.endless.jankson.Comment;

public class SingleButtonConfig {
	@Comment("X offset from center")
	public int x = 0;
	@Comment("Y offset from top")
	public int y = 0;
	public int width = 0;
	public int height = 0;
	public String text = "<default>";
	public String type = "override";
	public String parameter = "";
	@Comment("Not currently implemented")
	public String textureName = "";
}
