package dgrxf.watercraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.interfaces.GuiBoatAssembler;
import dgrxf.watercraft.client.gui.interfaces.GuiCalculator;
import dgrxf.watercraft.client.gui.interfaces.GuiFreezer;
import dgrxf.watercraft.client.gui.interfaces.GuiLockAssembler;
import dgrxf.watercraft.client.gui.interfaces.GuiToolBox;
import dgrxf.watercraft.client.gui.interfaces.controlunit.CraneGUI;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.item.ItemRegistry;
import dgrxf.watercraft.server.container.BoatAssemblerContainer;
import dgrxf.watercraft.server.container.CalculatorContainer;
import dgrxf.watercraft.server.container.CraneContainer;
import dgrxf.watercraft.server.container.FreezerContainer;
import dgrxf.watercraft.server.container.LockAssemblerContainer;
import dgrxf.watercraft.server.container.ToolboxContainer;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.tileentity.controlunit.WCTileEntityControlUnitDock;

public class GuiHandler implements IGuiHandler {
    
	public static final int VANILLA_CHEST_ID   = -1;
    public static final int TOOLBOX_GUI_ID     = 0;
    public static final int CONTROLUNIT_GUI_ID = 1;
    public static final int CALCULATOR_GUI_ID = 2;
    public static final int FREEZER_GUI_ID = 3;
    public static final int LOCK_ASSEMBLER_GUI_ID = 4;
    public static final int BOAT_ASSEMBLER_BLOCK_GUI_ID = 5;

    
    public GuiHandler() {
    }
    
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        
        TileEntity te = world.getBlockTileEntity(x, y, z);
        
        switch (id) {
            case TOOLBOX_GUI_ID:
                if (te instanceof WCTileEntityToolBox || player.getCurrentEquippedItem().itemID == ModBlocks.TOOLBOX.getId()) {
                    return new ToolboxContainer(player.inventory, te instanceof WCTileEntityToolBox ? (WCTileEntityToolBox) te
                            : null);
                }
                break;
            case CONTROLUNIT_GUI_ID:
                if (te instanceof WCTileEntityControlUnitDock) {
                    return new CraneContainer(player.inventory, (WCTileEntityCrane) te);
                }
                break;
            case CALCULATOR_GUI_ID:
                if (player.inventory.getCurrentItem().getItem().itemID == ItemRegistry.CALCULATOR.getId()) {
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
            case BOAT_ASSEMBLER_BLOCK_GUI_ID:
            	if(te instanceof WCTileEntityBoatAssembler){
            		return new BoatAssemblerContainer(player.inventory, (WCTileEntityBoatAssembler)te);
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
                
                if (te instanceof WCTileEntityToolBox || player.getCurrentEquippedItem().itemID == ModBlocks.TOOLBOX.getId()) {
                    Sounds.TOOLBOX_OPENING.play(player.posX, player.posY, player.posZ, 1.0f, 1.0f);
                    return new GuiToolBox(player.inventory, te instanceof WCTileEntityToolBox ? (WCTileEntityToolBox) te
                            : null);
                }
                break;
            case CONTROLUNIT_GUI_ID:
                if (te instanceof WCTileEntityControlUnitDock) {
                    return new CraneGUI(player.inventory, (WCTileEntityCrane) te);
                }
                break;
            case CALCULATOR_GUI_ID:
                if (player.inventory.getCurrentItem().getItem().itemID == ItemRegistry.CALCULATOR.getId()) {
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
            case BOAT_ASSEMBLER_BLOCK_GUI_ID:
            	if(te instanceof WCTileEntityBoatAssembler){
            		return new GuiBoatAssembler(player.inventory, (WCTileEntityBoatAssembler)te);
            	}
            	break;
        }
        return null;
    }
}
