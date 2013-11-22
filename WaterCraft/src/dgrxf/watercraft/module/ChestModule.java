package dgrxf.watercraft.module;

import net.minecraft.block.Block;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class ChestModule implements IBoatModule {
	
	@Override
	public ModuleType[] getModuleType() {
		return new ModuleType[] {ModuleType.INVENTORY, ModuleType.BLOCK};
	}

	@Override
	public Block getBlockType() {
		return ModBlocks.chest;
	}

	@Override
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f) {
		list.addTask(new InventoryTask(boat, f, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true));
	}

}
