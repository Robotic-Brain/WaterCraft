	package dgrxf.watercraft.entity;
	
	import java.util.List;
	import java.util.Random;
	
	import net.minecraft.block.Block;
	import net.minecraft.block.material.Material;
	import net.minecraft.entity.Entity;
	import net.minecraft.entity.EntityLivingBase;
	import net.minecraft.entity.item.EntityBoat;
	import net.minecraft.nbt.NBTTagCompound;
	import net.minecraft.util.AxisAlignedBB;
	import net.minecraft.util.MathHelper;
	import net.minecraft.world.World;
	import dgrxf.watercraft.item.ModItems;
	
	public class EntityIceBoat extends WCEntityBoatBase {
		
	    private boolean field_70279_a;
	    private double speedMultiplier;
	    private int boatPosRotationIncrements;
	    private double boatX;
	    private double boatY;
	    private double boatZ;
	    private double boatYaw;
	    private double boatPitch;
	    Random myRandom = new Random();
		
		public EntityIceBoat(World par1World, double par2, double par4, double par6) {
			super(par1World, par2, par4, par6);
		}
	
		public EntityIceBoat(World par1World) {
			super(par1World);
	        this.field_70279_a = true;
	        this.speedMultiplier = 0.07D;
	        this.preventEntitySpawning = true;
	        this.setSize(1.5F, 0.6F);
	        this.yOffset = this.height / 2.0F;
	        this.ridable = true;
		}
		
		@Override
		public void writeToNBT(NBTTagCompound compound) {
			super.writeToNBT(compound);
		}
		    
		@Override
		public void readFromNBT(NBTTagCompound compound) {
			super.readFromNBT(compound);
		}
		
		@Override
		 public void updateRiderPosition()
	    {
	        if (this.riddenByEntity != null)
	        {
	            double d0 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
	            double d1 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
	            this.riddenByEntity.setPosition(this.posX + d0, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
	        }
	    }
		
		@Override
		public void onUpdate()
	    {
			this.onEntityUpdate();
			
	        if (this.getTimeSinceHit() > 0)
	        {
	            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
	        }
	
	        if (this.getDamageTaken() > 0.0F)
	        {
	            this.setDamageTaken(this.getDamageTaken() - 1.0F);
	        }
	
	        this.prevPosX = this.posX;
	        this.prevPosY = this.posY;
	        this.prevPosZ = this.posZ;
	        byte b0 = 5;
	        double d0 = 0.0D;
	        
	        int randomness = 0;
	    	for (int int1 = -2; int1 < 2; int1++) {
	    		for (int int2 = -2; int2 < 1; int2++) {
	    			for (int int3 = -2; int3 < 2; int3++) {
	    				if (worldObj.getBlockId(((int)posX + int1), ((int)posY + int2), ((int)posZ + int3)) == Block.ice.blockID) {
	    					randomness = myRandom.nextInt(5);
	    					if (randomness != 3) {
	    						if(posY + int2 > (int)posY)
	    							this.worldObj.destroyBlock(((int)posX + int1), ((int)posY + int2), ((int)posZ + int3), false);
	    						else
	    							this.worldObj.setBlock(((int)posX + int1), ((int)posY + int2), ((int)posZ + int3), Block.waterStill.blockID);//((int)posX + int1), ((int)posY + int2), ((int)posZ + int3), false);
	    						//this.addVelocity(par1, par3, par5);
	    					}
	    				}
	    			}
	    		}
	    	}
	        
	        for (int i = 0; i < b0; ++i)
	        {
	            double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i + 0) / (double)b0 - 0.125D;
	            double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i + 1) / (double)b0 - 0.125D;
	            AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d2, this.boundingBox.maxZ);
	
	            if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
	            {
	                d0 += 1.0D / (double)b0;
	            }
	        }
	
	        double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
	        double d4;
	        double d5;
	
	        if (d3 > 0.26249999999999996D)
	        {
	            d4 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
	            d5 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);
	
	            for (int j = 0; (double)j < 1.0D + d3 * 60.0D; ++j)
	            {
	                double d6 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
	                double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
	                double d8;
	                double d9;
	
	                if (this.rand.nextBoolean())
	                {
	                    d8 = this.posX - d4 * d6 * 0.8D + d5 * d7;
	                    d9 = this.posZ - d5 * d6 * 0.8D - d4 * d7;
	                    this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
	                }
	                else
	                {
	                    d8 = this.posX + d4 + d5 * d6 * 0.7D;
	                    d9 = this.posZ + d5 - d4 * d6 * 0.7D;
	                    this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
	                }
	            }
	        }
	
	        double d10;
	        double d11;
	
	        if (this.worldObj.isRemote && this.field_70279_a)
	        {
	            if (this.boatPosRotationIncrements > 0)
	            {
	                d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
	                d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
	                d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
	                d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
	                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
	                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
	                --this.boatPosRotationIncrements;
	                this.setPosition(d4, d5, d11);
	                this.setRotation(this.rotationYaw, this.rotationPitch);
	            }
	            else
	            {
	                d4 = this.posX + this.motionX;
	                d5 = this.posY + this.motionY;
	                d11 = this.posZ + this.motionZ;
	                this.setPosition(d4, d5, d11);
	
	                if (this.onGround)
	                {
	                    this.motionX *= 0.5D;
	                    this.motionY *= 0.5D;
	                    this.motionZ *= 0.5D;
	                }
	
	                this.motionX *= 0.9900000095367432D;
	                this.motionY *= 0.949999988079071D;
	                this.motionZ *= 0.9900000095367432D;
	            }
	        }
	        else
	        {
	            if (d0 < 1.0D)
	            {
	                d4 = d0 * 2.0D - 1.0D;
	                this.motionY += 0.03999999910593033D * d4;
	            }
	            else
	            {
	                if (this.motionY < 0.0D)
	                {
	                    this.motionY /= 2.0D;
	                }
	
	                this.motionY += 0.007000000216066837D;
	            }
	
	            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
	            {
	                d4 = (double)((EntityLivingBase)this.riddenByEntity).moveForward;
	                if (d4 > 0.0D)
	                {
	                    d5 = -Math.sin((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
	                    d11 = Math.cos((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
	                    this.motionX += d5 * this.speedMultiplier * 0.05000000074505806D;
	                    this.motionZ += d11 * this.speedMultiplier * 0.05000000074505806D;
	                }
	            }
	
	            d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
	
	            if (d4 > 0.35D)
	            {
	                d5 = 0.35D / d4;
	                this.motionX *= d5;
	                this.motionZ *= d5;
	                d4 = 0.35D;
	            }
	
	            if (d4 > d3 && this.speedMultiplier < 0.35D)
	            {
	                this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
	
	                if (this.speedMultiplier > 0.35D)
	                {
	                    this.speedMultiplier = 0.35D;
	                }
	            }
	            else
	            {
	                this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
	
	                if (this.speedMultiplier < 0.07D)
	                {
	                    this.speedMultiplier = 0.07D;
	                }
	            }
	
	            if (this.onGround)
	            {
	                this.motionX *= 0.5D;
	                this.motionY *= 0.5D;
	                this.motionZ *= 0.5D;
	            }
	
	            this.moveEntity(this.motionX, this.motionY, this.motionZ);
	
	            if (this.isCollidedHorizontally && d3 > 0.2D)
	            {
	            	boolean safe = true;
	            	for (int int1 = -2; int1 < 2; int1++) {
	            		for (int int2 = -2; int2 < 1; int2++) {
	            			for (int int3 = -2; int3 < 2; int3++) {
	            				if (worldObj.getBlockId(((int)posX + int1), ((int)posY + int2), ((int)posZ + int3)) != Block.ice.blockID  && 
	            						(worldObj.getBlockId(((int)posX + int1), ((int)posY + int2), ((int)posZ + int3)) != Block.waterMoving.blockID) &&
	            						(worldObj.getBlockId(((int)posX + int1), ((int)posY + int2), ((int)posZ + int3)) != Block.waterStill.blockID) && 
	            						(worldObj.getBlockId(((int)posX + int1), ((int)posY + int2), ((int)posZ + int3)) != 0)) {
	            						safe = false;
	            						break;
	            				}
	            			}
	            		}
	            	}
	                if (!this.worldObj.isRemote && !this.isDead && !safe) {
	                
	                    this.setDead();
	                    this.dropItemWithOffset(ModItems.iceBoat.itemID, 1, 0.0F);
	                }
	                else
	                {
		                this.motionX *= 0.9900000095367432D;
		                this.motionY *= 0.949999988079071D;
		                this.motionZ *= 0.9900000095367432D;
		            }
	
	            this.rotationPitch = 0.0F;
	            d5 = (double)this.rotationYaw;
	            d11 = this.prevPosX - this.posX;
	            d10 = this.prevPosZ - this.posZ;
	
	            if (d11 * d11 + d10 * d10 > 0.001D)
	            {
	                d5 = (double)((float)(Math.atan2(d10, d11) * 180.0D / Math.PI));
	            }
	
	            double d12 = MathHelper.wrapAngleTo180_double(d5 - (double)this.rotationYaw);
	
	            if (d12 > 20.0D)
	            {
	                d12 = 20.0D;
	            }
	
	            if (d12 < -20.0D)
	            {
	                d12 = -20.0D;
	            }
	
	            this.rotationYaw = (float)((double)this.rotationYaw + d12);
	            this.setRotation(this.rotationYaw, this.rotationPitch);
	
	            if (!this.worldObj.isRemote)
	            {
	                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
	                int l;
	
	                if (list != null && !list.isEmpty())
	                {
	                    for (l = 0; l < list.size(); ++l)
	                    {
	                        Entity entity = (Entity)list.get(l);
	
	                        if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat)
	                        {
	                            entity.applyEntityCollision(this);
	                        }
	                    }
	                }
	
	                for (l = 0; l < 4; ++l)
	                {
	                    int i1 = MathHelper.floor_double(this.posX + ((double)(l % 2) - 0.5D) * 0.8D);
	                    int j1 = MathHelper.floor_double(this.posZ + ((double)(l / 2) - 0.5D) * 0.8D);
	
	                    for (int k1 = 0; k1 < 2; ++k1)
	                    {
	                        int l1 = MathHelper.floor_double(this.posY) + k1;
	                        int i2 = this.worldObj.getBlockId(i1, l1, j1);
	
	                        if (i2 == Block.snow.blockID)
	                        {
	                            this.worldObj.setBlockToAir(i1, l1, j1);
	                        }
	                        else if (i2 == Block.waterlily.blockID)
	                        {
	                            this.worldObj.destroyBlock(i1, l1, j1, true);
	                        }
	                        else if (i2 == Block.ice.blockID)
	                        {
	                            this.worldObj.destroyBlock(i1, l1, j1, true);
	                        }
	                    }
	                }
	
	                if (this.riddenByEntity != null && this.riddenByEntity.isDead)
	                {
	                    this.riddenByEntity = null;
	                }
	            }
	        }
	    }
	}
}
