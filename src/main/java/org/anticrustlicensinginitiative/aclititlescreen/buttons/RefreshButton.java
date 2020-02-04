package org.anticrustlicensinginitiative.aclititlescreen.buttons;

import org.anticrustlicensinginitiative.aclititlescreen.ACLITitleMod;
import net.minecraft.client.gui.widget.ButtonWidget;

public class RefreshButton extends ButtonWidget {
	public RefreshButton(int i, int j, int k, int l, String string) {
		super(i, j, k, l, string, (ButtonWidget x)->ACLITitleMod.loadConfig());
	}
}
