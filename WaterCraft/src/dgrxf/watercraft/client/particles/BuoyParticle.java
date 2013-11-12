package dgrxf.watercraft.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.util.Vector3;

public class BuoyParticle extends EntityFX {
	
	private static final float gravity = 0.1F;
	private static final int FLY_TIME = 30;
	
	private Vector3 startingVelocity;

	public BuoyParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, 0, 0, 0);
		startingVelocity = new Vector3((float)vx, (float)vy, (float)vz);
		particleMaxAge = FLY_TIME;
		particleScale = 1;
		
		setParticleIcon(ModBlocks.buoy.getParticleIcon());
	}
	
	@Override
	public void onUpdate() {
	    motionX = startingVelocity.x;
	    motionY = startingVelocity.y - particleAge * 0.04D * (double)this.gravity;
	    motionZ = startingVelocity.z;
	    
		super.onUpdate();
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
