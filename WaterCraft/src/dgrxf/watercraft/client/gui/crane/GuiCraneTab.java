package dgrxf.watercraft.client.gui.crane;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.components.GuiDropdown;
import dgrxf.watercraft.client.gui.components.GuiGraphicalTab;

/**
 * GuiCraneTab
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public class GuiCraneTab extends GuiGraphicalTab{

	private ItemStack module;
	
	private GuiDropdown menu;
	
	/**
	 * @param name
	 * @param id
	 */
	public GuiCraneTab(String name, int id, int x, int y, int w, int h, ItemStack module) {
		super(name, id, x, y, w, h);
		this.module = module;
		menu = new GuiDropdown(0, 0, new ArrayList(){{add("temp1"); add("temp2"); add("temp3"); add("temp3"); add("temp3"); add("temp3"); add("temp3"); add("temp3"); add("temp3"); add("temp3");}});
	}
	
	@Override
	public void drawHoverString(GuiBase gui, int mouseX, int mouseY, String str) {
		if(inRect(gui, mouseX, mouseY)){
			List temp = new ArrayList<String>();
			temp.add(str);
			gui.drawHoverString(temp, mouseX-gui.getLeft(), mouseY-gui.getTop());
		}
	}
	
	@Override
	public void drawBackground(GuiBase gui, int x, int y) {
		Minecraft.getMinecraft().renderEngine.bindTexture(getGuiTabResource());
		int srcX = 0;
		int srcY = 0;
		if(this != ((CraneGUI)gui).getActiveTab()){
			srcY = 24;
		}
		gui.drawTexturedModalRect(getX()+gui.getLeft(), getY()+gui.getTop(), srcX, srcY, getWidth(), getHeight());
		if(((CraneGUI)gui).activeTab != this) return;
		menu.drawBackground(gui, x, y);
	}

	@Override
	public void drawForeground(GuiBase gui, int x, int y) {
		renderingHandler(getX(), getY(), module, 0, 1.0F, gui);
		if(((CraneGUI)gui).activeTab != this) return;
		menu.drawForeground(gui, x, y);
	}

	@Override
	public void actionPerformed(GuiButton button) {}

	@Override
	public void mouseClick(GuiBase gui, int x, int y, int button) {
		if(((CraneGUI)gui).activeTab != this) return;
		if(button == 0 && inRect(gui, x, y)){
			this.setTabActive(true);
		}
		menu.mouseClick(gui, x, y, button);
	}

	@Override
	public void mouseMoveClick(GuiBase gui, int x, int y, int button, long timeSinceClicked) {
		if(((CraneGUI)gui).activeTab != this) return;
		menu.mouseMoveClick(gui, x, y, button, timeSinceClicked);
	}

	@Override
	public void mouseReleased(GuiBase gui, int x, int y, int button) {
		if(((CraneGUI)gui).activeTab != this) return;
		menu.mouseReleased(gui, x, y, button);
	}

	public void renderingHandler(int x, int y, ItemStack stack, float rotation, float scale, GuiBase gui) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		if(stack.getItem() instanceof ItemBlock){
			GL11.glEnable(GL11.GL_LIGHTING);
			TextureManager textMan = Minecraft.getMinecraft().getTextureManager();
			RenderBlocks renderBlocks = new RenderBlocks();
			
			textMan.bindTexture(textMan.getResourceLocation(0));
			GL11.glTranslatef(x + (getWidth()/2), y + (getHeight()/2), 100);
			GL11.glScalef(-10, 10, 10);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(30, 1, 0, 0);
			GL11.glRotatef(-45, 0, 1, 0);
	        RenderManager.instance.itemRenderer.renderItem(null, stack, 10);
			GL11.glDisable(GL11.GL_LIGHTING);
		}else{
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
			gui.drawTexturedModelRectFromIcon(x+4, y+4, stack.getItem().getIcon(stack, stack.getItemDamage()), 16, 16);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
