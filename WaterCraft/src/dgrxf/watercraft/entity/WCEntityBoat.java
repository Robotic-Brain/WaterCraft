package dgrxf.watercraft.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import dgrxf.watercraft.enumeration.Colours;
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
public class WCEntityBoat extends WCEntityBoatBase {
    
    protected double                      speedMultiplier;
    protected int                         boatPosRotationIncrements;
    protected Vector2                     target;
    protected WCTileEntityControlUnitDock homeDock = null;
    public Colours flag;
    
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
        this.ridable = false;
    }
    
    @Override
    protected void entityInit()
    {
    	super.entityInit();
    	dataWatcher.addObject(20, new Integer(Colours.none.ordinal()));
    }
    
    public void setTargetLocation(Vector2 target) {
        this.target = target;
    }
    
    private static final int MAX_SPEED = 10;
    
    public void moveToTarget() {
        float xDist, zDist;
        if (target == null || worldObj.isRemote) {
            return;
        }
        xDist = dgrxf.watercraft.util.MathHelper.calculatePointDistance((float) posX, target.x);
        zDist = dgrxf.watercraft.util.MathHelper.calculatePointDistance((float) posZ, target.y);
        if (xDist > 1.0F) {
            if (posX < target.x)
                this.motionX = 0.1;
            else if (posX > target.x)
                this.motionX = -0.1;
        } else {
            this.motionX = 0;
            xDist = 0;
        }
        
        if (zDist > 1.0F) {
            if (posZ < target.y)
                this.motionZ = 0.1;
            else if (posZ > target.y)
                this.motionZ = -0.1;
        } else {
            this.motionZ = 0;
            zDist = 0;
        }
        
        if (zDist == 0 && xDist == 0) {
            target = null;
        }
        
    }
    
    @Override
    public void onEntityUpdate() {
        if (!worldObj.isRemote) {
            moveToTarget();
            if(flag != null)
            	System.out.println(flag.name);
        }else{
            this.rotationPitch = 0.0F;
            double d5 = (double)this.rotationYaw;
            double d11 = this.prevPosX - this.posX;
            double d10 = this.prevPosZ - this.posZ;

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
        }
    }
    
    @Override
    public boolean interactFirst(EntityPlayer player) {    	
    	ItemStack stack = player.getCurrentEquippedItem();
    	if(stack != null && stack.getItem().itemID == ModItems.flag.itemID){
    		Colours[] temp = Colours.values();
    		setFlagColor(temp[stack.getItemDamage()]);
    	}
    	
        double d5 = -Math.sin((double) (this.rotationYaw * (float) Math.PI / 180.0F));
        double d11 = Math.cos((double) (this.rotationYaw * (float) Math.PI / 180.0F));
        this.motionX += d5 * this.speedMultiplier * 0.05000000074505806D;
        this.motionZ += d11 * this.speedMultiplier * 0.05000000074505806D;
        
        return true;
    }
    
    public void setDocked(WCTileEntityControlUnitDock dock) {
        homeDock = dock;
    }
    
    public Colours getColour(int id){
    	Colours[] temp = Colours.values();
    	return temp[id];
    }
    
    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    	super.readEntityFromNBT(compound);
    	if(compound.hasKey("flag"))
    		flag = getColour(compound.getInteger("flag"));
    }
    
    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    	super.writeEntityToNBT(compound);
    	if(flag != null)
    		compound.setInteger("flag", flag.ordinal());
    }
    
    public void setFlagColor(Colours col){
    	this.dataWatcher.updateObject(20, Integer.valueOf(col.ordinal()));
    }
    
    public Colours getFlagColor(){
    	return this.getColour(dataWatcher.getWatchableObjectInt(20));
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

	public Block getDisplayTile() {
		return null;
	}
}
