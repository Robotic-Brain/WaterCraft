package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import dgrxf.watercraft.tileentity.WCTileEntityChest;

/**
 * 
 * ChestIvocationHandler
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ChestIvocationHandler extends PeripheralInvocationHandlerBase{

	private WCTileEntityChest chest;
	
	private final String[] methods = new String[]{"getChestContents"};
	
	public ChestIvocationHandler(WCTileEntityChest te) {
		chest = te;
	}
	
	@Override
	public boolean cantAttachToSide(int side) {
		return true;
	}

	@Override
	public Object[] callMethod(Object computer, Object context, int method, Object[] args) {
		switch(method){
		case 0:
			return new Object[]{getChestContents()};
		}
		return null;
	}

	@Override
	public String getType() {
		return "Locking Chest";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}
	
	private Map getChestContents(){
		HashMap<Integer, String> temp = new HashMap();
		for(int i = 0; i < chest.getSizeInventory(); ++i){
			ItemStack tempItem = chest.getStackInSlot(i);
			String tempSlot = tempItem == null ? "Empty" : tempItem.getDisplayName();
			temp.put(i, tempSlot);
		}
		
		return temp;
	}

}
