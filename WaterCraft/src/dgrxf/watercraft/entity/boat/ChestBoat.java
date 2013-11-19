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

public class ChestBoat extends AbstractBaseBoat implements ILockableBlock{

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
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(EntityInfo.DATAWATCHER_CHEST_LOCK, new Byte((byte) 0));
	}
	
	@Override
	protected void setBoatAI(BoatAITaskList list) {
		list.addTask(new DumbTask(this, 0f));
		list.addTask(new InventoryTask(this, 1.0f, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27));
	}

	@Override
	public Block getDisplayTile() {
		return ModBlocks.chest;
	}
	
	//All of these aren't used, only implementing to differentiate from normal boats, using datawatchers instead so you don't have to send packets.
	@Override
	public void setLocked(boolean lock) {}

	@Override
	public boolean isLocked() {return false;}

	@Override
	public int getCode() {return 0;}

	@Override
	public void setCode(int code) {}
	
}
