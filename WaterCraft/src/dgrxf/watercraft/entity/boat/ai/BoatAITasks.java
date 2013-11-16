package dgrxf.watercraft.entity.boat.ai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.WCEntityBoatBase;

public class BoatAITasks extends BoatAIBase {
    
    private static final String NBT_TASK_LIST = "taskList";
    
    private EntityAITasks tasks;
    private List<BoatEntityAIBase> taskObjects = new ArrayList<BoatEntityAIBase>();
    
    public BoatAITasks(WCEntityBoatBase boat) {
        super(boat);
        tasks = new EntityAITasks(boat.worldObj != null && boat.worldObj.theProfiler != null ? boat.worldObj.theProfiler : null);
    }
    
    public void addTask(int par1, BoatEntityAIBase par2EntityAIBase)
    {
        tasks.addTask(par1, par2EntityAIBase);
        taskObjects.add(par2EntityAIBase);
    }
    
    public void removeTask(EntityAIBase par1EntityAIBase) {
        tasks.removeTask(par1EntityAIBase);
        taskObjects.remove(par1EntityAIBase);
    }
    
    /**
     * Use this to move in new direction
     */
    public void updateMotion() {
        tasks.onUpdateTasks();
    }

    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        /*NBTTagList taskList = new NBTTagList();
        
        for (BoatEntityAIBase task : taskObjects) {
            NBTTagCompound taskData = new NBTTagCompound();
            task.writeToNBT(taskData);
            taskList.appendTag(taskData);
        }
        
        par1nbtTagCompound.setTag(NBT_TASK_LIST, taskList);*/
    }

    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
        /*NBTTagList taskList = (NBTTagList)par1nbtTagCompound.getTag(NBT_TASK_LIST);
        
        for (BoatEntityAIBase task : taskObjects) {
            NBTTagCompound taskData = new NBTTagCompound();
            task.writeToNBT(taskData);
            taskList.appendTag(taskData);
        }*/
        
        // TODO: make this work (Tasks saving to NBT)
    }
}
