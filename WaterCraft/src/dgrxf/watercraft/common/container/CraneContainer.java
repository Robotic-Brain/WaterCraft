package dgrxf.watercraft.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;

/**
 * 
 * CraneContainer
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class CraneContainer extends Container implements ITeContainer {
    
    private WCTileEntityCrane unit;
    
    public CraneContainer(InventoryPlayer invPlayer, WCTileEntityCrane te) {
        unit = te;
        
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(invPlayer, x, 28 + 18 * x, 131));
        }
        
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 28 + 18 * x, 73 + y * 18));
            }
        }
        
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return unit.isUseableByPlayer(entityplayer);
    }
    
    @Override
    public TileEntity getTileEntity() {
        return unit;
    }
    
    @Override
    public void addCraftingToCrafters(ICrafting player) {
        super.addCraftingToCrafters(player);
        player.sendProgressBarUpdate(this, 0, unit.activeTabIndex);
    }
    
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            unit.activeTabIndex = data;
        }
    }
    
    private int oldTabIndex;
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        
        for (Object player : crafters) {
            if (unit.activeTabIndex != oldTabIndex) {
                ((ICrafting) player).sendProgressBarUpdate(this, 0, unit.activeTabIndex);
            }
        }
        
        oldTabIndex = unit.activeTabIndex;
    }
    
}
