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
		
		@Override
		public void onUpdate()
	    {
	
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
	
	 
	
	        double d10;
	        double d11;
	
	        if (this.worldObj.isRemote && this.field_70279_a)
	        {
	        }
	        else
	        {
	     
	
	
	            if (this.onGround)
	            {
	                this.motionX *= 0.5D;
	                this.motionY *= 0.5D;
	                this.motionZ *= 0.5D;
	            }
	
	            this.moveEntity(this.motionX, this.motionY, this.motionZ);
	
	            if (this.isCollidedHorizontally && d3 > 0.2D)
	            {
	                // NOTE: New Code
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
	            }
	        }
	    }
	}
}
