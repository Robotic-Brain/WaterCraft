package dgrxf.watercraft.entity.boat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.RopeTask;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.lib.EntityInfo;
import dgrxf.watercraft.util.ModuleHelper;
import dgrxf.watercraft.util.ModuleRegistry;

public class ModularBoat extends AbstractBaseBoat{
	
	ArrayList strings = new ArrayList<String>();
	private BoatAITaskList list;
	
	private static final String NBT_TAG_MODULE_COMPUND = "Modules";
	
	public ModularBoat(World world){
		super(world);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(0));
		this.dataWatcher.addObject(EntityInfo.DATAWATCHER_LIQUID_NAME, "none");
		this.dataWatcher.addObject(EntityInfo.DATAWATCHER_TILE_ID, new Integer(-1));
		this.dataWatcher.addObject(EntityInfo.DATAWATCHER_CHEST_LOCK, new Byte((byte)0));
	}
	
	public ModularBoat(World par1World, double par2, double par4, double par6, NBTTagCompound tag) {
		super(par1World, par2, par4, par6);
		strings = (ArrayList)readTagInformation(tag);
		list.clear();
		updateBoatAI(this.list);
	}
	
	private List readTagInformation(NBTTagCompound tag){
		if(tag == null) return null;
		NBTTagCompound readTag = tag.getCompoundTag(NBT_TAG_MODULE_COMPUND);
		List<String> list = new ArrayList<String>();
		if(readTag != null){
			for(int i = 0; i < Alphabet.COUNT.ordinal(); i++){
				if(readTag.hasKey(Alphabet.values()[i].toString())){
					list.add(readTag.getString(Alphabet.values()[i].toString()));
				}
			}
		}
		return list;
	}

	@Override
	protected void updateBoatAI(BoatAITaskList list) {
		this.list = list;
		int priority = 0;
		if(strings != null){
			for(int i = 0; i < strings.size(); i++){
				ModuleHelper.addBoatAI(ModuleHelper.parseStringToItemStack((String) strings.get(i)), list, this, (float)i);
				Block block = ModuleHelper.getBlockType(ModuleHelper.parseStringToItemStack((String) strings.get(i)));
				if(block != null){
					this.dataWatcher.updateObject(EntityInfo.DATAWATCHER_TILE_ID, block.blockID);
				}
				priority = i;
			}
		}
		list.addTask(new RopeTask(this, list.size() == 0 ? priority : priority+1));
	}

	@Override
	public Block getDisplayTile() {
		if(this.dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TILE_ID) != -1){
			return Block.blocksList[this.dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TILE_ID)];
		}else{
			return null;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTTagCompound innerTag = new NBTTagCompound();
		if(strings != null){
			for(int i = 0; i < strings.size(); i++){
				innerTag.setString(Alphabet.values()[i].toString(), strings.get(i).toString());
			}
			tag.setCompoundTag(NBT_TAG_MODULE_COMPUND, innerTag);
		}
		super.writeEntityToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTTagCompound innerTag = new NBTTagCompound();
		if(tag.hasKey(NBT_TAG_MODULE_COMPUND)){
			innerTag = tag.getCompoundTag(NBT_TAG_MODULE_COMPUND);
			int i = 0;
			while(!innerTag.hasNoTags()){
				strings.add(innerTag.getString(Alphabet.values()[i].toString()));
				innerTag.removeTag(Alphabet.values()[i].toString());
				i++;
			}
			this.updateBoatAI();
			super.readEntityFromNBT(tag);
		}
	}
}
