package dgrxf.watercraft.entity.boat.ai;

import dgrxf.watercraft.entity.boat.DumbBoat;

public class NewBoatAIDumb extends BoatEntityAIBase {
    
    private DumbBoat boat;
    
    public NewBoatAIDumb(DumbBoat boat) {
        this.boat = boat;
    }

    @Override
    public boolean shouldExecute() {
        return true;
    }
    
    public void updateTask() {}
    
}
