package org.acli.modpackmenu.buttons;

import org.acli.modpackmenu.ModpackMenuMod;
import net.minecraft.client.gui.widget.ButtonWidget;

public class RefreshButton extends ButtonWidget {
	public RefreshButton(int i, int j, int k, int l, String string) {
		super(i, j, k, l, string, (ButtonWidget x)->ModpackMenuMod.loadConfig());
	}
}
