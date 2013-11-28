package dgrxf.watercraft.item.toolbox;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.util.MD5Generator;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:14/06/2013 MM/DD/YYYYY
 * 
 */

public class ItemPadlock extends Item {
    
    public ItemPadlock() {
        super(ItemInfo.PADLOCK_ID);
        setUnlocalizedName(ItemInfo.PADLOCK_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.miscTab);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        
        if (tile instanceof ILockableBlock && !((ILockableBlock) tile).isLocked()) {
            //System.out.println("Toolbox found, placing padlock");
            ((ILockableBlock) tile).setLocked(true);
            ((ILockableBlock) tile).setCode(stack.getItemDamage());
            world.markBlockForUpdate(x, y, z);
            
            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
            }
        }
        
        return true;
    }
    //temporary because the item renderer is poop <3
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        itemIcon = par1IconRegister.registerIcon("Watercraft:lock");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
     // TODO Translation
        info.add("CODE: " + MD5Generator.generateHash(Integer.toString(itemstack.getItemDamage())));
    }
}
