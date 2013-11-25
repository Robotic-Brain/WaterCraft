package dgrxf.watercraft.item.boat;

import java.util.HashSet;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ModularBoat;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.interfaces.IModularBoat;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.util.ModuleHelper;

public class ItemModularBoat extends Item implements IModularBoat{

	public ItemModularBoat() {
        super(ItemInfo.MODULAR_BOAT_ID);
        setUnlocalizedName(ItemInfo.BOAT_MODULAR_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.boatTab);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("boat");
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        }
        
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
        
        if (movingobjectposition == null) {
            return itemStack;
        } else {
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
                
                int x1 = movingobjectposition.blockX;
                int y1 = movingobjectposition.blockY;
                int z1 = movingobjectposition.blockZ;
                
                if ((world.getBlockMaterial(x1, y1, z1) == Material.water || world.getBlockMaterial(x1, y1, z1) == Material.lava) && world.isAirBlock(x1, y1 + 1, z1)) {
                    AbstractBaseBoat boat = new ModularBoat(world, x1, y1 + 1, z1, itemStack.getTagCompound());
                    world.spawnEntityInWorld(boat);
                    
                    if (!player.capabilities.isCreativeMode) {
                        --itemStack.stackSize;
                    }
                }
            }
            
            return itemStack;
        }
    }
    
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack.hasTagCompound()){
			NBTTagCompound tag = par1ItemStack.getTagCompound().getCompoundTag("Modules");
			
			for(int i = 0; i < Alphabet.COUNT.ordinal(); i++){
				if(tag.hasKey(Alphabet.values()[i].toString())){
					par3List.add(ModuleHelper.parseStringToItemStack(tag.getString(Alphabet.values()[i].toString())).getDisplayName());
				}
			}
			
		}
    }
    
    @Override
    public HashSet<String> getModuleList(ItemStack stack) {
    	if(stack.getTagCompound() == null)
    		return new HashSet<String>();
    	else{
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Modules");
    		HashSet returnList = new HashSet<String>();
			for(int i = 0; i < Alphabet.COUNT.ordinal(); i++){
				if(tag.hasKey(Alphabet.values()[i].toString())){
					returnList.add(tag.getString(Alphabet.values()[i].toString()));
				}
			}
    		return returnList;
    	}
    }
}
