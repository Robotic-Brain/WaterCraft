package dgrxf.watercraft.item.boat;

import net.minecraft.item.Item;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.enumeration.FuelType;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.lib.ItemInfo;

public class ItemBoatModifierEngine extends Item {
    
    private final FuelType fuel;
    
    public ItemBoatModifierEngine(int id, FuelType fuel) {
        super(id);
        setUnlocalizedName(ItemInfo.ENGINE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.boatTab);
        this.fuel = fuel;
    }
    
}
