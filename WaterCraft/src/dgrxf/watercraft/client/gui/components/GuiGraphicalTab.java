package dgrxf.watercraft.client.gui.components;

import dgrxf.watercraft.util.Rectangle;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * GraphicalGuiTab
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public abstract class GuiGraphicalTab extends GuiGraphicsRectangle{

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param inv
	 * @param exemptAreas
	 */
	public GuiGraphicalTab(int x, int y, int w, int h, InventoryPlayer inv, Rectangle... exemptAreas) {
		super(x, y, w, h, inv, exemptAreas);
	}

}
