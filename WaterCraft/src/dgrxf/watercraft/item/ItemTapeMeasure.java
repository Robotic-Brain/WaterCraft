package dgrxf.watercraft.item;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.util.TranslationHelper;
import dgrxf.watercraft.util.Vector3;

/**
 * 
 * ItemTapeMeasure
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemTapeMeasure extends Item {
    
    private static final String NBT_TAG_X     = "x";
    private static final String NBT_TAG_Y     = "y";
    private static final String NBT_TAG_Z     = "z";
    private static final String NBT_TAG_FIRST = "first";
    
    public ItemTapeMeasure(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
    }
    
    /**
     * Used to allow the player to reset the tape measure by shift-right clicking in the air
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    	if (world.isRemote)
            return stack;
    	
    	if(player.isSneaking()){
        	setFirstFlag(stack, false);
        	player.sendChatToPlayer(ChatMessageComponent.createFromText(TH.translate(TH.TAPE_MEASURE_RESTART)));
        }
    	return stack;
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return false;
        
	        if (world.blockExists(x, y, z)) {
	            if (!getFirstFlag(stack)) {
	                setPos(stack, new Vector3(x, y, z));
	                setFirstFlag(stack, true);
	                player.sendChatToPlayer(ChatMessageComponent.createFromText(TH.translate(TH.TAPE_MEASURE_START, x, y, z)));
	            } else {
	            	player.sendChatToPlayer(ChatMessageComponent.createFromText(TH.translate(TH.TAPE_MEASURE_END, x, y, z)));
	            	player.sendChatToPlayer(ChatMessageComponent.createFromText(TH.translate(TH.TAPE_MEASURE_DISTANCE, getPos(stack).sub(new Vector3(x, y, z)).length() + 1)));
	                setFirstFlag(stack, false);
	            }
        }
        return false;
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
    	if (GuiScreen.isShiftKeyDown()){
    	 // TODO Translation
    	    // TODO: This whole "Manual" is a mess, this should display what to do based on internal state
    		list.add("To use this item do this:");
    		list.add(" 1: Right Click on the first block");
    		list.add(" 2: Right Click on the secound block");
    		list.add("    Simple!");
    		list.add("");
    		list.add("    You can also right click in the air");
    		list.add("    to reset the measurment");
    	}else
    	 // TODO Translation
    		list.add("Hold" + GuiColor.LIGHTBLUE + " SHIFT " + GuiColor.LIGHTGRAY + "to view more information");
    }
    
    /**
     * Stores the position to the stack
     * 
     * (only Integers!)
     * 
     * @param stack
     * @param v
     *            position
     */
    private void setPos(ItemStack stack, Vector3 v) {
        NBTTagCompound tag = getTagCompound(stack);
        tag.setInteger(NBT_TAG_X, (int) v.x);
        tag.setInteger(NBT_TAG_Y, (int) v.y);
        tag.setInteger(NBT_TAG_Z, (int) v.z);
        stack.setTagCompound(tag);
    }
    
    /**
     * Gets the stored position from stack
     * 
     * @param stack
     * @return position
     */
    private Vector3 getPos(ItemStack stack) {
        NBTTagCompound tag = getTagCompound(stack);
        try {
            int x = tag.getInteger(NBT_TAG_X);
            int y = tag.getInteger(NBT_TAG_Y);
            int z = tag.getInteger(NBT_TAG_Z);
            return new Vector3(x, y, z);
        } catch (Exception e) {
            return new Vector3();
        }
    }
    
    /**
     * Stores the first flag to the stack
     * 
     * @param stack
     * @param flag
     */
    private void setFirstFlag(ItemStack stack, boolean f) {
        NBTTagCompound tag = getTagCompound(stack);
        tag.setBoolean(NBT_TAG_FIRST, f);
        stack.setTagCompound(tag);
    }
    
    /**
     * Gets the stored flag value
     * 
     * @param stack
     * @return
     */
    private boolean getFirstFlag(ItemStack stack) {
        NBTTagCompound tag = getTagCompound(stack);
        try {
            return tag.getBoolean(NBT_TAG_FIRST);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Safely gets the tag compound
     * 
     * @param stack
     * @return tag
     */
    private NBTTagCompound getTagCompound(ItemStack stack) {
        NBTTagCompound tag;
        if (stack.getTagCompound() != null) {
            tag = stack.getTagCompound();
        } else {
            tag = new NBTTagCompound();
        }
        
        return tag;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("Watercraft:tape");
    }
}
