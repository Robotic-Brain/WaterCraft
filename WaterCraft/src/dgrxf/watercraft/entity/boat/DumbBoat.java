package dgrxf.watercraft.entity.boat;

import net.minecraft.world.World;
import dgrxf.watercraft.entity.WCEntityBoatBase;
import dgrxf.watercraft.entity.boat.ai.BoatAIDumb;

public class DumbBoat extends WCEntityBoatBase{

    public DumbBoat(World par1World) {
        super(par1World);
    }
    
    @Override
    protected void setBoatAI() {
        this.ai = new BoatAIDumb(this);
    }
}
