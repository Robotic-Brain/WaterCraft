package dgrxf.watercraft.entity.boat.ai;

import java.util.Set;
import java.util.TreeSet;

import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.WCEntityBoatBase;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;

public class BoatAITaskList extends BoatAIBase {
    
    Set<BoatAITaskBase> tasks = new TreeSet<BoatAITaskBase>();
    
    public BoatAITaskList(WCEntityBoatBase boat) {
        super(boat);
    }
    
    public void addTask(BoatAITaskBase task) {
        tasks.add(task);
    }
    
    public void removeTask(BoatAITaskBase task) {
        tasks.remove(task);
    }
    
    public void entityInit() {
        for (BoatAITaskBase task : tasks) {
            task.entityInit();
        }
    }
    
    public void preOnUpdate() {
        for (BoatAITaskBase task : tasks) {
            task.preOnUpdate();
        }
    }
    
    public void updateMotion() {
        for (BoatAITaskBase task : tasks) {
            task.updateMotion();
        }
    }
    
    public void postOnUpdate() {
        for (BoatAITaskBase task : tasks) {
            task.postOnUpdate();
        }
    }
    
    public void buoyFound(WCBouyLogic buoy) {
        for (BoatAITaskBase task : tasks) {
            task.buoyFound(buoy);
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        // TODO: make saving work
    }

    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {}
}
