package dgrxf.watercraft.client.gui.crane;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.components.GuiArrowButton;
import dgrxf.watercraft.client.gui.interfaces.IGuiTabContainer;
import dgrxf.watercraft.common.container.CraneContainer;
import dgrxf.watercraft.module.ModuleRegistry;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;

/**
 * 
 * CraneGUI
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public class CraneGUI extends GuiBase implements IGuiTabContainer{

    private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/crane.png");
    private static final int			  TABS_PER_PAGE = 8;
    
    private WCTileEntityCrane unit;
    private ArrayList<GuiCraneTab>			tabList = new ArrayList();
    private ItemStack[]						modules = ModuleRegistry.getRegisteredItemStacks();
    private ArrayList<Integer>				pages = new ArrayList();
    private int 							currentPage;
    private GuiCraneTab                     activeTab;
    private GuiArrowButton[]				buttons = new GuiArrowButton[2];
    
    public CraneGUI(InventoryPlayer inventory, WCTileEntityCrane te) {
        super(new CraneContainer(inventory, te));
        unit = te;
        xSize = 216;
        ySize = 157;
        pages.add(1);
        int startingPosX = 12;
        int startingPosY = -21;
        int tabSizeXY = 24;
        int pageCount = 1;
        for(int i = 0; i < modules.length; i++){
        	tabList.add(new GuiCraneTab(modules[i].getDisplayName(), i, 12 + 24 * (i % TABS_PER_PAGE), -21, tabSizeXY, tabSizeXY, modules[i]));
        }
        
        for(int i = 1; i < tabList.size()+1; i++){
        	if(i > TABS_PER_PAGE * pages.size()){
        		pages.add(pages.size());
        	}
        }
        System.out.println(unit.activeTabIndex);
        activeTab = tabList.get(unit.activeTabIndex);
        currentPage = getPageForTab(activeTab);
    }
    
	@Override
	public void updateScreen() {
		super.updateScreen();
		if(currentPage == pages.size()){
			buttons[0].enabled = false;
		}else{
			buttons[0].enabled = true;
		}
		
		if(currentPage == 1){
			buttons[1].enabled = false;
		}else{
			buttons[1].enabled = true;
		}
	}
	
    @Override
    public void initGui() {
    	super.initGui();
    	buttons[0] = new GuiArrowButton(0, xSize-7, -15, GuiArrowButton.GuiDirection.RIGHT);
    	buttons[1] = new GuiArrowButton(1, -5, -15, GuiArrowButton.GuiDirection.LEFT);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        for(GuiArrowButton button : buttons){
        	button.drawBackground(this, x, y);
        }
        
        for (GuiCraneTab tab : tabList) {
        	if(isTabOnPage(tab))
        	tab.drawBackground(this, x, y);
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        for (GuiCraneTab tab : tabList) {
        	if(isTabOnPage(tab))
            tab.drawForeground(this, x, y);
        }
        for (GuiCraneTab tab : tabList) {
        	if(isTabOnPage(tab))
            tab.drawHoverString(this, x, y, tab.getName());
        }
    }
    
    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        
        for (GuiArrowButton b : buttons){
        	if(b.mouseClick(this, x, y)){
                if(b.id == 0){
                	if(currentPage < pages.size()){
                		currentPage++;
                	}
                }
                if(b.id == 1){
                	if(currentPage > 1){
                		currentPage--;
                	}
                }
        	}
        }
        
        activeTab.mouseClick(this, x, y, button);
        int i = 0;
        for (GuiCraneTab tab : tabList) {
            if (activeTab != tab) {
                if (tab.inRect(this, x, y) && isTabOnPage(tab)) {
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

	@Override
	public GuiCraneTab getActiveTab() {
		return activeTab;
	}
    
	private int getPageForTab(GuiCraneTab tab) {
		int id = tab.getId();
		if(id < TABS_PER_PAGE) return 1;
		
		for(int i = 1; i <= pages.size(); i++){
			if(i*TABS_PER_PAGE-1 > id) return i;
		}
		
		return 1;
	}
	
	private boolean isTabOnPage(GuiCraneTab tab){
		int id = tab.getId();
		if(id >= TABS_PER_PAGE * currentPage - TABS_PER_PAGE && id <= TABS_PER_PAGE * currentPage - 1) return true;
		return false;
	}
	
}
