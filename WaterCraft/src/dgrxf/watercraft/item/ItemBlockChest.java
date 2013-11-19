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

public class ItemBlockChest extends ItemBlock{
    
    public ItemBlockChest(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
        maxStackSize = 64;
    }
}
