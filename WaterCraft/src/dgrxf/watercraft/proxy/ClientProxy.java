package dgrxf.watercraft.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dgrxf.watercraft.client.renderer.block.BuoyRenderer;
import dgrxf.watercraft.client.renderer.block.ControlUnitRenderer;
import dgrxf.watercraft.client.renderer.block.ToolBoxRenderer;
import dgrxf.watercraft.client.renderer.block.WCChestRenderer;
import dgrxf.watercraft.client.renderer.entity.WCBoatRenderer;
import dgrxf.watercraft.client.renderer.item.ItemBuoyRenderer;
import dgrxf.watercraft.client.renderer.item.ItemControlUnitRenderer;
import dgrxf.watercraft.client.renderer.item.ItemToolBoxRenderer;
import dgrxf.watercraft.client.renderer.item.ItemWCChestRenderer;
import dgrxf.watercraft.client.sound.SoundHandler;
import dgrxf.watercraft.entity.boat.WCEntityBoatBase;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityChest;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;

/**
 * Client Proxy
 * 
 * @author Drunk Mafia
 * 
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void initSounds() {
        new SoundHandler();
    }
    
    @Override
    public void initRendering() {
        RenderInfo.BUOY_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderInfo.CONTROL_UNIT_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderInfo.TOOLBOX_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderInfo.BUOY_FILTER_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderInfo.WC_CHEST_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityBuoy.class, new BuoyRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityControlUnitDock.class, new ControlUnitRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityToolBox.class, new ToolBoxRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityChest.class, new WCChestRenderer());
        
        RenderingRegistry.registerEntityRenderingHandler(WCEntityBoatBase.class, new WCBoatRenderer());
        
        MinecraftForgeClient.registerItemRenderer(BlockInfo.BUOY_ID, new ItemBuoyRenderer());
        MinecraftForgeClient.registerItemRenderer(BlockInfo.BUOY_FILTER_ID, new ItemBuoyRenderer());
        MinecraftForgeClient.registerItemRenderer(BlockInfo.TOOLBOX_ID, new ItemToolBoxRenderer());
        MinecraftForgeClient.registerItemRenderer(BlockInfo.CONTROL_UNIT_DOCK_ID, new ItemControlUnitRenderer());
        MinecraftForgeClient.registerItemRenderer(BlockInfo.WC_CHEST_ID, new ItemWCChestRenderer());
    }
}
