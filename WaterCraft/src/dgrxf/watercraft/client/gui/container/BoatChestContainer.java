package dgrxf.watercraft.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import dgrxf.watercraft.entity.boat.ChestBoat;

public class BoatChestContainer extends Container {
    public BoatChestContainer(InventoryPlayer inv, ChestBoat chest) {
        
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inv, i, 8 + 18 * i, 194));
        }
        
        for (int i = 0; i < 27; i++) {
            addSlotToContainer(new Slot(inv, i + 9, 8 + 18 * (i % 9), 136 + 18 * (i / 9)));
        }
        
        for (int i = 0; i < 27; i++) {
            addSlotToContainer(new Slot(chest, i + 9, 8 + 18 * (i % 9), 122 + 18 * (i / 9)));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
