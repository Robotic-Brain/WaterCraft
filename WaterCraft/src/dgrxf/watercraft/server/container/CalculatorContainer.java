package dgrxf.watercraft.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * 
 * CalculatorContainer
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class CalculatorContainer extends Container {
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
    
}
