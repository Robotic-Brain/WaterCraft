package dgrxf.watercraft.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;

public class WCEntityBoatBase extends Entity
{
    protected boolean isEmpty;
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    protected double boatX;
    protected double boatY;
    protected double boatZ;
    protected double boatYaw;
    protected double boatPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;

    public WCEntityBoatBase(World par1World)
    {
        super(par1World);
        this.isEmpty = true;
        this.speedMultiplier = 0.07D;
        this.preventEntitySpawning = true;
        this.setSize(1.5F, 0.6F);
        this.yOffset = this.height / 2.0F;
    }
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    
    @Override
    protected void entityInit()
    {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0F));
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity par1Entity)
    {
        return par1Entity.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB getBoundingBox()
    {
        return this.boundingBox;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return true;
    }

    public WCEntityBoatBase(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return (double)this.height * 0.0D - 0.30000001192092896D;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (!this.worldObj.isRemote && !this.isDead)
        {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + par2 * 10.0F);
            this.setBeenAttacked();
            boolean flag = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;

            if (flag || this.getDamageTaken() > 40.0F)
            {
                if (this.riddenByEntity != null)
                {
                    this.riddenByEntity.mountEntity(this);
                }

                if (!flag)
                {
                    this.dropItemWithOffset(Item.boat.itemID, 1, 0.0F);
                }

                this.setDead();
            }

            return true;
        }
        else
        {
            return true;
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
     */
    public void performHurtAnimation()
    {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        if (this.isEmpty)
        {
            this.boatPosRotationIncrements = par9 + 5;
        }
        else
        {
            double d3 = par1 - this.posX;
            double d4 = par3 - this.posY;
            double d5 = par5 - this.posZ;
            double d6 = d3 * d3 + d4 * d4 + d5 * d5;

            if (d6 <= 1.0D)
            {
                return;
            }

            this.boatPosRotationIncrements = 3;
        }

        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = (double)par7;
        this.boatPitch = (double)par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double par1, double par3, double par5)
    {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    /**
     * This heals the boat over time
     * 
     * depends on:
     *      nothing
     */
    private void tickHealing() {
        if (this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }
    }
    
    /**
     * This calculates the vertical speed to "bounce" to the surface
     * 
     * depends on:
     *      this.boundingBox
     * 
     * @return 
     */
    private double h_calc_bounce() {
        byte b0 = 5;
        double temp_y_speed = 0.0D;

        for (int i = 0; i < b0; ++i)
        {
            double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i + 0) / (double)b0 - 0.125D;
            double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i + 1) / (double)b0 - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d2, this.boundingBox.maxZ);

            if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
            {
                temp_y_speed += 1.0D / (double)b0;
            }
        }
        
        return temp_y_speed;
    }
    
    /**
     * This spawns "splash" particles behind the boat
     * (depending on horizontal speed)
     * 
     * depends on:
     *      this.motionX
     *      this.motionY
     *      this.motionZ
     *      this.posX
     *      this.posY
     *      this.posZ
     * 
     * modified state:
     *      none
     */
    private void h_spawn_paricles() {
        double xzSpeed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

        if (xzSpeed > 0.26249999999999996D)
        {
            double curr_facing_x = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
            double curr_facing_y = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);

            for (int j = 0; (double)j < 1.0D + xzSpeed * 60.0D; ++j)
            {
                double d6 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
                double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
                double partPosX;
                double partPosZ;

                if (this.rand.nextBoolean()) {
                    partPosX = this.posX - curr_facing_x * d6 * 0.8D + curr_facing_y * d7;
                    partPosZ = this.posZ - curr_facing_y * d6 * 0.8D - curr_facing_x * d7;
                } else {
                    partPosX = this.posX + curr_facing_x + curr_facing_y * d6 * 0.7D;
                    partPosZ = this.posZ + curr_facing_y - curr_facing_x * d6 * 0.7D;
                }
                
                this.worldObj.spawnParticle("splash", partPosX, this.posY - 0.125D, partPosZ, this.motionX, this.motionY, this.motionZ);
            }
        }
    }
    
    /**
     * Applies player steering
     * 
     * depends on:
     *      this.riddenByEntity
     *      this.speedMultiplier
     *      
     * modified state:
     *      this.motionX
     *      this.motionZ
     */
    private void h_steer_by_player() {
     // ---------- PLAYER STEERING [START]
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
        {
            double curr_facing_x = (double)((EntityLivingBase)this.riddenByEntity).moveForward;

            if (curr_facing_x > 0.0D)
            {
                double curr_facing_y = -Math.sin((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                double d11 = Math.cos((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                this.motionX += curr_facing_y * this.speedMultiplier * 0.05000000074505806D;
                this.motionZ += d11 * this.speedMultiplier * 0.05000000074505806D;
            }
        }
        // ---------- PLAYER STEERING [END]
    }
    
    /**
     * Updates the velocity vector
     * 
     * depends on:
     *      this.motionX
     *      this.motionZ
     *      this.speedMultiplier
     *      this.onGround
     *      
     * modified state:
     *      this.speedMultiplier
     *      this.motionX
     *      this.motionY
     *      this.motionZ
     * 
     * @param xzSpeed   Stored Speed
     */
    private void h_update_speed(double xzSpeed) {
        double xzSpeed2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

        if (xzSpeed2 > 0.35D)
        {
            double curr_facing_y = 0.35D / xzSpeed2;
            this.motionX *= curr_facing_y;
            this.motionZ *= curr_facing_y;
            xzSpeed2 = 0.35D;
        }

        if (xzSpeed2 > xzSpeed && this.speedMultiplier < 0.35D)
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
    }
    
    /**
     * Performs the boat breaking and drops planks and sticks
     * 
     * modified state:
     *      this.setDead()
     */
    private void h_break_boat() {
        if (!this.worldObj.isRemote && !this.isDead)
        {
            this.setDead();
            int k;

            for (k = 0; k < 3; ++k)
            {
                this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
            }

            for (k = 0; k < 2; ++k)
            {
                this.dropItemWithOffset(Item.stick.itemID, 1, 0.0F);
            }
        }
    }
    
    /**
     * Updates the facing of the boat
     * 
     * depends on:
     *      this.rotationYaw
     *      (this.rotationPitch)
     *      this.prevPosX
     *      this.prevPosZ
     *      this.posX
     *      this.posZ
     *      
     * modified state:
     *      this.rotationYaw
     *      this.setRotation()
     */
    private void h_update_facing() {
        this.rotationPitch = 0.0F;
        double curr_facing_y = (double)this.rotationYaw;
        double d11 = this.prevPosX - this.posX;
        double d10 = this.prevPosZ - this.posZ;

        if (d11 * d11 + d10 * d10 > 0.001D)
        {
            curr_facing_y = (double)((float)(Math.atan2(d10, d11) * 180.0D / Math.PI));
        }

        double d12 = MathHelper.wrapAngleTo180_double(curr_facing_y - (double)this.rotationYaw);

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
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        
        updateBuoys();
        
        tickHealing();

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        double temp_y_speed = h_calc_bounce();
        
        h_spawn_paricles();
        
        // store speed
        double xzSpeed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

        if (this.worldObj.isRemote && this.isEmpty)
        {
            if (this.boatPosRotationIncrements > 0)
            {
                double curr_facing_x = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double curr_facing_y = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                double d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(curr_facing_x, curr_facing_y, d11);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                
            }
            else
            {
                double curr_facing_x = this.posX + this.motionX;
                double curr_facing_y = this.posY + this.motionY;
                double d11 = this.posZ + this.motionZ;
                this.setPosition(curr_facing_x, curr_facing_y, d11);

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
            if (temp_y_speed < 1.0D)
            {
                double curr_facing_x = temp_y_speed * 2.0D - 1.0D;
                this.motionY += 0.03999999910593033D * curr_facing_x;
            }
            else
            {
                if (this.motionY < 0.0D)
                {
                    this.motionY /= 2.0D;
                }

                this.motionY += 0.007000000216066837D;
            }
            
            h_steer_by_player();

            h_update_speed(xzSpeed);

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            
            // ---------- COLLISSION BREAKING [START]
            if (this.isCollidedHorizontally && xzSpeed > 0.2D)
            {
                h_break_boat();
            }
            // ---------- COLLISSION BREAKING [END]
            else
            {
                this.motionX *= 0.9900000095367432D;
                this.motionY *= 0.949999988079071D;
                this.motionZ *= 0.9900000095367432D;
            }

            h_update_facing();

            if (!this.worldObj.isRemote)
            {
                // ---------- COLLISSION HANDLING [START]
                // ---------- Pushing boat by other boats [START]
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                int l;

                if (list != null && !list.isEmpty())
                {
                    for (l = 0; l < list.size(); ++l)
                    {
                        Entity entity = (Entity)list.get(l);

                        if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof WCEntityBoatBase)
                        {
                            entity.applyEntityCollision(this);
                        }
                    }
                }
                // ---------- Pushing boat by other boats [END]

                // ---------- Waterlily and Snow collisions [START]
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
                    }
                }
                // ---------- Waterlily and Snow collisions [END]
                // ---------- COLLISSION HANDLING [END]
                
                // ---------- Set empty if rider dead [START]
                if (this.riddenByEntity != null && this.riddenByEntity.isDead)
                {
                    this.riddenByEntity = null;
                }
                // ---------- Set empty if rider dead [END]
            }
        }
    }

    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
            double d0 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
            double d1 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
            this.riddenByEntity.setPosition(this.posX + d0, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * First layer of player interaction
     */
    public boolean interactFirst(EntityPlayer par1EntityPlayer)
    {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer)
        {
            return true;
        }
        else
        {
            if (!this.worldObj.isRemote)
            {
                par1EntityPlayer.mountEntity(this);
            }

            return true;
        }
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float par1)
    {
        this.dataWatcher.updateObject(19, Float.valueOf(par1));
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken()
    {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int par1)
    {
        this.dataWatcher.updateObject(17, Integer.valueOf(par1));
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit()
    {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int par1)
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(par1));
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection()
    {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    @SideOnly(Side.CLIENT)
    public void func_70270_d(boolean par1)
    {
        this.isEmpty = par1;
    }
    
    
    /****************************** END of Vanilla code ******************************/
	public Block getDisplayTile() {
		return null;
	}
	
	private int buoyUpdateTimer;
	private static final int BUOY_UPDATE_INTERVAL = 10;
	
	/**
	 * Updates the Buoys within a 3x3x3 cube
	 * (see BUOY_UPDATE_INTERVAL)
	 */
    protected void updateBuoys() {
        buoyUpdateTimer--;
        if (buoyUpdateTimer <= 0) {
            buoyUpdateTimer = BUOY_UPDATE_INTERVAL;
            
            forceUpdateBuoys();
        }
    }
    
    /**
     * This Updates the buoys immediately
     */
    protected void forceUpdateBuoys() {
        int myX = (int) posX;
        int myY = (int) posY;
        int myZ = (int) posZ;
        
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    TileEntity te = worldObj.getBlockTileEntity(myX + dx, myY + dy, myZ + dz);
                    if (te instanceof WCBouyLogic) {
                        ((WCBouyLogic) te).updateBuoys();
                        this.buoyFound((WCBouyLogic) te);
                    }
                }
            }
        }
    }
    
    /**
     * This callback gets called by the Boat base class if boat is near buoy
     * 
     * @param buoy
     */
    protected void buoyFound(WCBouyLogic buoy) {}
}
