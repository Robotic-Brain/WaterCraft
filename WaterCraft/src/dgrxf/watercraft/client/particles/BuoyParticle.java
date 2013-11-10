package dgrxf.watercraft.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class BuoyParticle extends EntityFX {

	public BuoyParticle(World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, vx, vy, vz);
		particleAge = 100000;
		
		//TODO particle age
		//func_110125_a(NEEDS AN ICON);

	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}

}
