package dgrxf.watercraft.lib;

import net.minecraft.util.ResourceLocation;

/**
 * 
 * RenderInfo
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class RenderInfo {
    public static final ResourceLocation BUOY_TEXTURE_LOCATION         = new ResourceLocation(ModInfo.MODID, "textures/models/ModelBuoyTexture.png");
    public static int                    BUOY_RENDER_ID;
    public static int                    BUOY_FILTER_RENDER_ID;
    
    public static final ResourceLocation CONTROL_UNIT_TEXTURE_LOCATION = new ResourceLocation(ModInfo.MODID, "textures/models/ControlUnit2.png");
    public static int                    CONTROL_UNIT_RENDER_ID;
    
    public static final ResourceLocation TOOLBOX_TEXTURE_LOCATION      = new ResourceLocation(ModInfo.MODID, "textures/models/Toolbox.png");
    public static int                    TOOLBOX_RENDER_ID;
    
    public static final ResourceLocation PADLOCK_TEXTURE_LOCATION      = new ResourceLocation(ModInfo.MODID, "textures/models/Toolbox.png");
    

    public static final ResourceLocation IRON_BOAT_TEXTURE_LOCATION	   = new ResourceLocation(ModInfo.MODID, "textures/entity/boatIron.png");
    
    public static final String           FLAG_TEXTURE_LOCATION         = "textures/models/flag/UV_";
    public static int                    FLAG_RENDER_ID;
    
    public static int                    WC_CHEST_RENDER_ID;
    
    public static int					 TANK_RENDER_ID;
}
