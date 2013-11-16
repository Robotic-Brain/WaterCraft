package dgrxf.watercraft.item;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
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

public class ItemBlockChest extends ItemBlock {
    
    public ItemBlockChest(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
        maxStackSize = 64;
    }
}