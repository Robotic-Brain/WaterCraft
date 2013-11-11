package dgrxf.watercraft.multiblock;

import org.lwjgl.util.vector.Vector3f;

import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class NewDockMultiBlock {
    
    private static final int BOUNDING_BLOCK = Block.woodSingleSlab.blockID;
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIDE_EXTEND = 1;
    private static final int MIN_FRONT_EXTEND = 2;
    
    /**
     * This method searches for a rect connected to x,y,z
     * 
     * @param world
     * @param start
     * @param direction
     * @return true if MultiBlock found
     */
    public static boolean checkMultiblock(World world, Vector3 start, ForgeDirection d) {
        
        start = start.add(new Vector3(d));
        
        LogHelper.debug("Hayo!!");
        LogHelper.debug(checkBlock(world, start));
        
        int leftExtend = 0;
        int rightExtend = 0;
        int frontExtend = 0;
        
        // Set increment direction to right
        ForgeDirection searchDir = d.getRotation(ForgeDirection.UP);
        rightExtend = searchLine(world, start, new Vector3(searchDir));
        if (rightExtend < MIN_SIDE_EXTEND) {
            return false;
        }
        
        // reverse increment direction (now left)
        searchDir = searchDir.getOpposite();
        leftExtend = searchLine(world, start, new Vector3(searchDir));
        if (leftExtend < MIN_SIDE_EXTEND) {
            return false;
        }
        
        // rotate increment direction clockwise (now front)
        searchDir = searchDir.getRotation(ForgeDirection.UP);
        frontExtend = searchLine(world, start.add((new Vector3(d.getRotation(ForgeDirection.UP))).scalarMult(rightExtend)), new Vector3(searchDir));
        if (frontExtend < MIN_FRONT_EXTEND) {
            return false;
        }
        
        LogHelper.debug("Total Size: " + leftExtend + " " + rightExtend + " " + frontExtend);
        
        return false;
    }
    
    private static boolean checkBlock(World world, Vector3 p) {
        return BOUNDING_BLOCK == world.getBlockId((int)p.x, (int)p.y, (int)p.z);
    }
    
    /**
     * Searches for a continuous line from start in inc direction
     * 
     * @param world
     * @param s Block to start from
     * @param inc Direction to increment 
     * @return length of line
     */
    private static int searchLine(World world, Vector3 s, Vector3 inc) {
        Vector3 sPos = s;
        int i = 0;
        for (i = 1; i <= MAX_SIZE && checkBlock(world, sPos); ++i) {
            //LogHelper.debug("Search at: " + sPos);
            sPos = s.add(inc.scalarMult(i));
        }
        int result = i-2;
        LogHelper.debug("Result Extend: " + result);
        return result;
    }
}
