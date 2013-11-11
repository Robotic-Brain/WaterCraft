package dgrxf.watercraft.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dgrxf.watercraft.client.renderer.block.BuoyRenderer;
import dgrxf.watercraft.client.renderer.block.ControlUnitRenderer;
import dgrxf.watercraft.client.renderer.block.ToolBoxRenderer;
import dgrxf.watercraft.client.renderer.entity.WCBoatRenderer;
import dgrxf.watercraft.client.renderer.item.ItemBuoyRenderer;
import dgrxf.watercraft.client.renderer.item.ItemToolBoxRenderer;
import dgrxf.watercraft.client.sound.SoundHandler;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.entity.WCEntityBoatBase;
import dgrxf.watercraft.entity.WCEntitySmartBoat;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Client Proxy
 * 
 * @author Drunk Mafia
 * 
 */
public class ClientProxy extends CommonProxy {
    public void initSounds() {
        new SoundHandler();
    }
    
    public void initRendering() {
        RenderInfo.BUOY_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderInfo.CONTROL_UNIT_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderInfo.TOOLBOX_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityBuoy.class, new BuoyRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityControlUnitDock.class, new ControlUnitRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityToolBox.class, new ToolBoxRenderer());

        RenderingRegistry.registerEntityRenderingHandler(WCEntityBoatBase.class, new WCBoatRenderer());
        RenderingRegistry.registerEntityRenderingHandler(WCEntityBoat.class, new WCBoatRenderer());
        RenderingRegistry.registerEntityRenderingHandler(WCEntitySmartBoat.class, new WCBoatRenderer());
        
        MinecraftForgeClient.registerItemRenderer(BlockInfo.BUOY_ID, new ItemBuoyRenderer());
        MinecraftForgeClient.registerItemRenderer(BlockInfo.TOOLBOX_ID, new ItemToolBoxRenderer());
    }
}
