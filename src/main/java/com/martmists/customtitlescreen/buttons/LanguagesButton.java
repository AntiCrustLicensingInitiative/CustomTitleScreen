package com.martmists.customtitlescreen.buttons;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

public class LanguagesButton extends ButtonWidget {
	public LanguagesButton(TexturedButtonWidget btn, int x, int y, int width, int height, String text) {
		super(x, y, width, height, text, (p)->btn.onPress());
		btn.visible = false;
		btn.active = false;
	}
}
