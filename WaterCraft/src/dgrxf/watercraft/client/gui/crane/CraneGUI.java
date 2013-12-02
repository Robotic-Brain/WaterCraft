package dgrxf.watercraft.client.gui.crane;

import java.util.ArrayList;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.interfaces.IGuiTabContainer;
import dgrxf.watercraft.common.container.CraneContainer;
import dgrxf.watercraft.enumeration.OrdinalNumbers;
import dgrxf.watercraft.module.ModuleRegistry;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;
import dgrxf.watercraft.util.LogHelper;

/**
 * 
 * CraneGUI
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public class CraneGUI extends GuiBase implements IGuiTabContainer{
    
    private WCTileEntityCrane unit;
    private ArrayList<GuiCraneTab>			tabList = new ArrayList();
    private ItemStack[]					modules = ModuleRegistry.getRegisteredItemStacks();
    private GuiCraneTab                       activeTab;
    
    //TODO: COMPLETELY REDO THIS CLASS
    
    public CraneGUI(InventoryPlayer inventory, WCTileEntityCrane te) {
        super(new CraneContainer(inventory, te));
        unit = te;
        
        xSize = 216;
        ySize = 157;
        
        for(int i = 0; i < modules.length; i++){
        	tabList.add(new GuiCraneTab(OrdinalNumbers.values()[i].toString(), i, 12+(24*i), -21, 24, 24, modules[i]));
        }
        
        LogHelper.log(Level.WARNING, "[DEBUG]: " + unit.activeTabIndex);
        activeTab = tabList.get(unit.activeTabIndex);
    }
     
    private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/crane.png");
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        for (GuiCraneTab tab : tabList) {
            /*int srcY = 218;
            int srcX = 0;
            
            if(tab.getId() != 0){
            	if(tab.getId() == tabList.size()-1){
            		srcX = 90;
            	}else{
            		srcX = 45;
            	}
            }
            
            if (tab == activeTab) {
                srcY += 24;
            } else if (tab.inRect(this, x, y)) {
                srcY += 12;
            }
            
            tab.draw(this, srcX, srcY);*/
        	tab.drawBackground(this, x, y);
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        //fontRenderer.drawString("Control Unit", 8, 6, 0x404040);
    	
        for (GuiCraneTab tab : tabList) {
            tab.drawForeground(this, x, y);
            tab.drawHoverString(this, x, y, tab.getName());
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
/*        addButton = new GuiButton(0, guiLeft + 93, guiTop + 38, 80, 20, "Add Direction");
        removeButton = new GuiButton(1, guiLeft + 120, guiTop + 74, 42, 20, "Remove");
        removeButton.enabled = false;
        buttonList.add(addButton);
        buttonList.add(removeButton);*/
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        activeTab.actionPerformed(button);
    }
    
    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        
        activeTab.mouseClick(this, x, y, button);
        int i = 0;
        for (GuiCraneTab tab : tabList) {
            if (activeTab != tab) {
                if (tab.inRect(this, x, y)) {
                    unit.activeTabIndex = i;
                    PacketHandler.sendInterfacePacket((byte) 0, new byte[] { (byte) i });
                    activeTab = tab;
                    break;
                }
            }
            i++;
        }
    }
    
    @Override
    protected void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
        super.mouseClickMove(x, y, button, timeSinceClicked);
        
        activeTab.mouseMoveClick(this, x, y, button, timeSinceClicked);
    }
    
    @Override
    protected void mouseMovedOrUp(int x, int y, int button) {
        super.mouseMovedOrUp(x, y, button);
        
        activeTab.mouseReleased(this, x, y, button);
    }
    
    public ArrayList<String> getSelectedListFromCurrentTab() {
        return null;
    }

	/* (non-Javadoc)
	 * @see dgrxf.watercraft.client.gui.interfaces.IGuiTab#getActiveTab()
	 */
	@Override
	public GuiCraneTab getActiveTab() {
		return activeTab;
	}
    
}
