package dgrxf.watercraft.entity.boat;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.lib.EntityInfo;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

public class TankBoat extends AbstractBaseBoat implements IFluidHandler{

	public FluidTank tank;
	private boolean firstRun = true;
	
	public TankBoat(World world){
		super(world);
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
	}
	
	public TankBoat(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(firstRun){
			if(tank != null){
				if(tank.getFluid() != null){
					dataWatcher.updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(tank.getFluidAmount()));
					dataWatcher.updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, FluidRegistry.getFluid(tank.getFluid().fluidID).getName());
					firstRun = false;
				}
			}
		}
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(0));
		dataWatcher.addObject(EntityInfo.DATAWATCHER_LIQUID_NAME, "none");
	}

	@Override
	protected void setBoatAI(BoatAITaskList list) {
		list.addTask(new DumbTask(this, 0.0F));
	}
	
	@Override
	public Block getDisplayTile() {
		return ModBlocks.tank;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(worldObj.isRemote) return true;
		ItemStack playerItem = player.inventory.getCurrentItem();
		if(playerItem != null){
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());
			if(liquid != null){
				int amount = this.fill(ForgeDirection.UNKNOWN, liquid, false);
				if(amount == liquid.amount){
					this.fill(ForgeDirection.UNKNOWN, liquid, true);
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, removeItem(playerItem));
						return true;
					}

					return true;
				}
			}
			else if (FluidContainerRegistry.isBucket(playerItem)){
				FluidTankInfo[] tankInfo = this.getTankInfo(ForgeDirection.UNKNOWN);
				FluidStack fillFluid = tankInfo[0].fluid;
				ItemStack stack = FluidContainerRegistry.fillFluidContainer(fillFluid, playerItem);
				if(stack != null){
					this.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(stack).amount, true);
					if(!player.capabilities.isCreativeMode){
						if(playerItem.stackSize == 1){
							player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
						}
						else
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, removeItem(playerItem));
							
							if(!player.inventory.addItemStackToInventory(stack)){
								player.dropPlayerItem(stack);
							}
						}
					}
					return true;
				}
			}
		}
		
		return true;
	}
	
	private ItemStack removeItem(ItemStack playerItem) {
		if(playerItem.stackSize == 1){
			if(playerItem.getItem().hasContainerItem()){
				return playerItem.getItem().getContainerItemStack(playerItem);
			}
			else
			{
				return null;
			}
		}
		else
		{
			playerItem.splitStack(1);
			
			return playerItem;
		}
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount = tank.fill(resource, doFill);
		if(amount > 0 && doFill){
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT) + amount));
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, new String(resource.getFluid().getName()));
		}

		return amount;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
		if(amount != null && doDrain){
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT) - amount.amount));
		}else if(amount == null && doDrain){
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, new String("none"));
		}
		
		return amount;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidStack fluidStack = null;
		if(tank.getFluid() != null){
			fluidStack = tank.getFluid().copy();
		}
		
		return new FluidTankInfo[]{ new FluidTankInfo(fluidStack, tank.getCapacity()) };
	}
}
