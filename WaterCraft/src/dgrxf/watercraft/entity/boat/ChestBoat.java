package dgrxf.watercraft.entity.boat;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.EntityInfo;
import dgrxf.watercraft.util.LogHelper;

public class ChestBoat extends AbstractBaseBoat{

    public ItemStack[] items = new ItemStack[27];
	
    private int code;
    private boolean lock;
    
	public ChestBoat(World world){
		super(world);
	}
	
	public ChestBoat(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}
	
	@Override
	protected void updateBoatAI(BoatAITaskList list) {
		list.addTask(new DumbTask(this, 0f));
		list.addTask(new InventoryTask(this, 1.0f, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true));
	}

	@Override
	public Block getDisplayTile() {
		return ModBlocks.chest;
	}
	
}
