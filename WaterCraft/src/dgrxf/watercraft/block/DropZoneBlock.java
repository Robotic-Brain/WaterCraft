package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import dgrxf.watercraft.lib.BlockInfo;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By: Drunk Mafia Class Last Edited On:11/08/2013 MM/DD/YYYYY
 * 
 */

public class DropZoneBlock extends WCBlock {
    public DropZoneBlock() {
        super(BlockInfo.DROPZONE_ID, Material.rock);
        setUnlocalizedName(BlockInfo.BUOY_UNLOCALIZED_NAME);
    }
}
