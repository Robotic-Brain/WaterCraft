package dgrxf.watercraft.lib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.BlockRegistry;
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
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 0), new CustomModule(new ModuleType[]{ModuleType.BOAT}, new ModuleType[]{ModuleType.BOAT}, null, DumbTask.class), Watercraft.instance, "Adds basic Bouy AI to the boat");
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 1), new CustomModule(new ModuleType[]{ModuleType.BOAT, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.BOAT, ModuleType.BLOCK}, null, VanillaTask.class), Watercraft.instance, "Adds vanilla AI to the boat");
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 2), new CustomModule(new ModuleType[]{ModuleType.AI}, new ModuleType[0], null, IceTask.class), Watercraft.instance, "Adds Ice Reinforcement, allowing it to break though Ice");
		ModuleRegistry.registerModule(new ItemStack(ModItems.boatModule, 0, 3), new CustomModule(new ModuleType[]{ModuleType.AI}, new ModuleType[0], null, LavaTask.class), Watercraft.instance, "Adds Lava Reinforcement, allowing it to travel on lava and keep it's occupants safe");
		ModuleRegistry.registerModule(new ItemStack(Block.dirt), new CustomModule(new ModuleType[0], new ModuleType[0], Block.dirt, null), Watercraft.instance, "Got too much dirt? Stick some one a boat");
		ModuleRegistry.registerModule(new ItemStack(Block.chest), new CustomModule(new ModuleType[]{ModuleType.INVENTORY, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.INVENTORY, ModuleType.TANK}, BlockRegistry.WC_CHEST.getBlock(), InventoryTask.class, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true), Watercraft.instance, "Adds a chest to the boat");
		ModuleRegistry.registerModule(new ItemStack(BlockRegistry.WC_CHEST.getBlock()), new CustomModule(new ModuleType[]{ModuleType.INVENTORY, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.INVENTORY, ModuleType.TANK}, BlockRegistry.WC_CHEST.getBlock(), InventoryTask.class, GuiHandler.VANILLA_CHEST_ID, Watercraft.instance, 27, true), Watercraft.instance, "Adds a chest to the boat");
		ModuleRegistry.registerModule(new ItemStack(BlockRegistry.TANK.getBlock()), new CustomModule(new ModuleType[]{ModuleType.TANK, ModuleType.ACTIVATABLE}, new ModuleType[]{ModuleType.INVENTORY, ModuleType.TANK}, BlockRegistry.TANK.getBlock(), TankTask.class, 8), Watercraft.instance, "Adds a tank to the boat");
	}
}
