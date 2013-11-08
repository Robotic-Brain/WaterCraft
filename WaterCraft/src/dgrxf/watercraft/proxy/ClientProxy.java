package dgrxf.watercraft.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dgrxf.watercraft.client.renderer.block.BuoyRenderer;
import dgrxf.watercraft.client.renderer.block.ControlUnitRenderer;
import dgrxf.watercraft.client.renderer.entity.WCBoatRenderer;
import dgrxf.watercraft.client.renderer.item.ItemBuoyRenderer;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:11/06/2013 MM/DD/YYYYY
 * 
 **/

public class ClientProxy extends CommonProxy {
    public void initSounds() {
        
    }
    
    public void initRendering() {
        RenderInfo.BUOY_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityBuoy.class, new BuoyRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityControlUnitDock.class, new ControlUnitRenderer());
        
        RenderingRegistry.registerEntityRenderingHandler(WCEntityBoat.class, new WCBoatRenderer());
        
        MinecraftForgeClient.registerItemRenderer(BlockInfo.BOUY_ID, new ItemBuoyRenderer());
    }
}
