package dgrxf.watercraft;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.config.ConfigurationHandler;
import dgrxf.watercraft.creativetab.CreativeTabWaterCraft;
import dgrxf.watercraft.entity.Entities;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.lib.MultiBlockInfo;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.proxy.CommonProxy;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:11/06/2013 MM/DD/YYYYY
 * 
 */

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, name = ModInfo.NAME)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { ModInfo.CHANNEL }, packetHandler = PacketHandler.class)
public class Watercraft {
    
    @Instance(ModInfo.MODID)
    public static Watercraft instance;
    
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;
    
    public static CreativeTabWaterCraft tab = new CreativeTabWaterCraft(CreativeTabs.getNextID(), ModInfo.MODID);
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        ConfigurationHandler.init(e.getSuggestedConfigurationFile());
        new GuiHandler();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent e) {
        proxy.initRendering();
        ModBlocks.init();
        ModItems.init();
        MultiBlockInfo.init();
        Entities.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        
    }
    
    public static void printToPlayer(String txt) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("" + txt);
    }
}
