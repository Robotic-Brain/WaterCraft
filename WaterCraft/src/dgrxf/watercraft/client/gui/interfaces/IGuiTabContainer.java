package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.gui.FontRenderer;
import dgrxf.watercraft.client.gui.components.GuiTab;

/**
 * IGuiTab
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public interface IGuiTabContainer {

	public FontRenderer getFontRenderer();
	public GuiTab getActiveTab();
	
}
