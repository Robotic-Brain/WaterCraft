package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.util.Vector2;

public class RopeTask extends BoatAITaskBase {
	
	public static final float ROPE_LENGTH = 2.0F;
	
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
    public void updateMotion() {
		if (target != null) {
			moveToTarget();
		}
    }
    
	
	private void moveToTarget() {
		Vector2 targetBack = new Vector2(target.posX - target.width * Math.cos(target.rotationYaw * Math.PI / 180.0), target.posZ - target.width * Math.cos(target.rotationYaw * Math.PI / 180.0));
		Vector2 boatFront = new Vector2(boat.posX + boat.width * Math.cos(boat.rotationYaw * Math.PI / 180.0), boat.posZ + boat.width * Math.cos(boat.rotationYaw * Math.PI / 180.0));
		
		Vector2 direction = targetBack.sub(boatFront);
		
		if (direction.length() < ROPE_LENGTH) {
			return;
		}
		
		boat.motionX = direction.x;
		boat.motionZ = direction.y;
	}

}
