package dgrxf.watercraft.item.toolbox;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.util.RotationHelper;
import dgrxf.watercraft.util.TranslationHelper;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ItemBlockToolBox extends ItemBlock {
    
    public ItemBlockToolBox(int id) {
        super(id);
        this.setCreativeTab(Watercraft.creativeTab);
        maxStackSize = 1;
    }
    
    //Makes the item call createEntity when the item is dropped
    @Override
    public boolean hasCustomEntity(ItemStack stack) {
    	return true;
    }
    
    //This is used to stop the toolbox from being rendered open when dropped
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {    	
    	AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(location.posX - 1, location.posY - 1, location.posZ - 1, location.posX + 1, location.posY + 1, location.posZ + 1);
    	List list = world.getEntitiesWithinAABB(EntityPlayer.class, bounds);
    	
    	for(int i = 0; i < list.size(); i++){
    		if(list.get(i) instanceof EntityPlayer){
    			((EntityPlayer)list.get(i)).closeScreen();
    		}
    	}
    	
    	NBTTagCompound tag;
    	if(itemstack.getTagCompound() != null) tag = itemstack.getTagCompound();
    	else tag = new NBTTagCompound();
    	
    	tag.setBoolean("isOpen", false);
    	itemstack.setTagCompound(tag);
    	return null;
    }
    
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (world.isRemote) return false;
        if (!player.isSneaking()) {
        	FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.TOOLBOX_GUI_ID, world, x, y, z); 
        	return false;
    	}
        world.setBlock(x, y, z, ModBlocks.toolbox.blockID);
        WCTileEntityToolBox tile = (WCTileEntityToolBox) world.getBlockTileEntity(x, y, z);
        if (stack.getTagCompound() != null) {
            NBTTagList items = stack.getTagCompound().getTagList("Items");
            
            for (int i = 0; i < items.tagCount(); i++) {
                NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
                int slot = item.getByte("Slot");
                
                if (slot >= 0 && slot < tile.getSizeInventory()) {
                    tile.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
                }
            }
        }
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("playerName")) {
            tile.setPlayerName(stack.getTagCompound().getString("playerName"));
        } else {
            tile.setPlayerName(player.username);
        }
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        ((WCTileEntityToolBox) world.getBlockTileEntity(x, y, z)).setBlockDirection(RotationHelper.yawToForge(player.rotationYaw));
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.isSneaking()) {
            if (stack.getTagCompound() == null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setString("playerName", player.username);
                stack.setTagCompound(tag);
            }
		    Sounds.TOOLBOX_OPENING.play(player.posX, player.posY, player.posZ, 1.0f, 1.0f);
            FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.TOOLBOX_GUI_ID, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
        if (GuiScreen.isShiftKeyDown()) {
            if (stack.getTagCompound() != null) {
                NBTTagList items = stack.getTagCompound().getTagList("Items");
                
                ItemStack[] inv = new ItemStack[9];
                
                for (int i = 0; i < items.tagCount(); i++) {
                    NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
                    int slot = item.getByte("Slot");
                    
                    if (slot >= 0 && slot < inv.length) {
                        inv[slot] = ItemStack.loadItemStackFromNBT(item);
                    }
                }
                if (inv != null) {
                    list.add(TranslationHelper.translate(TranslationHelper.TOOLBOX_CONTAINS));
                    int index = 0;
                    for (int i = 0; i < inv.length; i++) {
                        if (inv[i] != null) {
                            index++;
                            list.add(index + "| " + inv[i].getDisplayName());
                        }
                    }
                    if (index == 0) {
                        list.add(TranslationHelper.translate(TranslationHelper.TOOLBOX_CONTAINS_NOTING));
                    }
                }
            } else {
                list.add(TranslationHelper.translate(TranslationHelper.TOOLBOX_EMPTY));
            }
        } else {
            if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("playerName")) {
                list.add(TranslationHelper.translate(TranslationHelper.TOOLBOX_OWNER, stack.getTagCompound().getString("playerName")));
            } else {
                list.add(TranslationHelper.translate(TranslationHelper.TOOLBOX_NO_OWNER));
            }
        }
    }
}
