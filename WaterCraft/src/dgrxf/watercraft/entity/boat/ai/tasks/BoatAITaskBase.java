package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAIBase;

/**
 * 
 * BoatAITaskBase
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public abstract class BoatAITaskBase extends BoatAIBase implements Comparable<BoatAITaskBase> {
    
    /**
     * Priority of task Lower numbers mean higher priority
     */
    private float priority;
    
    /**
     * If you need more than two parameters use Object...
     * Make sure you do not use primitives such as int, float, boolean ect. Instead use Integer, Float, Boolean ect.
     */
    public BoatAITaskBase(AbstractBaseBoat boat, Float priority, Object... obj){
    	super(boat);
    	this.priority = priority;
    }
    
    @Override
    public int compareTo(BoatAITaskBase o) {
        return Float.compare(this.priority, o.priority);
    }
}
