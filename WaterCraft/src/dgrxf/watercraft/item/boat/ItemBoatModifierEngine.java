package dgrxf.watercraft.item.boat;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.enumeration.FuelType;
import dgrxf.watercraft.enumeration.ModifierType;
import dgrxf.watercraft.item.ItemModifier;
import dgrxf.watercraft.lib.ItemInfo;

public class ItemBoatModifierEngine extends ItemModifier {

	private final FuelType fuel;
	
	public ItemBoatModifierEngine(int id, FuelType fuel) {
		super(id, ModifierType.ENGINE);
		setUnlocalizedName(ItemInfo.ENGINE_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.boatTab);
		this.fuel = fuel;
	}
	
	
	
}
