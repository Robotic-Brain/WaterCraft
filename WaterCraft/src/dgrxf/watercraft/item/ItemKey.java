package dgrxf.watercraft.item;

import java.util.List;
import java.util.Random;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.util.MD5Generator;

/**
 * 
 * ItemKey
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemKey extends Item {
    
    Random random = new Random();
    
    public ItemKey(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity te = world.getBlockTileEntity(x, y, z);
            
            if (te instanceof ILockableBlock && ((ILockableBlock) te).isLocked()) {
                int code = ((ILockableBlock) te).getCode();
                
                if (code == stack.getItemDamage()) {
                    if (player.isSneaking()) {
                        ((ILockableBlock) te).setLocked(false);
                        ((ILockableBlock) te).setCode(-1);
                        world.markBlockForUpdate(x, y, z);
                        
                        EntityItem entityitem = new EntityItem(world, x, y + 1, z, new ItemStack(ModItems.PADLOCK.getItem(), 1, code));
                        
                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.random.nextGaussian() * f3;
                        entityitem.motionY = (float) this.random.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.random.nextGaussian() * f3;
                        entityitem.delayBeforeCanPickup = 10;
                        world.spawnEntityInWorld(entityitem);
                    } else {
                        ((ILockableBlock) te).setLocked(false);
                    }
                }
            }
        }
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        itemIcon = par1IconRegister.registerIcon("watercraft:key");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
        if (GuiScreen.isShiftKeyDown()) {
            // TODO Translation
            info.add("CODE: " + MD5Generator.generateHash(Integer.toString(itemstack.getItemDamage())));
        } else {
            // TODO Translation
            info.add("Hold" + GuiColor.LIGHTBLUE + " SHIFT " + GuiColor.LIGHTGRAY + "to view the key code");
        }
    }
}
