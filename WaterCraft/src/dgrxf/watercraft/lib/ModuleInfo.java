package dgrxf.watercraft.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.ai.tasks.IceTask;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.entity.boat.ai.tasks.LavaTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.module.ChestModule;
import dgrxf.watercraft.module.CustomModule;
import dgrxf.watercraft.module.TankModule;
import dgrxf.watercraft.util.ModuleRegistry;

public class ModuleInfo {
	public static void init(){
		ModuleRegistry.registerModule(new ItemStack(Block.ice), new CustomModule(new ModuleType[]{ModuleType.BOAT}, null, IceTask.class));
		ModuleRegistry.registerModule(new ItemStack(Item.bucketLava), new CustomModule(new ModuleType[]{ModuleType.BOAT}, null, LavaTask.class));
		ModuleRegistry.registerModule(new ItemStack(ModBlocks.chest), new ChestModule());
		ModuleRegistry.registerModule(new ItemStack(ModBlocks.tank), new TankModule());
		ModuleRegistry.registerModule(new ItemStack(Block.dirt), new CustomModule(new ModuleType[]{}, Block.dirt, null));
		ModuleRegistry.registerModule(new ItemStack(Block.chest), new CustomModule(new ModuleType[]{ModuleType.INVENTORY}, null, InventoryTask.class, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true));
	}
}
