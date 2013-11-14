package dgrxf.watercraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.config.ConfigurationHandler;
import dgrxf.watercraft.creativetab.CreativeTabWaterCraft;
import dgrxf.watercraft.entity.Entities;
import dgrxf.watercraft.event.WCEventHandler;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.lib.MultiBlockInfo;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.proxy.CommonProxy;
import dgrxf.watercraft.recipe.RecipeHandler;
import dgrxf.watercraft.util.RecipeHelper;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:14/06/2013 MM/DD/YYYYY
 * 
 */

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, name = ModInfo.NAME)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { ModInfo.CHANNEL }, packetHandler = PacketHandler.class)
public class Watercraft {
    
    @Instance(ModInfo.MODID)
    public static Watercraft            instance;
    
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy           proxy;
    
    public static CreativeTabWaterCraft creativeTab = new CreativeTabWaterCraft(CreativeTabs.getNextID(), ModInfo.MODID);
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	MinecraftForge.EVENT_BUS.register(new WCEventHandler());
        ConfigurationHandler.init(e.getSuggestedConfigurationFile());
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        RecipeHandler.removeVanillaRecpies();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent e) {
        proxy.initRendering();
        proxy.initSounds();
        ModBlocks.init();
        ModItems.init();
        MultiBlockInfo.init();
        Entities.init();
        RecipeHandler.init();
    }
        
    public static void printToPlayer(String txt) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("" + txt);
    }
}
