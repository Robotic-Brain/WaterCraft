package dgrxf.watercraft.client.gui.boat;

import net.minecraft.inventory.IInventory;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.common.container.BoatContainer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

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
