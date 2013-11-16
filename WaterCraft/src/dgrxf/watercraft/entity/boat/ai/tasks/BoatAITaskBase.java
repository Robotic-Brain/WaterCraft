package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.WCEntityBoatBase;
import dgrxf.watercraft.entity.boat.ai.BoatAIBase;

public abstract class BoatAITaskBase extends BoatAIBase implements Comparable<BoatAITaskBase> {
    
    private float priority;
    
    public BoatAITaskBase(WCEntityBoatBase boat, float priority) {
        super(boat);
        this.priority = priority;
    }
    
    @Override
    public int compareTo(BoatAITaskBase o) {
        return Float.compare(this.priority, o.priority);
    }
}
