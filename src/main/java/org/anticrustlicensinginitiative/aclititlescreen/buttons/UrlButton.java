package org.anticrustlicensinginitiative.aclititlescreen.buttons;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.Util;

public class UrlButton extends ButtonWidget {
	public UrlButton(int i, int j, int k, int l, String string, String url) {
		super(i, j, k, l, string, (bw)-> Util.getOperatingSystem().open(url));
	}
}
