package dgrxf.watercraft.item.boat;

import net.minecraft.item.Item;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.enumeration.FuelType;

public class ItemBoatModifierEngine extends Item {
    
    private final FuelType fuel;
    
    public ItemBoatModifierEngine(int id, FuelType fuel) {
        super(id);
        setCreativeTab(Watercraft.boatTab);
        this.fuel = fuel;
    }
    
}
