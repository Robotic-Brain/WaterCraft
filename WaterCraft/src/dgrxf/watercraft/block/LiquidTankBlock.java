package dgrxf.watercraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

public class LiquidTankBlock extends Block{

	private Icon innerIcon;
	
	public LiquidTankBlock(int par1) {
		super(par1, Material.glass);
		this.setHardness(1f);
		this.setCreativeTab(Watercraft.miscTab);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderInfo.TANK_RENDER_ID;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		if(meta == 1){
			return innerIcon;
		}
		
		return blockIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(ModInfo.MODID +":tank");
		innerIcon = par1IconRegister.registerIcon(ModInfo.MODID +":tank_inner");
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new WCTileEntityLiquidStorageTank();
	}
	
	@Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random) {
		super.updateTick(par1World, x, y, z, par5Random);
		Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(x, y, z);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		world.scheduleBlockUpdate(x, y, z, 0, 1);
		ItemStack playerItem = player.inventory.getCurrentItem();
		if(playerItem != null){
			WCTileEntityLiquidStorageTank tank = (WCTileEntityLiquidStorageTank)world.getBlockTileEntity(x, y, z);
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());
			if(liquid != null){
				int amount = tank.fill(ForgeDirection.UNKNOWN, liquid, false);
				if(amount == liquid.amount){
					tank.fill(ForgeDirection.UNKNOWN, liquid, true);
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, removeItem(playerItem));
						return true;
					}
					
					return true;
				}
				else{
					return true;
				}
			}
			else if (FluidContainerRegistry.isBucket(playerItem)){
				FluidTankInfo[] tankInfo = tank.getTankInfo(ForgeDirection.UNKNOWN);
				FluidStack fillFluid = tankInfo[0].fluid;
				ItemStack stack = FluidContainerRegistry.fillFluidContainer(fillFluid, playerItem);
				if(stack != null){
					tank.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(stack).amount, true);
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
				else
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		player.addExhaustion(0.025f);
		ItemStack dropItem = new ItemStack(this.blockID, 1, 0);
		WCTileEntityLiquidStorageTank teTank = (WCTileEntityLiquidStorageTank)world.getBlockTileEntity(x, y, z);
		FluidStack liquid = teTank.tank.getFluid();
		
		if(liquid != null){
			NBTTagCompound tag = new NBTTagCompound();
			NBTTagCompound liquidTag = new NBTTagCompound();
			liquid.writeToNBT(liquidTag);
			tag.setCompoundTag("Fluid", liquidTag);
			dropItem.setTagCompound(tag);
			//dropItem.
		}
		
		if(!player.capabilities.isCreativeMode){
			if(!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")){
				float spawnX = x + world.rand.nextFloat();
				float spawnY = y + world.rand.nextFloat();
				float spawnZ = z + world.rand.nextFloat();
				
				EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, dropItem);
				
				float mult = 0.1f;
				
				droppedItem.motionX = (-0.5 + world.rand.nextFloat()) * mult;
				droppedItem.motionY = (1 + world.rand.nextFloat()) * mult;
				droppedItem.motionZ = (-0.5 + world.rand.nextFloat()) * mult;
				
				world.spawnEntityInWorld(droppedItem);
			}
		}
		
		return world.setBlockToAir(x, y, z);
	}
	
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) { }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack) {
		if(stack.hasTagCompound()){
			
			NBTTagCompound liquidTag = stack.getTagCompound().getCompoundTag("Fluid");
			if(liquidTag != null){
				FluidStack fluid = FluidStack.loadFluidStackFromNBT(liquidTag);
				WCTileEntityLiquidStorageTank teTank = (WCTileEntityLiquidStorageTank)world.getBlockTileEntity(x, y, z);
				teTank.tank.setFluid(fluid);
				
			}
			
		}
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
}