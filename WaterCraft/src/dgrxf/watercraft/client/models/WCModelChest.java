package dgrxf.watercraft.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * WCModelChest
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public class WCModelChest extends ModelBase {
    /** The chest lid in the chest's model. */
    public ModelRenderer chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
    
    /** The model of the bottom of the chest. */
    public ModelRenderer chestBelow;
    
    /** The chest's knob in the chest model. */
    public ModelRenderer chestKnob;
    
    public ModelRenderer lock;
    
    public WCModelChest() {
        this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
        this.chestLid.rotationPointX = 1.0F;
        this.chestLid.rotationPointY = 7.0F;
        this.chestLid.rotationPointZ = 15.0F;
        this.chestKnob = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        this.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
        this.chestKnob.rotationPointX = 8.0F;
        this.chestKnob.rotationPointY = 7.0F;
        this.chestKnob.rotationPointZ = 15.0F;
        this.chestBelow = (new ModelRenderer(this, 0, 19)).setTextureSize(64, 64);
        this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
        this.chestBelow.rotationPointX = 1.0F;
        this.chestBelow.rotationPointY = 6.0F;
        this.chestBelow.rotationPointZ = 1.0F;
        this.lock = ((new ModelRenderer(this, 0, 43)).setTextureSize(64, 64));
        this.lock.addBox(-2.0F, -2.0F, -15.0F, 4, 4, 2, 0.0F);
        this.lock.rotationPointX = 8.0F;
        this.lock.rotationPointY = 7.0F;
        this.lock.rotationPointZ = 15.0F;
    }
    
    public void renderAll(boolean locked) {
        this.chestKnob.rotateAngleX = this.lock.rotateAngleX = this.chestLid.rotateAngleX;
        this.chestLid.render(0.0625F);
        if (locked) {
            this.lock.render(0.0625F);
        } else {
            this.chestKnob.render(0.0625F);
        }
        this.chestBelow.render(0.0625F);
    }
}
