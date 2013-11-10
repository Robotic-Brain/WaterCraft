package dgrxf.watercraft.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class BuoyParticle extends EntityFX {

	public BuoyParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, 0, 0, 0);
		motionX = vx;
		motionY = vy;
		motionZ = vz;
		particleMaxAge = 100000;
		particleGravity = 0.1F;
		
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

}
