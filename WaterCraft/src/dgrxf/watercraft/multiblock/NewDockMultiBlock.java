package dgrxf.watercraft.multiblock;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Rectangle;
import dgrxf.watercraft.util.Vector2;
import dgrxf.watercraft.util.Vector3;

/**
 * WARNING: Mess ahead!
 * 
 */
public class NewDockMultiBlock {
    
    private static final int BOUNDING_BLOCK   = Block.woodSingleSlab.blockID;
    private static final int WATER_BLOCK      = Block.waterStill.blockID;
    private static final int MAX_SIZE         = 10;
    private static final int MIN_SIDE_EXTEND  = 1;
    private static final int MIN_FRONT_EXTEND = 2;
    
    /**
     * This method searches for a rect connected to x,y,z
     * 
     * @param world
     * @param start
     * @param direction
     * @return Rectangle of MultiBlock bounds or NULL
     */
    public static Rectangle checkMultiblock(World world, Vector3 start, ForgeDirection d) {
        
        start = start.add(new Vector3(d));
        
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
            return null;
        }
        
        /********************** Search left **********************/
        leftExtend = searchLine(world, start, new Vector3(leftDir));
        if (leftExtend < MIN_SIDE_EXTEND) {
            return null;
        }
        
        // check if max size exceeded
        if ((leftExtend + rightExtend + 1) > MAX_SIZE) {
            return null;
        }
        
        /********************** Search front **********************/
        // right front search start
        Vector3 fsStart = start.add((new Vector3(rightDir)).scalarMult(rightExtend));
        frontExtend = searchLine(world, fsStart, new Vector3(frontDir));
        if (frontExtend < MIN_FRONT_EXTEND) {
            return null;
        }
        
        /******************* Search front again *******************/
        // check if other (left) arm matches
        fsStart = start.add((new Vector3(leftDir)).scalarMult(leftExtend));
        int leftFrontExtend = searchLine(world, fsStart, new Vector3(frontDir));
        if (leftFrontExtend < MIN_FRONT_EXTEND) {
            return null;
        }
        
        // Clamp front extend to smaller one
        frontExtend = Math.min(frontExtend, leftFrontExtend);
        
        /******************* Building rectangle *******************/
        Vector3 p0 = start.add(new Vector3(leftDir).scalarMult(leftExtend));    // first Corner
        Vector3 p1 = start.add(new Vector3(rightDir).scalarMult(rightExtend));  // second corner - just temporary
        Vector3 p2 = p1.add(new Vector3(frontDir).scalarMult(frontExtend));     // third corner
        
        Rectangle rect = new Rectangle(p0.xz(), p2.sub(p0).xz());
        Rectangle interior = rect.trim(new Vector2(-2)).translate(new Vector2(1.0f));
        
        /********************* Check for Water ********************/
        if (!searchFilledRect(world, interior, (int) start.y - 1)) {
            return null;
        }
        
        /*LogHelper.debug("P0: " + p0);
        LogHelper.debug("P2: " + p2);
        LogHelper.debug("Diff: " + p2.sub(p0));
        LogHelper.debug("Total Size: " + leftExtend + " " + rightExtend + " " + frontExtend);*/
        
        //LogHelper.debug(rect);
        //LogHelper.debug("Offset" + interior);
        
        return interior;
    }
    
    private static boolean checkBlock(World world, int x, int y, int z, int blockId) {
        return blockId == world.getBlockId(x, y, z);
    }
    
    private static boolean checkBlock(World world, Vector3 p, int blockId) {
        return checkBlock(world, (int) p.x, (int) p.y, (int) p.z, blockId);
    }
    
    /**
     * Searches for a continuous line from start in inc direction
     * 
     * @param world
     * @param s
     *            Block to start from
     * @param inc
     *            Direction to increment
     * @return length of line
     */
    private static int searchLine(World world, Vector3 s, Vector3 inc) {
        Vector3 sPos = s;
        int i = 0;
        for (i = 1; i <= MAX_SIZE && checkBlock(world, sPos, BOUNDING_BLOCK); ++i) {
            //LogHelper.debug("Search at: " + sPos);
            sPos = s.add(inc.scalarMult(i));
        }
        int result = i - 2;
        result = ((result > 0) ? result : 0);
        //LogHelper.debug("Result Extend: " + result);
        return result;
    }
    
    /**
     * This method checks if the given rect is filled with water
     * 
     * @param world
     * @param r
     *            Rectangle to search in
     * @param yOffset
     *            Y level to search on
     * @return
     */
    private static boolean searchFilledRect(World world, Rectangle r, int yOffset) {
        Vector3 start = new Vector3(r.x, yOffset, r.y);
        
        for (int dx = 0; dx <= r.w; ++dx) {
            for (int dz = 0; dz <= r.h; ++dz) {
                if (!checkBlock(world, start.add(new Vector3(dx, 0, dz)), WATER_BLOCK)) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
