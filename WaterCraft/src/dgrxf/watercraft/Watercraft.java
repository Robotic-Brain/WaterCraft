package dgrxf.watercraft;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.config.ConfigurationHandler;
import dgrxf.watercraft.creativetab.CreativeTabBoats;
import dgrxf.watercraft.creativetab.CreativeTabBuoys;
import dgrxf.watercraft.creativetab.CreativeTabMisc;
import dgrxf.watercraft.entity.Entities;
import dgrxf.watercraft.event.WCEventHandler;
import dgrxf.watercraft.interactions.ComputerCraft.ComputerCraftInteractions;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.lib.ModuleInfo;
import dgrxf.watercraft.lib.MultiBlockInfo;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.proxy.CommonProxy;
import dgrxf.watercraft.recipe.RecipeHandler;
import dgrxf.watercraft.util.LogHelper;

/**
 * WaterCraft
 * 
 * (https://github.com/Robotic-Brain/WaterCraft)
 * 
 * <pre>
 * Copyright (C) 2013
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * </pre>
 * 
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author Drunk Mafia
 * @author Robotic-Brain
 * @author xandayn
 * @author FrodCube
 * @author GoryMoon
 * 
 */

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, name = ModInfo.NAME, dependencies = "after:ComputerCraft")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { ModInfo.CHANNEL }, packetHandler = PacketHandler.class)
public class Watercraft {
    
    public static boolean          hasCC   = false;
    
    @Instance(ModInfo.MODID)
    public static Watercraft       instance;
    
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy      proxy;
    
    public static CreativeTabMisc  miscTab = new CreativeTabMisc(CreativeTabs.getNextID(), ModInfo.MISC_TAB);
    public static CreativeTabBoats boatTab = new CreativeTabBoats(CreativeTabs.getNextID(), ModInfo.BOATS_TAB);
    public static CreativeTabBuoys buoyTab = new CreativeTabBuoys(CreativeTabs.getNextID(), ModInfo.BUOYS_TAB);
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        LogHelper.info("Watercraft build version is: " + ModInfo.BUILD_SHA);
        
        MinecraftForge.EVENT_BUS.register(new WCEventHandler());
        ConfigurationHandler.init(e.getSuggestedConfigurationFile());
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        RecipeHandler.removeVanillaRecpies();
        ComputerCraftInteractions.beginInteraction();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent e) {
        proxy.initRendering();
        proxy.initSounds();
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        MultiBlockInfo.init();
        Entities.init();
        RecipeHandler.init();
        ModuleInfo.init();
    }
    
    @EventHandler
    public void interModComs(IMCEvent event) {
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("ComputerCraft")) {
            hasCC = true;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static void printToPlayer(String txt) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("" + txt);
    }
}
