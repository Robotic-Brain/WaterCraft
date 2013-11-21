package dgrxf.watercraft.entity.boat.ai.tasks;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.util.Vector2;

public class RopeTask extends BoatAITaskBase {
	
	public static final float ROPE_LENGTH = 4.0F;
	public static final float SEARCH_RADIOUS = 5.0F;
	
	private AbstractBaseBoat boat;
	private AbstractBaseBoat target;

	public RopeTask(AbstractBaseBoat boat, float priority) {
		super(boat, priority);
		this.boat = boat;
	}
	
	public void setTarget(AbstractBaseBoat target) {
		this.target = target;
	}
	
	@Override
	public void onInteractFirst(EntityPlayer player) {
		ItemStack stack = player.inventory.getCurrentItem();
		
		if (boat.worldObj.isRemote || stack == null || stack.itemID != ModItems.rope.itemID) {
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
			
			Watercraft.printToPlayer("ID set to " + Integer.toString(boat.entityId));
			
		} else if (stack.getItemDamage() == 1 && target == null) {
			
			Watercraft.printToPlayer("searching target");
			
			int id = tag.getInteger("id");		
			double d = boat.width + SEARCH_RADIOUS;
			
			List list = boat.worldObj.getEntitiesWithinAABBExcludingEntity(boat, AxisAlignedBB.getAABBPool().getAABB(boat.posX - d, boat.posY - d, boat.posZ - d, boat.posX + d, boat.posY + d, boat.posZ + d));
			
			if (list != null) {
				Iterator iterator = list.iterator();
				//boolean found = false;

	            while (iterator.hasNext() /*&& !found*/) {	     
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
	            		target = foundBoat;
	            		stack.stackSize--;
	            		break;
	            		//found = true;
	            	}
	            }
			}
			
			stack.setItemDamage(0);
		}


	}
	
	@Override
    public void updateMotion() {
		if (target != null) {
			Watercraft.printToPlayer("target not null!");
			moveToTarget();
		}
    }
    
	
	private void moveToTarget() {
		Vector2 targetBack = new Vector2(target.posX - target.width * Math.cos(target.rotationYaw * Math.PI / 180.0) / 2.0F, target.posZ - target.width * Math.cos(target.rotationYaw * Math.PI / 180.0) / 2.0F);
		Vector2 boatFront = new Vector2(boat.posX + boat.width * Math.cos(boat.rotationYaw * Math.PI / 180.0) / 2.0F, boat.posZ + boat.width * Math.cos(boat.rotationYaw * Math.PI / 180.0) / 2.0F);
		
		Vector2 distance = targetBack.sub(boatFront);
		
		if (distance.length() < ROPE_LENGTH) {
			return;
		}
		
		distance = distance.normalize();
		
		boat.motionX = boat.speedMultiplier * distance.x;
		boat.motionZ = boat.speedMultiplier * distance.y;
	}

}
