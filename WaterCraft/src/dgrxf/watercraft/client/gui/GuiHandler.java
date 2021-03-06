package dgrxf.watercraft.client.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.boat.GuiBoat;
import dgrxf.watercraft.client.gui.boatassembler.GuiBoatAssembler;
import dgrxf.watercraft.client.gui.crane.CraneGUI;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.common.container.BoatAssemblerContainer;
import dgrxf.watercraft.common.container.BoatContainer;
import dgrxf.watercraft.common.container.CalculatorContainer;
import dgrxf.watercraft.common.container.CraneContainer;
import dgrxf.watercraft.common.container.FreezerContainer;
import dgrxf.watercraft.common.container.LockAssemblerContainer;
import dgrxf.watercraft.common.container.ToolboxContainer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * 
 * GuiHandler
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiHandler implements IGuiHandler {
    
	public static final int VANILLA_CHEST_ID   = -1;
    public static final int TOOLBOX_GUI_ID     = 0;
    public static final int CRANE_GUI_ID 	= 1;
    public static final int CALCULATOR_GUI_ID = 2;
    public static final int FREEZER_GUI_ID = 3;
    public static final int LOCK_ASSEMBLER_GUI_ID = 4;
    public static final int BOAT_ASSEMBLER_BLOCK_GUI_ID = 5;
    public static final int BOAT_GUI_ID = 6;
    
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
            case CRANE_GUI_ID:
                if (te instanceof WCTileEntityCrane) {
                    return new CraneContainer(player.inventory, (WCTileEntityCrane) te);
                }
                break;
            case CALCULATOR_GUI_ID:
                if (player.inventory.getCurrentItem().getItem().itemID == ModItems.CALCULATOR.getId()) {
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
            case BOAT_GUI_ID:
            	AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
            	List list = world.getEntitiesWithinAABB(AbstractBaseBoat.class, axisalignedbb);
            	Iterator iterator = list.iterator();
            	if(iterator.hasNext())
	        		return new BoatContainer(player.inventory, (AbstractBaseBoat)iterator.next());
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
            case CRANE_GUI_ID:
                if (te instanceof WCTileEntityCrane) {
                    return new CraneGUI(player.inventory, (WCTileEntityCrane) te);
                }
                break;
            case CALCULATOR_GUI_ID:
                if (player.inventory.getCurrentItem().getItem().itemID == ModItems.CALCULATOR.getId()) {
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
            case BOAT_GUI_ID:
            	AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
            	List list = world.getEntitiesWithinAABB(AbstractBaseBoat.class, axisalignedbb);
            	Iterator iterator = list.iterator();
            	if(iterator.hasNext())
	        		return new GuiBoat(player.inventory, (AbstractBaseBoat)iterator.next());
            	break;
        }
        return null;
    }
}
