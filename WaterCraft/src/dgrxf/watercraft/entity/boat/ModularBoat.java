package dgrxf.watercraft.entity.boat;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.lib.EntityInfo;

public class ModularBoat extends AbstractBaseBoat implements IInventory{

	ItemStack[] items = new ItemStack[27];
	
	public ModularBoat(World world){
		super(world);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(EntityInfo.DATAWATCHER_TILE_ID, new Integer(-1));
	}
	
	public ModularBoat(World par1World, double par2, double par4, double par6, NBTTagCompound tag) {
		super(par1World, par2, par4, par6);
		BoatAITaskList list = new BoatAITaskList(this);
		this.readTagInformation(tag, list);
		this.ai = list;
	}
	
	public void readTagInformation(NBTTagCompound tag, BoatAITaskList list){
		NBTTagCompound tagOne = tag.getCompoundTag("Tag One");
		NBTTagCompound tagTwo = tag.getCompoundTag("Tag Two");
		int x = 0;
		if(tagOne != null){
			for(int i = 0; x < 10; x++){
				if(tagOne.hasKey("AI"+x)){
					try {
						if(Class.forName(tagOne.getString("AI"+x)) == DumbTask.class){
							list.addTask(new DumbTask(this, (float)x));
						}
						else if(Class.forName(tagOne.getString("AI"+x)) == InventoryTask.class){
							list.addTask(new InventoryTask(this, (float)x, GuiHandler.VANILLA_CHEST_ID));
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else{
					break;
				}
			}
			if(tagOne.hasKey("Type")){
				
			}
			if(tagOne.hasKey("Block")){
				this.dataWatcher.updateObject(EntityInfo.DATAWATCHER_TILE_ID, tagOne.getInteger("Block"));
			}
		}
		
		if(tagTwo != null){
			for(int i = 0; i < 10; i++){
				if(tagTwo.hasKey("AI"+i)){
					try {
						if(Class.forName(tagTwo.getString("AI"+i)) == DumbTask.class){
							list.addTask(new DumbTask(this, (float)i));
						}
						else if(Class.forName(tagTwo.getString("AI"+i)) == InventoryTask.class){
							list.addTask(new InventoryTask(this, (float)i+x, GuiHandler.VANILLA_CHEST_ID));
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else{
					break;
				}
			}
			if(tagTwo.hasKey("Type")){
			}
			if(tagTwo.hasKey("Block")){
				this.dataWatcher.updateObject(EntityInfo.DATAWATCHER_TILE_ID, tagTwo.getInteger("Block"));
			}
		}
		
	}

	@Override
	protected void setBoatAI(BoatAITaskList list) {
		
	}

	@Override
	public Block getDisplayTile() {
		if(this.dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TILE_ID) != -1){
			return Block.blocksList[this.dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TILE_ID)];
		}else{
			return null;
		}
	}
	
	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
        return items[i];
	}

    @Override
    public ItemStack decrStackSize(int i, int j) {
        ItemStack item = getStackInSlot(i);
        if (item != null) {
            if (item.stackSize <= j) {
                setInventorySlotContents(i, null);
            } else {
                item = item.splitStack(j);
                onInventoryChanged();
            }
        }
        return item;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack item = getStackInSlot(i);
        setInventorySlotContents(i, null);
        return item;
    }

    public void setInventorySlotContents(int i, ItemStack item) {
        items[i] = item;
        if (item != null && item.stackSize > getInventoryStackLimit()) {
            item.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Boat Chest";
    }

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

	@Override
	public void onInventoryChanged() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return entityplayer.getDistanceSq(posX + 0.5, posY + 0.5, posZ + 0.5) <= 64;
    }

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
}
