package dgrxf.watercraft.entity.boat.ai.tasks;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.util.Vector2;
import dgrxf.watercraft.util.Vector3;

public class RopeTask extends BoatAITaskBase {
	
	public static final float ROPE_LENGTH = 4.0F;
	public static final float SEARCH_RADIOUS = 5.0F;
	public static final double SPEED_MULTIPLIER = 0.5;
	public static final double FRICTION = 0.6;
	
	private AbstractBaseBoat boat;
	private AbstractBaseBoat target;
	
	private Vector3 backRopePoint = new Vector3(0, 0, 0);
	private Vector3 frontRopePoint = new Vector3(0, 0, 0);
	
	//TODO clear linking when a boat is destroyed (set -1 to target ID)

	public RopeTask(AbstractBaseBoat boat, float priority) {
		super(boat, priority);
		this.boat = boat;
	}
	
	@Override
	public void postOnUpdate() {
		
	}
	
	public void setTarget(AbstractBaseBoat target) {
		this.target = target;
	}
	
	public AbstractBaseBoat getTarget() {
		return target;
	}
	
	@Override
	public void onInteractFirst(EntityPlayer player) {
		if (boat.worldObj.isRemote) {
			return;
		}
		ItemStack stack = player.inventory.getCurrentItem();
		
		if (stack == null || stack.itemID != ModItems.rope.itemID) {
			return;
		}
		
		NBTTagCompound tag;
        if (stack.getTagCompound() != null) {
            tag = stack.getTagCompound();
        } else {
            tag = new NBTTagCompound();
        }
		
		if (stack.getItemDamage() == 0) {
			stack.setItemDamage(1);
			tag.setInteger("id", boat.entityId);
			stack.setTagCompound(tag);
			
			Watercraft.printToPlayer("ID set to " + Integer.toString(boat.entityId));
			
		} else if (stack.getItemDamage() == 1 && target == null) {
			
			Watercraft.printToPlayer("searching target");
			
			int id = tag.getInteger("id");		
			double d = boat.width + SEARCH_RADIOUS;
			
			List list = boat.worldObj.getEntitiesWithinAABBExcludingEntity(boat, AxisAlignedBB.getAABBPool().getAABB(boat.posX - d, boat.posY - d, boat.posZ - d, boat.posX + d, boat.posY + d, boat.posZ + d));
			
			if (list != null) {
				Iterator iterator = list.iterator();

	            while (iterator.hasNext()) {	     
	            	Entity e = (Entity)iterator.next();
	            	AbstractBaseBoat foundBoat;
	            	
	            	if (e instanceof AbstractBaseBoat) {
	            		foundBoat = (AbstractBaseBoat)e;
	            	} else {
	            		continue;
	            	}
	            	
	            	Watercraft.printToPlayer("processing boat with ID " + Integer.toString(foundBoat.entityId));
	            	Watercraft.printToPlayer("my ID is " + Integer.toString(id));
	            	
	            	if (id == foundBoat.entityId) {
	            		Watercraft.printToPlayer("target found!");
	            		boat.setRopeTargetId(id);
	            		target = foundBoat;
	            		stack.stackSize--;
	            		break;
	            	}
	            }
			}
			stack.setItemDamage(0);
		}
	}
	
	@Override
    public void updateMotion() {
		if (target != null) {
			moveToTarget();
		}
    }
    
	private void moveToTarget() {
		Vector2 targetBack = new Vector2(target.posX + target.width * Math.cos(target.rotationYaw * Math.PI / 180.0) / 2.0F, target.posZ + target.width * Math.sin(target.rotationYaw * Math.PI / 180.0) / 2.0F);
		Vector2 boatFront = new Vector2(boat.posX - boat.width * Math.cos(boat.rotationYaw * Math.PI / 180.0) / 2.0F, boat.posZ - boat.width * Math.sin(boat.rotationYaw * Math.PI / 180.0) / 2.0F);
		
		Vector2 distance = targetBack.sub(boatFront);
		
		if (distance.length() < ROPE_LENGTH) {
			boat.motionX *= FRICTION;
			boat.motionZ *= FRICTION;
			return;
		}
		
		distance = distance.normalize();
		
		boat.motionX = SPEED_MULTIPLIER * distance.x;
		boat.motionZ = SPEED_MULTIPLIER * distance.y;	
	}

}
