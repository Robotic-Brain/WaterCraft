package dgrxf.watercraft.tileentity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.block.WCChest;
import dgrxf.watercraft.interfaces.ILockableBlock;

public class WCTileEntityChest extends TileEntity implements IInventory, ILockableBlock {
    private ItemStack[]      inventory = new ItemStack[36];
    
    //TODO support for large chests (locks)
    
    public boolean           adjacentChestChecked;
    public WCTileEntityChest adjacentChestZNeg;
    public WCTileEntityChest adjacentChestXPos;
    public WCTileEntityChest adjacentChestXNeg;
    public WCTileEntityChest adjacentChestZPosition;
    public float             lidAngle;
    public float             prevLidAngle;
    public int               numUsingPlayers;
    private int              ticksSinceSync;
    private int              cachedChestType;
    private String           customName;
    private boolean          locked;
    private int              code;
    private boolean          shouldUpdateCode;
    
    public WCTileEntityChest() {
        this.cachedChestType = -1;
        shouldUpdateCode = true;
    }
    
    private void checkForAdjacentLockedChests() {
        if (worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) != null && worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof WCTileEntityChest) {
            this.adjacentChestXNeg = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
        }
        
        if (worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) != null && worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof WCTileEntityChest) {
            this.adjacentChestXPos = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
        }
        
        if (worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) != null && worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof WCTileEntityChest) {
            this.adjacentChestZNeg = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
        }
        
        if (worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) != null && worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof WCTileEntityChest) {
            this.adjacentChestZPosition = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
        }
        
        if (adjacentChestXNeg != null) {
            code = adjacentChestXNeg.getCode();
            locked = adjacentChestXNeg.isLocked();
        } else if (adjacentChestZNeg != null) {
            code = adjacentChestZNeg.getCode();
            locked = adjacentChestZNeg.isLocked();
        } else if (adjacentChestXPos != null) {
            code = adjacentChestXPos.getCode();
            locked = adjacentChestXPos.isLocked();
        } else if (adjacentChestZPosition != null) {
            code = adjacentChestZPosition.getCode();
            locked = adjacentChestZPosition.isLocked();
        } else {
            code = -1;
            locked = false;
        }
        
    }
    
    @SideOnly(Side.CLIENT)
    public WCTileEntityChest(int par1) {
        this.cachedChestType = par1;
    }
    
    @Override
    public void setLocked(boolean lock) {
        locked = lock;
        
        if (adjacentChestXNeg != null && adjacentChestXNeg.isLocked() != lock) {
            adjacentChestXNeg.setLocked(lock);
        }
        
        if (adjacentChestZNeg != null && adjacentChestZNeg.isLocked() != lock) {
            adjacentChestZNeg.setLocked(lock);
        }
        
        if (adjacentChestXPos != null && adjacentChestXPos.isLocked() != lock) {
            adjacentChestXPos.setLocked(lock);
        }
        
        if (adjacentChestZPosition != null && adjacentChestZPosition.isLocked() != lock) {
            adjacentChestZPosition.setLocked(lock);
        }
    }
    
    @Override
    public boolean isLocked() {
        return locked;
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.inventory[par1];
    }
    
    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.inventory[par1] != null) {
            ItemStack itemstack;
            
            if (this.inventory[par1].stackSize <= par2) {
                itemstack = this.inventory[par1];
                this.inventory[par1] = null;
                this.onInventoryChanged();
                return itemstack;
            } else {
                itemstack = this.inventory[par1].splitStack(par2);
                
                if (this.inventory[par1].stackSize == 0) {
                    this.inventory[par1] = null;
                }
                
                this.onInventoryChanged();
                return itemstack;
            }
        } else {
            return null;
        }
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.inventory[par1] != null) {
            ItemStack itemstack = this.inventory[par1];
            this.inventory[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }
    
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.inventory[par1] = par2ItemStack;
        
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        
        this.onInventoryChanged();
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.customName : "container.chest";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.customName != null && this.customName.length() > 0;
    }
    
    public void setChestGuiName(String par1Str) {
        this.customName = par1Str;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.inventory = new ItemStack[this.getSizeInventory()];
        
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            
            if (j >= 0 && j < this.inventory.length) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        setLocked(par1NBTTagCompound.getBoolean("locked"));
        setCode(par1NBTTagCompound.getInteger("code"));
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        
        par1NBTTagCompound.setTag("Items", nbttaglist);
        
        if (this.isInvNameLocalized()) {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }
        
        par1NBTTagCompound.setInteger("code", getCode());
        par1NBTTagCompound.setBoolean("locked", isLocked());
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
                : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }
    
    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }
    
    private void func_90009_a(WCTileEntityChest par1TileEntityChest, int par2) {
        if (par1TileEntityChest.isInvalid()) {
            this.adjacentChestChecked = false;
        } else if (this.adjacentChestChecked) {
            switch (par2) {
                case 0:
                    if (this.adjacentChestZPosition != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                    }
                    
                    break;
                case 1:
                    if (this.adjacentChestXNeg != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                    }
                    
                    break;
                case 2:
                    if (this.adjacentChestZNeg != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                    }
                    
                    break;
                case 3:
                    if (this.adjacentChestXPos != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }
    
    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            this.adjacentChestChecked = true;
            this.adjacentChestZNeg = null;
            this.adjacentChestXPos = null;
            this.adjacentChestXNeg = null;
            this.adjacentChestZPosition = null;
            
            if (this.func_94044_a(this.xCoord - 1, this.yCoord, this.zCoord)) {
                this.adjacentChestXNeg = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            }
            
            if (this.func_94044_a(this.xCoord + 1, this.yCoord, this.zCoord)) {
                this.adjacentChestXPos = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
            }
            
            if (this.func_94044_a(this.xCoord, this.yCoord, this.zCoord - 1)) {
                this.adjacentChestZNeg = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            }
            
            if (this.func_94044_a(this.xCoord, this.yCoord, this.zCoord + 1)) {
                this.adjacentChestZPosition = (WCTileEntityChest) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
            }
            
            if (this.adjacentChestZNeg != null) {
                this.adjacentChestZNeg.func_90009_a(this, 0);
            }
            
            if (this.adjacentChestZPosition != null) {
                this.adjacentChestZPosition.func_90009_a(this, 2);
            }
            
            if (this.adjacentChestXPos != null) {
                this.adjacentChestXPos.func_90009_a(this, 1);
            }
            
            if (this.adjacentChestXNeg != null) {
                this.adjacentChestXNeg.func_90009_a(this, 3);
            }
        }
    }
    
    private boolean func_94044_a(int par1, int par2, int par3) {
        Block block = Block.blocksList[this.worldObj.getBlockId(par1, par2, par3)];

        return block != null && block instanceof WCChest ? ((WCChest) block).chestType == this.getChestType()
                : false;

    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if (shouldUpdateCode) {
            checkForAdjacentLockedChests();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            shouldUpdateCode = false;
        }
        this.checkForAdjacentChests();
        ++this.ticksSinceSync;
        float f;
        
        if (!this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numUsingPlayers = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(this.xCoord - f, this.yCoord - f, this.zCoord - f, this.xCoord + 1 + f, this.yCoord + 1 + f, this.zCoord + 1 + f));
            Iterator iterator = list.iterator();
            
            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();
                
                if (entityplayer.openContainer instanceof ContainerChest) {
                    IInventory iinventory = ((ContainerChest) entityplayer.openContainer).getLowerChestInventory();
                    
                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest) iinventory).isPartOfLargeChest(this)) {
                        ++this.numUsingPlayers;
                    }
                }
            }
        }
        
        this.prevLidAngle = this.lidAngle;
        f = 0.1F;
        double d0;
        
        if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double d1 = this.xCoord + 0.5D;
            d0 = this.zCoord + 0.5D;
            
            if (this.adjacentChestZPosition != null) {
                d0 += 0.5D;
            }
            
            if (this.adjacentChestXPos != null) {
                d1 += 0.5D;
            }
            
            this.worldObj.playSoundEffect(d1, this.yCoord + 0.5D, d0, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        
        if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F) {
            float f1 = this.lidAngle;
            
            if (this.numUsingPlayers > 0) {
                this.lidAngle += f;
            } else {
                this.lidAngle -= f;
            }
            
            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }
            
            float f2 = 0.5F;
            
            if (this.lidAngle < f2 && f1 >= f2 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
                d0 = this.xCoord + 0.5D;
                double d2 = this.zCoord + 0.5D;
                
                if (this.adjacentChestZPosition != null) {
                    d2 += 0.5D;
                }
                
                if (this.adjacentChestXPos != null) {
                    d0 += 0.5D;
                }
                
                this.worldObj.playSoundEffect(d0, this.yCoord + 0.5D, d2, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            
            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }
    
    @Override
    public boolean receiveClientEvent(int par1, int par2) {
        if (par1 == 1) {
            this.numUsingPlayers = par2;
            return true;
        } else {
            return super.receiveClientEvent(par1, par2);
        }
    }
    
    @Override
    public void openChest() {
        if (this.numUsingPlayers < 0) {
            this.numUsingPlayers = 0;
        }
        
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
    }
    
    @Override
    public void closeChest() {
        if (this.getBlockType() != null && this.getBlockType() instanceof WCChest) {
            --this.numUsingPlayers;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
        }
    }
    
    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }
    
    public int getChestType() {
        if (this.cachedChestType == -1) {
            if (this.worldObj == null || !(this.getBlockType() instanceof WCChest)) {
                return 0;
            }

            this.cachedChestType = ((WCChest) this.getBlockType()).chestType;
        }
        
        return this.cachedChestType;
    }
    
    @Override
    public int getCode() {
        return code;
    }
    
    @Override
    public void setCode(int code) {
        this.code = code;
        
        if (adjacentChestXNeg != null && adjacentChestXNeg.getCode() != code) {
            adjacentChestXNeg.setCode(code);
        }
        
        if (adjacentChestZNeg != null && adjacentChestZNeg.getCode() != code) {
            adjacentChestZNeg.setCode(code);
        }
        
        if (adjacentChestXPos != null && adjacentChestXPos.getCode() != code) {
            adjacentChestXPos.setCode(code);
        }
        
        if (adjacentChestZPosition != null && adjacentChestZPosition.getCode() != code) {
            adjacentChestZPosition.setCode(code);
        }
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        NBTTagCompound tag = pkt.data;
        setLocked(tag.getBoolean("isLocked"));
        System.out.println("Client: Packet Recevied");
    }
    
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("isLocked", isLocked());
        System.out.println("Server: Sending packet");
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, blockMetadata, tag);
    }
}
