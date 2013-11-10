package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.container.BoatChestContainer;
import dgrxf.watercraft.entity.EntityBoatChest;

public class GuiBoatChest extends GuiContainer {
	private int invRows;
	private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
	private EntityBoatChest chest;
	private static final ResourceLocation resloc = new ResourceLocation("textures/gui/container/generic_54.png");
	
	public GuiBoatChest(InventoryPlayer inv, EntityBoatChest chest) {
		super(new BoatChestContainer(inv, chest));
		this.chest = chest;
		xSize = 176;
		ySize = 154;
		short a = 222;
        int b = a- 108;
		this.invRows = chest.getSizeInventory() / 9;
		this.ySize = b + this.invRows * 18;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(this.lowerChestInventory.isInvNameLocalized() ? this.lowerChestInventory.getInvName() : I18n.getString(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.upperChestInventory.isInvNameLocalized() ? this.upperChestInventory.getInvName() : I18n.getString(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resloc);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.invRows * 18 + 17);
        this.drawTexturedModalRect(k, l + this.invRows * 18 + 17, 0, 126, this.xSize, 96);
	}

}
