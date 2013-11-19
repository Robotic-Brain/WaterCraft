package dgrxf.watercraft.client.gui;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import dgrxf.watercraft.client.gui.container.BoatAssemblerContainer;
import dgrxf.watercraft.client.gui.container.CalculatorContainer;
import dgrxf.watercraft.client.gui.container.ControlUnitContainer;
import dgrxf.watercraft.client.gui.container.FreezerContainer;
import dgrxf.watercraft.client.gui.container.LockAssemblerContainer;
import dgrxf.watercraft.client.gui.container.ToolboxContainer;
import dgrxf.watercraft.client.gui.interfaces.GuiBoatAssembler;
import dgrxf.watercraft.client.gui.interfaces.GuiCalculator;
import dgrxf.watercraft.client.gui.interfaces.GuiFreezer;
import dgrxf.watercraft.client.gui.interfaces.GuiLockAssembler;
import dgrxf.watercraft.client.gui.interfaces.GuiToolBox;
import dgrxf.watercraft.client.gui.interfaces.controlunit.ControlUnitGUI;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

public class GuiHandler implements IGuiHandler {
    
	public static final int VANILLA_CHEST_ID   = -1;
    public static final int TOOLBOX_GUI_ID     = 0;
    public static final int CONTROLUNIT_GUI_ID = 1;
    public static final int CALCULATOR_GUI_ID = 2;
    public static final int FREEZER_GUI_ID = 3;
    public static final int LOCK_ASSEMBLER_GUI_ID = 4;
    public static final int BOAT_ASSEMBLER_BLOCK_GUI_ID = 5;
    public static final int BOAT_ASSEMBLER_ENTITY_GUI_ID = 6;

    
    public GuiHandler() {
    }
    
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        
        TileEntity te = world.getBlockTileEntity(x, y, z);
        
        switch (id) {
            case TOOLBOX_GUI_ID:
                if (te instanceof WCTileEntityToolBox || player.getCurrentEquippedItem().itemID == BlockInfo.TOOLBOX_ID) {
                    return new ToolboxContainer(player.inventory, te instanceof WCTileEntityToolBox ? (WCTileEntityToolBox) te
                            : null);
                }
                break;
            case CONTROLUNIT_GUI_ID:
                if (te instanceof WCTileEntityControlUnitDock) {
                    return new ControlUnitContainer(player.inventory, (WCTileEntityControlUnitDock) te);
                }
                break;
            case CALCULATOR_GUI_ID:
                if (player.inventory.getCurrentItem().getItem().itemID == ModItems.calculator.itemID) {
                    return new CalculatorContainer();
                } else {
                    System.out.println("Item is not valid");
                }
                break;
            case FREEZER_GUI_ID:
            	if (te instanceof WCTileEntityFreezer) {
            		return new FreezerContainer((WCTileEntityFreezer)te);
            	}
            	break;
            case LOCK_ASSEMBLER_GUI_ID:
            	if (te instanceof WCTileEntityLockAssembler) {
            		return new LockAssemblerContainer(player.inventory, (WCTileEntityLockAssembler)te);
            	}
            	break;
            case BOAT_ASSEMBLER_ENTITY_GUI_ID:
            	Entity e = world.getEntityByID(x);
            	if(e instanceof IInventory){
            		return new BoatAssemblerContainer(player.inventory, (IInventory)e);
            	}
            	break;
            case BOAT_ASSEMBLER_BLOCK_GUI_ID:
            	if(te instanceof WCTileEntityBoatAssembler){
            		return new BoatAssemblerContainer(player.inventory, (IInventory)te);
            	}
            	break;
        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        
        TileEntity te = world.getBlockTileEntity(x, y, z);
        switch (id) {
            case TOOLBOX_GUI_ID:
                
                if (te instanceof WCTileEntityToolBox || player.getCurrentEquippedItem().itemID == BlockInfo.TOOLBOX_ID) {
                    Sounds.TOOLBOX_OPENING.play(player.posX, player.posY, player.posZ, 1.0f, 1.0f);
                    return new GuiToolBox(player.inventory, te instanceof WCTileEntityToolBox ? (WCTileEntityToolBox) te
                            : null);
                }
                break;
            case CONTROLUNIT_GUI_ID:
                if (te instanceof WCTileEntityControlUnitDock) {
                    return new ControlUnitGUI(player.inventory, (WCTileEntityControlUnitDock) te);
                }
                break;
            case CALCULATOR_GUI_ID:
                if (player.inventory.getCurrentItem().getItem().itemID == ModItems.calculator.itemID) {
                    return new GuiCalculator();
                } else {
                    System.out.println("Item is not valid");
                }
                break;
            case FREEZER_GUI_ID:
            	if (te instanceof WCTileEntityFreezer) {
            		return new GuiFreezer((WCTileEntityFreezer)te);
            	}
            	break;
            case LOCK_ASSEMBLER_GUI_ID:
            	if (te instanceof WCTileEntityLockAssembler) {
            		return new GuiLockAssembler(player.inventory, (WCTileEntityLockAssembler)te);
            	}
            	break;
            case BOAT_ASSEMBLER_ENTITY_GUI_ID:
            	Entity e = world.getEntityByID(x);
            	if(e instanceof IInventory){
            		return new GuiBoatAssembler(player.inventory, (IInventory)e);
            	}
            	break;
            case BOAT_ASSEMBLER_BLOCK_GUI_ID:
            	if(te instanceof WCTileEntityBoatAssembler){
            		return new GuiBoatAssembler(player.inventory, (IInventory)te);
            	}
            	break;
        }
        return null;
    }
}
