package dgrxf.watercraft.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;

/**
 * 
 * FreezerContainer
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class FreezerContainer extends Container {
    WCTileEntityFreezer freezer;
    
    public FreezerContainer(WCTileEntityFreezer te) {
        freezer = te;
    }
    
    public WCTileEntityFreezer getTileEntity() {
        return freezer;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
    
}
