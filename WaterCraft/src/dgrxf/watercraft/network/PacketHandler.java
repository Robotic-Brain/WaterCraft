package dgrxf.watercraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

public class PacketHandler implements IPacketHandler {
    
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
        
        EntityPlayer entityPlayer = (EntityPlayer) player;
        
        byte packetId = reader.readByte();
        
        switch (packetId) {
            default:
                System.out.println(ModInfo.getMODID() + " Invalid packet recived!");
        }
    }
    
    /*
     * public static void skeletonPacket(int x, int y, int z, String player){
     * ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
     * DataOutputStream dataStream = new DataOutputStream(byteStream);
     * 
     * try { dataStream.writeByte((byte)0); dataStream.writeInt(x);
     * dataStream.writeInt(y); dataStream.writeInt(z);
     * dataStream.writeUTF(player);
     * 
     * PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket(ModInfo
     * .CHANNEL, byteStream.toByteArray())); }catch(IOException e) {
     * FMLLog.warning(ModInfo.getMODID() +
     * " Failed to send player name packet for the ToolBox, please report this to the mod Author"
     * , e); e.printStackTrace(); } }
     */
}
