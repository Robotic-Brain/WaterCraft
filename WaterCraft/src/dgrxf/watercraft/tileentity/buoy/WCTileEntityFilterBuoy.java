package dgrxf.watercraft.tileentity.buoy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import dgrxf.watercraft.enumeration.Colours;


public class WCTileEntityFilterBuoy extends WCTileEntityBuoy {
	
	private Colours[] directions;
	
	public WCTileEntityFilterBuoy() {
		directions = new Colours[4];
	}	
	
	public boolean setColour(int direction, Colours colour){
		if(directions[direction] == null){
			directions[direction] = colour;
			return true;
		}else
			return false;
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
