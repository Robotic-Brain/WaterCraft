package dgrxf.watercraft.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.util.MathHelper;
import dgrxf.watercraft.util.Vector3;

public class ItemTapeMeasure extends Item{

	public ItemTapeMeasure() {
		super(ItemInfo.TAPE_MEASURE_ID);
		setUnlocalizedName(ItemInfo.TAPE_MEASURE_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.creativeTab);
	}
		
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return false;
		
		NBTTagCompound tag;
		if(stack.getTagCompound() != null) tag = stack.getTagCompound(); else tag = new NBTTagCompound();
		
		if(world.blockExists(x, y, z)){
			if(!tag.hasKey("first") || !tag.getBoolean("first")){
				tag.setInteger("x", x);
				tag.setInteger("y", y);
				tag.setInteger("z", z);
				tag.setBoolean("first", true);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("Start Measurement| X: " + x + " Y: " + y + " Z: " + z);
			}else if(!tag.hasKey("x") || tag.getBoolean("first")){
				Minecraft.getMinecraft().thePlayer.sendChatMessage("End Measurement| X: " + x + " Y: " + y + " Z: " + z);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("Distance: " + MathHelper.calculateVector3Distance(new Vector3(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")), new Vector3(x, y, z)));
				tag.setBoolean("first", false);
			}
		}
		
		stack.setTagCompound(tag);
		return false;
	}
}
