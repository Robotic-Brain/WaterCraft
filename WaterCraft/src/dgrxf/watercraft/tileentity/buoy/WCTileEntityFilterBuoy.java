package dgrxf.watercraft.tileentity.buoy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.enumeration.Colours;


public class WCTileEntityFilterBuoy extends WCBouyLogic {
	
	public Colours[] directions;
	
	public WCTileEntityFilterBuoy() {
		directions = new Colours[4];
	}	
	
	@Override
	public void updateEntity() {		
		  if(worldObj.isRemote) return;
		
		  WCEntityBoat e = findEntityBoat(getBlockDirection(), WCEntityBoat.class);
	}
	
	public void setColour(int direction, Colours colour){
		int index = direction - 2;
		if(directions[index] == null){
			directions[index] = colour;
		}
	}
	
	public void removeColour(int direction){
		int index = direction - 2;
		if(directions[index] != null){
			directions[index] = null;
		}
	}
	
	@Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        NBTTagCompound tag = pkt.data;
        for(int i = 0 ; i < directions.length; i++){
        	if(tag.hasKey("" + i)){
        		Colours[] temp = Colours.values();
        		int index = (int) tag.getByte("" + i);
        		directions[i] = temp[index];
        	}
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        for(int i = 0; i < directions.length; i++){
        	if(directions[i] != null)
        		tag.setByte("" + i, (byte) directions[i].ordinal());
        }
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, blockMetadata, tag);
    }
}
