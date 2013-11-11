package dgrxf.watercraft.enumeration;

import net.minecraft.item.Item;

public enum FuelType {

	COAL(Item.coal),
	NONE(null);
	
	private final Item fuel;
	
	private FuelType(Item fuel){
		this.fuel = fuel;
	}
}
