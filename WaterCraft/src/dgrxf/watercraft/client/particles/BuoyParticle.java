package dgrxf.watercraft.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class BuoyParticle extends EntityFX {
	
	private static final float gravity = 0;
	private static final int FLY_TIME = 40;
	private static final float MAX_HEIGHT = 2.0F;


	public BuoyParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, 0, 0, 0);
		noClip = true;
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		particleMaxAge = FLY_TIME;
		particleGravity = gravity;
		
		//TODO particle age
		//func_110125_a(NEEDS AN ICON);

	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
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
	
	public static float getMaxHeight() {
		return MAX_HEIGHT;
	}

}
