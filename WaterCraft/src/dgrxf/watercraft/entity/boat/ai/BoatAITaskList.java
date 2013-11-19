package dgrxf.watercraft.entity.boat.ai;

import java.util.Set;
import java.util.TreeSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;

public class BoatAITaskList extends BoatAIBase {
    
    Set<BoatAITaskBase> tasks = new TreeSet<BoatAITaskBase>();
    
    public BoatAITaskList(AbstractBaseBoat boat) {
        super(boat);
    }
    
    public void addTask(BoatAITaskBase task) {
        // TDO: NOTE: This implementation only allows one element per priority
        tasks.add(task);
    }
    
    public void removeTask(BoatAITaskBase task) {
        tasks.remove(task);
    }
    
    @Override
    public void entityInit() {
        for (BoatAITaskBase task : tasks) {
            task.entityInit();
        }
    }
    
    @Override
    public void preOnUpdate() {
        for (BoatAITaskBase task : tasks) {
            task.preOnUpdate();
        }
    }
    
    @Override
    public void updateMotion() {
        for (BoatAITaskBase task : tasks) {
            task.updateMotion();
        }
    }
    
    @Override
    public void postOnUpdate() {
        for (BoatAITaskBase task : tasks) {
            task.postOnUpdate();
        }
    }
    
    @Override
    public void buoyFound(WCBouyLogic buoy) {
        for (BoatAITaskBase task : tasks) {
            task.buoyFound(buoy);
        }
    }
    
    @Override
    public void onInteractFirst(EntityPlayer player) {
    	for(BoatAITaskBase task : tasks){
    		task.onInteractFirst(player);
    	}
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
    	boolean result = true;
        for(BoatAITaskBase task : tasks){
    		if (!task.attackEntityFrom(source, damage)) {
    		    result = false;
    		}
    	}
        
        return result;
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        // TODO: make saving work
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
    }
}
