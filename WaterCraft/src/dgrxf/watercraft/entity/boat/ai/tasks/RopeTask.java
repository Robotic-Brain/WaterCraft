package dgrxf.watercraft.entity.boat.ai.tasks;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
	public static final float SEARCH_RADIUS = 5.0F;
	public static final double SPEED_MULTIPLIER = 0.5;
	public static final double FRICTION = 0.8;
	
	private AbstractBaseBoat boat;
	private AbstractBaseBoat target;
	
	private Vector3 backRopePoint = new Vector3(0, 0, 0);
	private Vector3 frontRopePoint = new Vector3(0, 0, 0);
	
	private UUID targetID;
	
	//TODO clear linking when a boat is destroyed (set -1 to target ID)

	public RopeTask(AbstractBaseBoat boat, float priority) {
		super(boat, priority);
		this.boat = boat;
		this.targetID = null;
	}
	
	@Override
	public void postOnUpdate() {
		if ((target == null || target.isDead) && boat.getRopeTargetId() >= 0 && targetID != null) {	
			System.out.println("OMG I lost my partner!");
			if (!searchForBoat(targetID)) {
				targetID = null;
				boat.setRopeTargetId(-1);
			}
		}
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
			System.out.println("Target entity ID is: " + Integer.toString(boat.getRopeTargetId()));
			System.out.println("Target is null? " + Boolean.toString(target == null));
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
			tag.setLong("idLeast", boat.getUniqueID().getLeastSignificantBits());
			tag.setLong("idMost", boat.getUniqueID().getMostSignificantBits());
			stack.setTagCompound(tag);
			
			System.out.println("ID set to " + boat.getUniqueID().toString());
			
		} else if (stack.getItemDamage() == 1 && target == null) {
			
			System.out.println("searching target");
			
			long idLeast = tag.getLong("idLeast");
			long idMost = tag.getLong("idMost");
			
			if (searchForBoat(new UUID(idMost, idLeast))) {
				stack.stackSize--;
			}
			
			stack.setItemDamage(0);
		}
	}
	
	private boolean searchForBoat(UUID id) {
		double d = boat.width + SEARCH_RADIUS;			
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
            	
            	System.out.println("I found a boat with ID " + foundBoat.getUniqueID().toString());
            	System.out.println("I'm searching for a boat with ID " + id.toString());
            	
            	if (id.equals(foundBoat.getUniqueID())) {
            		System.out.println("I found my boat! Linked!");
            		boat.setRopeTargetId(foundBoat.entityId);
            		target = foundBoat;
            		targetID = id;            		
            		return true;            		
            	}
            }
		}
		
		return false;
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
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		
		if (target!= null && !target.isDead) {
			compound.setLong("targetIDleast", target.getUniqueID().getLeastSignificantBits());
			compound.setLong("targetIDmost", target.getUniqueID().getMostSignificantBits());
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		
		targetID = new UUID(compound.getLong("targetIDmost"), compound.getLong("targetIDleast"));
	}

}
