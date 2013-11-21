package dgrxf.watercraft.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.util.MD5Generator;

public class ItemRope extends Item {
    public ItemRope() {
        super(ItemInfo.ROPE_ID);
        setUnlocalizedName(ItemInfo.ROPE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.miscTab);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
    	info.add(Integer.toString(itemstack.getItemDamage()));
    }
    
}
