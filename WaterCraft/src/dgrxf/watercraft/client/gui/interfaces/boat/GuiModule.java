package dgrxf.watercraft.client.gui.interfaces.boat;

/**
 * 
 * GuiModule
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public abstract class GuiModule {
	
	protected abstract void drawGuiContainerBackgroundLayer(float f, int x, int y);
	
	protected abstract void drawGuiContainerForegroundLayer(int x, int y);
}
