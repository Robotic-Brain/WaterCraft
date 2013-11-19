package dgrxf.watercraft.item.boat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
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
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.lib.ItemInfo;

public class ItemModularBoat extends Item implements IBoatModule{

	public ItemModularBoat() {
        super(ItemInfo.MODULAR_BOAT_ID_DEFAULT);
        setUnlocalizedName(ItemInfo.BOAT_UNLOCALIZED_NAME+"Test");
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
                
                if (world.getBlockMaterial(x1, y1, z1) == Material.water && world.isAirBlock(x1, y1 + 1, z1)) {
                    AbstractBaseBoat boat = new ModularBoat(world, x1, y1 + 1, z1, itemStack.stackTagCompound);
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
			
			NBTTagCompound tagOne = par1ItemStack.getTagCompound().getCompoundTag("Tag One");
			NBTTagCompound tagTwo = par1ItemStack.getTagCompound().getCompoundTag("Tag Two");
			
			if(tagOne != null){
				for(int i = 0; i < 10; i++){
					if(tagOne.hasKey("AI"+i)){
						par3List.add(tagOne.getString("AI"+i));
					}else{
						break;
					}
				}
				if(tagOne.hasKey("Type")){
					par3List.add(tagOne.getString("Type"));
				}
				if(tagOne.hasKey("Block")){
					par3List.add(Integer.toString(tagOne.getInteger("Block")));
				}
			}
			
			if(tagTwo != null){
				for(int i = 0; i < 10; i++){
					if(tagTwo.hasKey("AI"+i)){
						par3List.add(tagTwo.getString("AI"+i));
					}else{
						break;
					}
				}
				if(tagTwo.hasKey("Type")){
					par3List.add(tagTwo.getString("Type"));
				}
				if(tagTwo.hasKey("Block")){
					par3List.add(Integer.toString(tagTwo.getInteger("Block")));
				}
			}
			
		}
    }

	@Override
	public ModuleType getModuleType() {
		return ModuleType.BOAT;
	}

	@Override
	public Block getBlockType() {
		return null;
	}

	@Override
	public void addBoatAI(List<Class<? extends BoatAITaskBase>> list) {
		list.add(DumbTask.class);
	}

	@Override
	public void writeModuleInfoToNBT(NBTTagCompound tag) {
		List<Class<? extends BoatAITaskBase>> list = new ArrayList();
		addBoatAI(list);
		ModuleType type = getModuleType();
		int i = 0;
		for(Class<? extends BoatAITaskBase> inList : list){
			tag.setString("AI" + i, inList.getName());
			++i;
		}
		
		tag.setString("Type", type.toString());
	}
}
