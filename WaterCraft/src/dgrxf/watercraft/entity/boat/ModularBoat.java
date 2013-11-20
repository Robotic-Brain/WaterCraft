package dgrxf.watercraft.entity.boat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.lib.EntityInfo;
import dgrxf.watercraft.util.ModuleHelper;

public class ModularBoat extends AbstractBaseBoat{
	
	NBTTagCompound tag;
	Object modID;
	ArrayList strings = new ArrayList<String>();
	private BoatAITaskList list;
	
	private static final String NBT_TAG_MODULE_COMPUND = "Modules";
	
	public ModularBoat(World world){
		super(world);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(EntityInfo.DATAWATCHER_TILE_ID, new Integer(-1));
	}
	
	public ModularBoat(World par1World, double par2, double par4, double par6, NBTTagCompound tag, Object modID) {
		super(par1World, par2, par4, par6);
		this.tag = tag;
		this.modID = modID;
		strings = (ArrayList)readTagInformation(tag);
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
	protected void setBoatAI(BoatAITaskList list) {
		if(strings != null){
			for(int i = 0; i < strings.size(); i++){
				Class<? extends IBoatModule> tempClass = null;
				try {
					tempClass = (Class<? extends IBoatModule>) Class.forName(strings.get(i).toString());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				ModuleHelper.addBoatAI(tempClass, list, this, (float)i);
				Block block = ModuleHelper.getBlockType(tempClass);
				if(block != null){
					this.dataWatcher.updateObject(EntityInfo.DATAWATCHER_TILE_ID, block.blockID);
				}
			}
		}
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
				System.out.println(strings.get(i).toString());
				i++;
			}
			this.setBoatAI(list);
			super.readEntityFromNBT(tag);
		}
	}
}
