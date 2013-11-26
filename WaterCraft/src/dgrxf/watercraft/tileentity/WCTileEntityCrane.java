package dgrxf.watercraft.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.module.ModuleRegistry;

public class WCTileEntityCrane extends DirectionalTileEntity{
	
	@Override
	public void updateEntity() {
		AbstractBaseBoat b = getBoatInBounds(getBlockDirection());
		if(b != null){
			ItemStack[] temp = b.getBoatModules();
			for(ItemStack item : temp){
				int tempInt = ModuleRegistry.isItemRegisteredAndGetID(item);
			}
		}
	}
	
	public AbstractBaseBoat getBoatInBounds(ForgeDirection d){
        int tempX = xCoord + d.offsetX * 3;
        int tempZ = zCoord + d.offsetZ * 3;
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(tempX - 1, yCoord - 1, tempZ - 1, tempX + 1, yCoord + 1, tempZ + 1);
        List list = worldObj.getEntitiesWithinAABB(AbstractBaseBoat.class, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof AbstractBaseBoat) {
            	System.out.println("test");
                return (AbstractBaseBoat) e;
            }
        }
        
        return null;
	}
	
}
