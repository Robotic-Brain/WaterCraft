package dgrxf.watercraft.lib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.IceTask;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.entity.boat.ai.tasks.LavaTask;
import dgrxf.watercraft.entity.boat.ai.tasks.TankTask;
import dgrxf.watercraft.entity.boat.ai.tasks.VanillaTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.module.CustomModule;
import dgrxf.watercraft.module.ModuleRegistry;

public class ModuleInfo {
	public static void init(){
	 // TODO Translation
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 0), new CustomModule(new ModuleType[]{ModuleType.BOAT}, new ModuleType[]{ModuleType.BOAT}, null, "Adds basic Bouy AI to the boat", DumbTask.class), Watercraft.instance);
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 1), new CustomModule(new ModuleType[]{ModuleType.BOAT, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.BOAT, ModuleType.BLOCK}, null, "Adds vanilla AI to the boat", VanillaTask.class), Watercraft.instance);
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 2), new CustomModule(new ModuleType[]{ModuleType.AI}, new ModuleType[0], null, "Allows boat to break though Ice", IceTask.class), Watercraft.instance);
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 3), new CustomModule(new ModuleType[]{ModuleType.AI}, new ModuleType[0], null, "Allows boat to float on lava and prevents damage", LavaTask.class), Watercraft.instance);
		ModuleRegistry.registerModule(new ItemStack(Block.dirt), new CustomModule(new ModuleType[0], new ModuleType[0], Block.dirt, "Got too much dirt? Stick some on a boat", null), Watercraft.instance);
		ModuleRegistry.registerModule(new ItemStack(Block.chest), new CustomModule(new ModuleType[]{ModuleType.INVENTORY, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.INVENTORY, ModuleType.TANK}, ModBlocks.WC_CHEST.getBlock(), "Adds a chest to the boat", InventoryTask.class, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, false), Watercraft.instance);
		ModuleRegistry.registerModule(new ItemStack(ModBlocks.WC_CHEST.getBlock()), new CustomModule(new ModuleType[]{ModuleType.INVENTORY, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.INVENTORY, ModuleType.TANK}, ModBlocks.WC_CHEST.getBlock(), "Adds a locking chest to the boat", InventoryTask.class, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true), Watercraft.instance);
		ModuleRegistry.registerModule(new ItemStack(ModBlocks.TANK.getBlock()), new CustomModule(new ModuleType[]{ModuleType.TANK, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.INVENTORY, ModuleType.TANK}, ModBlocks.TANK.getBlock(), "Adds a tank to the boat", TankTask.class, 8), Watercraft.instance);
	}
}
