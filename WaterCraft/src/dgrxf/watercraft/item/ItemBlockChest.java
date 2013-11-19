package dgrxf.watercraft.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class ItemBlockChest extends ItemBlock implements IBoatModule{
    
    public ItemBlockChest(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
        maxStackSize = 64;
    }

	@Override
	public ModuleType getModuleType() {
		return ModuleType.BLOCK;
	}

	@Override
	public Block getBlockType() {
		return ModBlocks.chest;
	}

	@Override
	public void addBoatAI(List<Class<? extends BoatAITaskBase>> list) {
		list.add(InventoryTask.class);
	}

	@Override
	public void writeModuleInfoToNBT(NBTTagCompound tag) {
		List<Class<? extends BoatAITaskBase>> list = new ArrayList();
		addBoatAI(list);
		ModuleType type = getModuleType();
		Block block = getBlockType();
		int i = 0;
		for(Class<? extends BoatAITaskBase> inList : list){
			tag.setString("AI" + i, inList.getName());
			++i;
		}
		
		tag.setString("Type", type.toString());
		tag.setInteger("Block", block.blockID);
		
	}
}
