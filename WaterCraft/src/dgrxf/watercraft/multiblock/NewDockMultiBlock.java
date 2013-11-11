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
        
        /**
         * these will hold the final dimensions
         */
        int leftExtend = 0;
        int rightExtend = 0;
        int frontExtend = 0;
        
        /**
         * these are the directions to search in
         */
        ForgeDirection rightDir = d.getRotation(ForgeDirection.UP);      // Set direction to right
        ForgeDirection leftDir = rightDir.getOpposite();                 // reverse rightDir (now left)
        ForgeDirection frontDir = leftDir.getRotation(ForgeDirection.UP);// rotate leftDir clockwise (now front)
        
        /********************** Search right **********************/
        rightExtend = searchLine(world, start, new Vector3(rightDir));
        if (rightExtend < MIN_SIDE_EXTEND) {
            return false;
        }
        
        /********************** Search left **********************/
        leftExtend = searchLine(world, start, new Vector3(leftDir));
        if (leftExtend < MIN_SIDE_EXTEND) {
            return false;
        }
        
        // check if max size exceeded
        if ((leftExtend + rightExtend + 1) > MAX_SIZE) {
            return false;
        }
        
        /********************** Search front **********************/
        // right front search start
        Vector3 fsStart = start.add((new Vector3(rightDir)).scalarMult(rightExtend));
        frontExtend = searchLine(world, fsStart, new Vector3(frontDir));
        if (frontExtend < MIN_FRONT_EXTEND) {
            return false;
        }
        
        /******************* Search front again *******************/
        // check if other (left) arm matches
        fsStart = start.add((new Vector3(leftDir)).scalarMult(leftExtend));
        int leftFrontExtend = searchLine(world, fsStart, new Vector3(frontDir));
        if (leftFrontExtend < frontExtend) {
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
        result = ((result > 0) ? result : 0);
        LogHelper.debug("Result Extend: " + result);
        return result;
    }
}
