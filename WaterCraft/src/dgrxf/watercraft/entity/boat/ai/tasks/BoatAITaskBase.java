package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAIBase;

public abstract class BoatAITaskBase extends BoatAIBase implements Comparable<BoatAITaskBase> {
    
    /**
     * Priority of task Lower numbers mean higher priority
     */
    private float priority;
    
    /**
     * If you need more than two parameters use Object...
     */
    public BoatAITaskBase(AbstractBaseBoat boat, float priority, Object... obj){
    	super(boat);
    	this.priority = priority;
    }
    
    @Override
    public int compareTo(BoatAITaskBase o) {
        return Float.compare(this.priority, o.priority);
    }
}
