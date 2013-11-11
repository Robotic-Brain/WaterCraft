package dgrxf.watercraft.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.util.TranslationHelper;
import dgrxf.watercraft.util.Vector3;

public class ItemTapeMeasure extends Item {
    
    private static final String NBT_TAG_X     = "x";
    private static final String NBT_TAG_Y     = "y";
    private static final String NBT_TAG_Z     = "z";
    private static final String NBT_TAG_FIRST = "first";
    
    public ItemTapeMeasure() {
        super(ItemInfo.TAPE_MEASURE_ID);
        setUnlocalizedName(ItemInfo.TAPE_MEASURE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.creativeTab);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        
        if (world.blockExists(x, y, z)) {
            if (!getFirstFlag(stack)) {
                setPos(stack, new Vector3(x, y, z));
                setFirstFlag(stack, true);
                
                Minecraft.getMinecraft().thePlayer.sendChatMessage(TranslationHelper.translate(TranslationHelper.TABLE_MEASURE_START, x, y, z));
            } else {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(TranslationHelper.translate(TranslationHelper.TABLE_MEASURE_END, x, y, z));
                Minecraft.getMinecraft().thePlayer.sendChatMessage(TranslationHelper.translate(TranslationHelper.TABLE_MEASURE_DISTANCE, getPos(stack).sub(new Vector3(x, y, z)).length()));
                setFirstFlag(stack, false);
            }
        }
        
        return false;
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
