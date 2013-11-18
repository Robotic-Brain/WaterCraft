package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAIBase;

public abstract class BoatAITaskBase extends BoatAIBase implements Comparable<BoatAITaskBase> {
    
    /**
     * Priority of task Lower numbers mean higher priority
     */
    private float priority;
    
    public BoatAITaskBase(AbstractBaseBoat boat, float priority) {
        super(boat);
        this.priority = priority;
    }
    
    @Override
    public int compareTo(BoatAITaskBase o) {
        return Float.compare(this.priority, o.priority);
    }
}
