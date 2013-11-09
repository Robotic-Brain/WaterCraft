package dgrxf.watercraft.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.util.Vector2;

//import dgrxf.watercraft.tileentity.WCTileEntityDock;

/**
 * Class Made By: DrunkMafia
 * 
 * Class Last Edited By: xandayn Class Last Edited On: 06/11/2013 DD/MM/YYYY
 * 
 */
public class WCEntityBoat extends Entity {
    
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private Vector2 target;
    private WCTileEntityControlUnitDock homeDock = null;
    
    public WCEntityBoat(World world) {
        super(world);
        this.speedMultiplier = 0.07D;
        this.preventEntitySpawning = true;
        this.setSize(1.5F, 0.6F);
        this.yOffset = this.height / 2.0F;
    }
    
    public WCEntityBoat(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y + (double) this.yOffset, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }
    
    public void setTargetLocation(Vector2 target) {
        this.target = target;
    }
    
    private static final int MAX_SPEED = 10;
    
    public void moveToTarget() {
    	float xDist, zDist;
    	if(target == null || worldObj.isRemote){
    		return;
    	}
    	xDist = dgrxf.watercraft.util.MathHelper.calculatePointDistance((float) posX, target.x);
    	zDist = dgrxf.watercraft.util.MathHelper.calculatePointDistance((float) posZ, target.z);
    	if(xDist > 1.0F){
    		if(posX < target.x)
    			this.motionX = 0.1;
    		else if (posX > target.x)
    			this.motionX = -0.1;
    	}
    	else{
    		this.motionX = 0;
    		xDist = 0;
    	}
    	
    	if(zDist > 1.0F){
    		if(posZ < target.z)
    			this.motionZ = 0.1;
    		else if(posZ > target.z)
    			this.motionZ = -0.1;
    	}
    	else{
    		this.motionZ = 0;
    		zDist = 0;
    	}
    	
    	if(zDist == 0 && xDist == 0){
    		target = null;
    	}
    	
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.boundingBox;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !isDead;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else if (!this.worldObj.isRemote && !this.isDead) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + par2 * 10.0F);
            this.setBeenAttacked();
            boolean flag = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) par1DamageSource.getEntity()).capabilities.isCreativeMode;
            
            if (flag || this.getDamageTaken() > 40.0F) {
                
                if (!flag) {
                    this.dropItemWithOffset(ModItems.boat.itemID, 1, 0.0F);
                }
                
                this.setDead();
            }
            
            return true;
        } else {
            return true;
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0F));
    }
    
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        
        moveToTarget();
        
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        
        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        byte b0 = 5;
        double d0 = 0.0D;
        
        for (int i = 0; i < b0; ++i) {
            double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (i + 0) / (double) b0 - 0.125D;
            double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (i + 1) / (double) b0 - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d2, this.boundingBox.maxZ);
            
            if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0D / (double) b0;
            }
        }
        
        double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d4;
        double d5;
        
        if (d3 > 0.26249999999999996D) {
            d4 = Math.cos((double) this.rotationYaw * Math.PI / 180.0D);
            d5 = Math.sin((double) this.rotationYaw * Math.PI / 180.0D);
            
            for (int j = 0; (double) j < 1.0D + d3 * 60.0D; ++j) {
                double d6 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
                double d7 = (double) (this.rand.nextInt(2) * 2 - 1) * 0.7D;
                double d8;
                double d9;
                
                if (this.rand.nextBoolean()) {
                    d8 = this.posX - d4 * d6 * 0.8D + d5 * d7;
                    d9 = this.posZ - d5 * d6 * 0.8D - d4 * d7;
                    this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
                } else {
                    d8 = this.posX + d4 + d5 * d6 * 0.7D;
                    d9 = this.posZ + d5 - d4 * d6 * 0.7D;
                    this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
        
        double d10;
        double d11;
        
        if (this.worldObj.isRemote) {
            if (this.boatPosRotationIncrements > 0) {
                
                d4 = this.posX;
                d5 = this.posY;
                d11 = this.posZ;
                /*d4 = this.posX + (this.boatX - this.posX) / (double) this.boatPosRotationIncrements;
                d5 = this.posY + (this.boatY - this.posY) / (double) this.boatPosRotationIncrements;
                d11 = this.posZ + (this.boatZ - this.posZ) / (double) this.boatPosRotationIncrements;*/
                //d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double) this.rotationYaw);
                d10 = MathHelper.wrapAngleTo180_double(this.rotationYaw);
                this.rotationYaw = (float) ((double) this.rotationYaw + d10 / (double) this.boatPosRotationIncrements);
                //this.rotationPitch = (float) ((double) this.rotationPitch + (this.boatPitch - (double) this.rotationPitch) / (double) this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                d4 = this.posX + this.motionX;
                d5 = this.posY + this.motionY;
                d11 = this.posZ + this.motionZ;
                this.setPosition(d4, d5, d11);
                
                if (this.onGround) {
                    this.motionX *= 0.5D;
                    this.motionY *= 0.5D;
                    this.motionZ *= 0.5D;
                }
                
                this.motionX *= 0.9900000095367432D;
                this.motionY *= 0.949999988079071D;
                this.motionZ *= 0.9900000095367432D;
                
            }
        } else {
            if (d0 < 1.0D) {
                d4 = d0 * 2.0D - 1.0D;
                this.motionY += 0.03999999910593033D * d4;
            } else {
                if (this.motionY < 0.0D) {
                    this.motionY /= 2.0D;
                }
                
                this.motionY += 0.007000000216066837D;
            }
            
            d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            
            if (d4 > 0.35D) {
                d5 = 0.35D / d4;
                this.motionX *= d5;
                this.motionZ *= d5;
                d4 = 0.35D;
            }
            
            if (d4 > d3 && this.speedMultiplier < 0.35D) {
                this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
                
                if (this.speedMultiplier > 0.35D) {
                    this.speedMultiplier = 0.35D;
                }
            } else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
                
                if (this.speedMultiplier < 0.07D) {
                    this.speedMultiplier = 0.07D;
                }
            }
            
            if (this.onGround) {
                this.motionX *= 0.5D;
                this.motionY *= 0.5D;
                this.motionZ *= 0.5D;
            }
            
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            
            if (!(this.isCollidedHorizontally && d3 > 0.2D)) {
                this.motionY *= 0.949999988079071D;
            }
            
            this.rotationPitch = 0.0F;
            d5 = (double) this.rotationYaw;
            d11 = this.prevPosX - this.posX;
            d10 = this.prevPosZ - this.posZ;
            
            if (d11 * d11 + d10 * d10 > 0.001D) {
                d5 = (double) ((float) (Math.atan2(d10, d11) * 180.0D / Math.PI));
            }
            
            double d12 = MathHelper.wrapAngleTo180_double(d5 - (double) this.rotationYaw);
            
            if (d12 > 20.0D) {
                d12 = 20.0D;
            }
            
            if (d12 < -20.0D) {
                d12 = -20.0D;
            }
            
            this.rotationYaw = (float) ((double) this.rotationYaw + d12);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            
            if (!this.worldObj.isRemote) {
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                int l;
                
                if (list != null && !list.isEmpty()) {
                    for (l = 0; l < list.size(); ++l) {
                        Entity entity = (Entity) list.get(l);
                        
                        if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
                            entity.applyEntityCollision(this);
                        }
                    }
                }
                
                for (l = 0; l < 4; ++l) {
                    int i1 = MathHelper.floor_double(this.posX + ((double) (l % 2) - 0.5D) * 0.8D);
                    int j1 = MathHelper.floor_double(this.posZ + ((double) (l / 2) - 0.5D) * 0.8D);
                    
                    for (int k1 = 0; k1 < 2; ++k1) {
                        int l1 = MathHelper.floor_double(this.posY) + k1;
                        int i2 = this.worldObj.getBlockId(i1, l1, j1);
                        
                        if (i2 == Block.snow.blockID) {
                            this.worldObj.setBlockToAir(i1, l1, j1);
                        } else if (i2 == Block.waterlily.blockID) {
                            this.worldObj.destroyBlock(i1, l1, j1, true);
                        }
                    }
                }
            }
        }
        
        /* END NORMAL BOAT STUFF */
        if (homeDock != null) {
            
        }
    }
    
    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        double d5 = -Math.sin((double) (this.rotationYaw * (float) Math.PI / 180.0F));
        double d11 = Math.cos((double) (this.rotationYaw * (float) Math.PI / 180.0F));
        this.motionX += d5 * this.speedMultiplier * 0.05000000074505806D;
        this.motionZ += d11 * this.speedMultiplier * 0.05000000074505806D;
        
        return true;
    }
    
    public void setDocked(WCTileEntityControlUnitDock dock) {
        homeDock = dock;
    }
    
    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        
    }
    
    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        
    }
    
    public void setDamageTaken(float par1) {
        this.dataWatcher.updateObject(19, Float.valueOf(par1));
    }
    
    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public void setTimeSinceHit(int par1) {
        this.dataWatcher.updateObject(17, Integer.valueOf(par1));
    }
    
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(int par1) {
        this.dataWatcher.updateObject(18, Integer.valueOf(par1));
    }
    
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
}
