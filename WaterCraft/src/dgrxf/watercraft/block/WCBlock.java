package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Water Craft Block Baseclass
 * 
 * @author Drunk Mafia
 * 
 */
public class WCBlock extends Block {
    
    private boolean canRotate;
    
    public WCBlock(int id, Material material) {
        super(id, material);
    }
    
    public void setCanRotate(boolean val) {
        canRotate = val;
    }
    
    public boolean getCanRotate() {
        return canRotate;
    }
}
