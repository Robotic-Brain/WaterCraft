package dgrxf.watercraft.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.renderer.block.BuoyRenderer;
import dgrxf.watercraft.client.renderer.block.ControlUnitRenderer;
import dgrxf.watercraft.client.renderer.block.LiquidTankRenderer;
import dgrxf.watercraft.client.renderer.block.ToolBoxRenderer;
import dgrxf.watercraft.client.renderer.block.WCChestRenderer;
import dgrxf.watercraft.client.renderer.entity.RenderTrader;
import dgrxf.watercraft.client.renderer.entity.WCBoatRenderer;
import dgrxf.watercraft.client.renderer.item.ItemBuoyRenderer;
import dgrxf.watercraft.client.renderer.item.ItemToolBoxRenderer;
import dgrxf.watercraft.client.renderer.item.ItemWCChestRenderer;
import dgrxf.watercraft.client.sound.SoundHandler;
import dgrxf.watercraft.entity.EntityTrader.EntityTrader;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityChest;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;
import dgrxf.watercraft.tileentity.controlunit.WCTileEntityControlUnitDock;

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
        RenderInfo.TANK_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityBuoy.class, new BuoyRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityControlUnitDock.class, new ControlUnitRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityToolBox.class, new ToolBoxRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WCTileEntityChest.class, new WCChestRenderer());
        
        RenderingRegistry.registerEntityRenderingHandler(AbstractBaseBoat.class, new WCBoatRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityTrader.class, new RenderTrader());
        
        MinecraftForgeClient.registerItemRenderer(ModBlocks.BUOY.getId(), new ItemBuoyRenderer());
        MinecraftForgeClient.registerItemRenderer(ModBlocks.TOOLBOX.getId(), new ItemToolBoxRenderer());
        //TODO Keeping this for the future if we ever need it again - Drunk
        //MinecraftForgeClient.registerItemRenderer(BlockInfo.CONTROL_UNIT_DOCK_ID, new ItemControlUnitRenderer());
        MinecraftForgeClient.registerItemRenderer(ModBlocks.WC_CHEST.getId(), new ItemWCChestRenderer());
        
        RenderingRegistry.registerBlockHandler(new LiquidTankRenderer());
    }
}
