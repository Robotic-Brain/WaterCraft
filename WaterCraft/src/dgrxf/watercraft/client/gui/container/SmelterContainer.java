package dgrxf.watercraft.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.tileentity.WCTileEntitySmelter;

public class SmelterContainer extends Container {
    
    private WCTileEntitySmelter smelter;
    
    public SmelterContainer(InventoryPlayer inv, WCTileEntitySmelter smelter) {
        this.smelter = smelter;
        
        // hotbar
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inv, i, 8 + 18 * i, 194));
        }
        
        // inventory
        for (int i = 0; i < 27; i++) {
            addSlotToContainer(new Slot(inv, i + 9, 8 + 18 * (i % 9), 136 + 18 * (i / 9)));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        // TODO
        return false;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        // TODO
        return null;
    }
    
    @Override
    public void addCraftingToCrafters(ICrafting player) {
        super.addCraftingToCrafters(player);
        // TODO
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        // TODO
    }
    
    @Override
    public void detectAndSendChanges() {
        // TODO
        super.detectAndSendChanges();
    }
    
    public WCTileEntitySmelter getTileEntity() {
        return smelter;
    }
    
}
