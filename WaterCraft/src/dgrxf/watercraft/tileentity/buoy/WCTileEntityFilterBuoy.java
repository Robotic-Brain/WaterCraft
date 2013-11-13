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
	public int tick;
	
	public WCTileEntityFilterBuoy() {
		directions = new Colours[4];
		tick = 30;
	}	
	
	@Override
	public void updateEntity() {		
		  if(worldObj.isRemote) return;
		  
		  tick--;
		  if(tick <= 0){
			  WCEntityBoat e = findEntityBoat(getBlockDirection(), WCEntityBoat.class);
			  if(e != null){
				  Colours boatColour = e.getFlagColor();
				  if(boatColour != null){
					  for(int i = 0; i < directions.length; i++){
						  if(boatColour == directions[i]){
							  System.out.println(ForgeDirection.getOrientation(i));
						  }
					  }
				  }
			  }
			  tick = 30;
		  }
	}
	
	public void setColour(ForgeDirection direction, Colours colour){
		int index = direction.ordinal() - 2;
		if(directions[index] == null){
			directions[index] = colour;
		}
	}
	
	public Colours removeColour(ForgeDirection direction){
		int index = direction.ordinal() - 2;
		Colours temp = null;
		if(directions[index] != null){
			temp = directions[index];
			directions[index] = null;
		}
		return temp;
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
