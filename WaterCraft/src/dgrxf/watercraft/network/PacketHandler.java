package dgrxf.watercraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import dgrxf.watercraft.client.gui.container.FreezerContainer;
import dgrxf.watercraft.client.gui.container.ITeContainer;
import dgrxf.watercraft.client.gui.container.LockAssemblerContainer;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.tileentity.ITileEntityInterfaceEvent;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;
import dgrxf.watercraft.util.LogHelper;

public class PacketHandler implements IPacketHandler {
    
    public static final int INTERFACE_PACKET_ID 	 = 0;
    public static final int FREEZER_PACKET_ID   	 = 1;
    public static final int SOUND_PACKET_ID    		 = 2;
    public static final int LOCK_ASSEMBLER_PACKET_ID = 3;
    
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
        
        EntityPlayer entityPlayer = (EntityPlayer) player;
        Container container = entityPlayer.openContainer;
        
        byte packetId = reader.readByte();
        
        switch (packetId) {
            case INTERFACE_PACKET_ID:
                byte type = reader.readByte();
                byte lenght = reader.readByte();
                byte[] val = new byte[lenght];
                for (int i = 0; i < val.length; i++) {
                    val[i] = reader.readByte();
                }
                
                if (container != null && container instanceof ITeContainer) {
                    TileEntity te = ((ITeContainer) container).getTileEntity();
                    if (te != null && te instanceof ITileEntityInterfaceEvent) {
                        ((ITileEntityInterfaceEvent) te).receiveInterfaceEvent(type, val);
                    }
                }
                break;
            case FREEZER_PACKET_ID:
                byte data = reader.readByte();
                if (container != null && container instanceof FreezerContainer) {
                    TileEntity te = ((FreezerContainer) container).getTileEntity();
                    if (te instanceof WCTileEntityFreezer) {
                        ((WCTileEntityFreezer) te).setType(data);
                    }
                }
                break;
            case SOUND_PACKET_ID:
                byte sound = reader.readByte();
                int x = reader.readInt();
                int y = reader.readInt();
                int z = reader.readInt();
                
                if (player != null ) {
                    Sounds.values()[sound].play(x, y, z, 1.0F, 1.0F);
                }
                break;
            case LOCK_ASSEMBLER_PACKET_ID:
                short code = reader.readShort();
                if (container != null && container instanceof LockAssemblerContainer) {
                    TileEntity te = ((LockAssemblerContainer)container).getTileEntity();
                    if (te instanceof WCTileEntityLockAssembler) {
                        ((WCTileEntityLockAssembler) te).setCode(code);
                    }
                }
                break;
            default:
                LogHelper.severe("Invalid packet with ID {" + Byte.toString(packetId) + "} recieved. This is a bug please report this to the mod authors.");
        }
    }
    
    public static void sendInterfacePacket(byte type, byte[] val) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        
        try {
            
            dataStream.writeByte((byte) INTERFACE_PACKET_ID);
            dataStream.writeByte(type);
            dataStream.writeByte((byte) val.length);
            dataStream.write(val);
            
            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
        } catch (IOException e) {
            LogHelper.severe("Failed to send smelter interface packet. This is a bug please report this to the mod auther." + e);
        }
        
    }
    
    public static void sendFreezerPacket(byte data) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        
        try {
            
            dataStream.writeByte((byte) FREEZER_PACKET_ID);
            dataStream.writeByte(data);
            
            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
        } catch (IOException e) {
            LogHelper.severe("Failed to send freezer interface packet. This is a bug please report this to the mod auther." + e);
        }
        
    }

    public static void sendSoundPackage(int data, int x, int y, int z, int dimension) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        
        try {
            
            dataStream.writeByte((byte) SOUND_PACKET_ID);
            dataStream.writeByte((byte)data);
            dataStream.writeInt(x);
            dataStream.writeInt(y);
            dataStream.writeInt(z);
            
            PacketDispatcher.sendPacketToAllAround(x, y, z, 16, dimension, PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
        } catch (IOException e) {
            LogHelper.severe("Failed to send sound interface packet. This is a bug please report this to the mod auther." + e);
        }
    }
    
    public static void sendLockAssemblerPacket(short code) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        
        try {
            
            dataStream.writeByte((byte) LOCK_ASSEMBLER_PACKET_ID);
            dataStream.writeShort(code);
            
            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
        } catch (IOException e) {
            LogHelper.severe("Failed to send lock assembler interface packet. This is a bug please report this to the mod auther." + e);
        }
        
    }
}
