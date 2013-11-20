package dgrxf.watercraft.item;

import net.minecraft.item.ItemBlock;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.interfaces.IItemModule;
import dgrxf.watercraft.module.ChestModule;

public class ItemBlockChest extends ItemBlock implements IItemModule{
    
    public ItemBlockChest(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
        maxStackSize = 64;
    }

	@Override
	public Class<? extends IBoatModule> getBoatModule() {
		return ChestModule.class;
	}
}
