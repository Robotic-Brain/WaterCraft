package dgrxf.watercraft.client.renderer.entity;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.entity.EntityTrader.EntityTrader;

/**
 * 
 * RenderTrader
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public class RenderTrader extends RenderLiving
{
    private static final ResourceLocation traderTextures = new ResourceLocation("watercraft", "textures/entity/trader.png");
    /** Model of the villager. */
    protected ModelVillager villagerModel;

    public RenderTrader()
    {
        super(new ModelVillager(0.0F), 0.5F);
        this.villagerModel = (ModelVillager)this.mainModel;
    }
    
    /**
     * Determines wether Villager Render pass or not.
     */
    protected int shouldVillagerRenderPass(EntityTrader par1EntityTrader, int par2, float par3)
    {
        return -1;
    }

    public void renderVillager(EntityTrader par1EntityTrader, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntityTrader, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation func_110902_a(EntityTrader par1EntityTrader)
    {
        switch (par1EntityTrader.getProfession())
        {
            case 0:
                return traderTextures;
            default:
                return traderTextures;
        }
    }

    protected void renderVillagerEquipedItems(EntityTrader par1EntityTrader, float par2)
    {
        super.renderEquippedItems(par1EntityTrader, par2);
    }

    protected void preRenderVillager(EntityTrader par1EntityTrader, float par2)
    {
        float f1 = 0.9375F;

        if (par1EntityTrader.getGrowingAge() < 0)
        {
            f1 = (float)((double)f1 * 0.5D);
            this.shadowSize = 0.25F;
        }
        else
        {
            this.shadowSize = 0.5F;
        }

        GL11.glScalef(f1, f1, f1);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderVillager((EntityTrader)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderVillager((EntityTrader)par1EntityLivingBase, par2);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldVillagerRenderPass((EntityTrader)par1EntityLivingBase, par2, par3);
    }

    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.renderVillagerEquipedItems((EntityTrader)par1EntityLivingBase, par2);
    }

    public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderVillager((EntityTrader)par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110902_a((EntityTrader)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderVillager((EntityTrader)par1Entity, par2, par4, par6, par8, par9);
    }
}
