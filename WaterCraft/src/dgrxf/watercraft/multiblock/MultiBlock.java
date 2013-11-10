package dgrxf.watercraft.multiblock;

import net.minecraft.world.World;
import dgrxf.watercraft.util.LogHelper;

/**
 * Class Created By: ???
 * Class Last Edited By: xandayn
 * 
 * Class Last Edited On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class MultiBlock {
    
    /*
     *           WEST
     *        {1,1,1,1,1},
     *        {1,0,0,0,1}, 
     *  NORTH {1,0,0,0,1}, SOUTH
     *        {1,0,0,0,1},
     *        {1,1,1,1,1}
     *           EAST
     */
    
    private int[][][][] pattern;
    public int          layers;
    
    private int[]       blocks;
    
    public MultiBlock(int[][][][] blockPattern, int[] blocks) {
        pattern = blockPattern;
        this.blocks = blocks;
        layers = blockPattern.length;
    }
    
    /**
     * <h2>Pattern:</h2> &nbsp;&nbsp;Index 1: Layer<br>
     * &nbsp;&nbsp;Index 2: x-Direction<br>
     * &nbsp;&nbsp;Index 3: z-Direction<br>
     * &nbsp;&nbsp;I needed to change this to fit the new direction system. -
     * xandayn<br>
     * 
     * 
     * @param direction
     *            The direction of the check: 0 = North, 1 = South, 2 = West, 3
     *            = East
     * @param pattern
     *            The pattern of blocks to check against
     * @return If the pattern is matching returns true
     */
    public boolean isPatternCorrect(int direction, int[][][] pattern) {
        int val = 0;
        int size = 0;
        int dirToUse = direction - 2;
        for (int l = 0; l < this.layers; l++) {
            int[][] temp = this.pattern[l][dirToUse];
            
            for (int a = 0; a < temp.length; a++) {
                for (int p = 0; p < temp[a].length; p++) {
                    size++;
                    
                    if (temp[a][p] == -1 || blocks[temp[a][p]] == pattern[l][a][p]) {
                        val++;
                    }
                }
            }
        }
        return val == size ? true : false;
    }
    
    public boolean getMultiBlock(World worldObj, int xCoord, int yCoord, int zCoord, int direction) {
        int[][][] blocks;
        int tempX = xCoord;
        int tempY = yCoord - 1;
        int tempZ = zCoord;
        int val = 0;
        switch (direction - 2) {
            case 0:
                tempZ -= 3;
                break;
            case 1:
                tempZ += 3;
                break;
            case 2:
                tempX -= 3;
                break;
            case 3:
                tempX += 3;
                break;
        }
        blocks = new int[2][5][5];
        int yL = 0;
        int inc = 0;
        for (int y = -1; y < 1; y++) {
            int xL = 0, zL = 0;
            for (int i = tempX - 2; i <= tempX + 2; i++) {
                zL = 0;
                for (int j = tempZ - 2; j <= tempZ + 2; j++) {
                    inc++;
                    if (worldObj.blockExists(i, tempY + y, j)) {
                        blocks[yL][xL][zL] = worldObj.getBlockId(i, tempY + y, j);
                        int temp = tempY + y;
                    }
                    zL++;
                }
                xL++;
            }
            yL++;
        }
        return isPatternCorrect(direction, blocks);
    }
}
