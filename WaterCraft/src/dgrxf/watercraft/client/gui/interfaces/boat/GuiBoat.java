package dgrxf.watercraft.client.gui.interfaces.boat;

import net.minecraft.inventory.IInventory;
import dgrxf.watercraft.client.gui.interfaces.GuiBase;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.server.container.BoatContainer;

/**
 * 
 * GuiBoat
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiBoat extends GuiBase{

	public GuiBoat(IInventory player, AbstractBaseBoat boat) {
		super(new BoatContainer(player, boat));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		
	}
}
