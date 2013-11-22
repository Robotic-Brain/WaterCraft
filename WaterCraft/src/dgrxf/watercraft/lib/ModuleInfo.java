package dgrxf.watercraft.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.ai.tasks.IceTask;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.entity.boat.ai.tasks.LavaTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.module.ChestModule;
import dgrxf.watercraft.module.TankModule;
import dgrxf.watercraft.module.VanillaItemModule;
import dgrxf.watercraft.util.ModuleRegistry;

public class ModuleInfo {
	public static void init(){
		ModuleRegistry.registerModule(Block.ice, new VanillaItemModule(new ModuleType[]{ModuleType.BOAT}, null, IceTask.class));
		ModuleRegistry.registerModule(Item.bucketLava, new VanillaItemModule(new ModuleType[]{ModuleType.BOAT}, null, LavaTask.class));
		ModuleRegistry.registerModule(ModBlocks.chest, new ChestModule());
		ModuleRegistry.registerModule(ModBlocks.tank, new TankModule());
		ModuleRegistry.registerModule(Block.dirt, new VanillaItemModule(new ModuleType[]{ModuleType.BLOCK}, Block.dirt, null));
		ModuleRegistry.registerModule(Block.chest, new VanillaItemModule(new ModuleType[]{ModuleType.INVENTORY}, null, InventoryTask.class, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true));
	}
}
