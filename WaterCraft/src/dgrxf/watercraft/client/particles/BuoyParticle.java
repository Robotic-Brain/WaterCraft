package dgrxf.watercraft.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import dgrxf.watercraft.util.Vector3;

public class BuoyParticle extends EntityFX {
	
	private static final float gravity = 0.1F;
	private static final int FLY_TIME = 30;
	private Vector3 start;

	public BuoyParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, vx, vy, vz);
		noClip = true;
		start = new Vector3((float)vx, (float)vy, (float)vz);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		particleMaxAge = FLY_TIME;
		
		//TODO particle age
		//func_110125_a(NEEDS AN ICON);

	}
	
	// Overriding to remove friction
	@Override
	public void onUpdate() {
		super.onUpdate();

	    this.motionX = start.x;
	    this.motionY = start.y - particleAge * 0.04D * (double)this.gravity;
	    this.motionZ = start.z;
	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}
	
	public static float getGravity() {
		return gravity * 0.04F;
	}
	
	public static float getFlyTime() {
		return FLY_TIME;
	}

}
