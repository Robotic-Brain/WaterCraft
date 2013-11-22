package dgrxf.watercraft.lib;

import net.minecraft.block.Block;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.util.ModuleRegistry;

public class ModuleInfo {
	public static void init(){
        ModuleRegistry.registerModule(new dgrxf.watercraft.module.DumbModule());
        ModuleRegistry.registerModule(new dgrxf.watercraft.module.ChestModule());
        ModuleRegistry.registerVanillaModule(new dgrxf.watercraft.module.VanillaItemModule(new ModuleType[] {ModuleType.INVENTORY, ModuleType.BLOCK}, ModBlocks.chest, InventoryTask.class, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true), Block.chest.blockID);
	}
}
