package dgrxf.watercraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.container.ToolboxContainer;
import dgrxf.watercraft.client.gui.interfaces.ToolBoxGUI;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

public class GuiHandler implements IGuiHandler {
    
    public static final int TOOLBOX_GUI_ID = 0;
    
    public GuiHandler() {
        NetworkRegistry.instance().registerGuiHandler(Watercraft.instance, this);
        // NOTE: Shouldn't we better move this to Watercraft.java?
    }
    
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case TOOLBOX_GUI_ID:
                TileEntity te = world.getBlockTileEntity(x, y, z);
                if (te instanceof WCTileEntityToolBox) {
                    return new ToolboxContainer(player.inventory, (WCTileEntityToolBox) te);
                }
                break;
        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case TOOLBOX_GUI_ID:
                TileEntity te = world.getBlockTileEntity(x, y, z);
                if (te instanceof WCTileEntityToolBox) {
                    return new ToolBoxGUI(player.inventory, (WCTileEntityToolBox) te);
                }
                break;
        }
        return null;
    }
}
