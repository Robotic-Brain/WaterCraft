package dgrxf.watercraft.multiblock;

import dgrxf.watercraft.util.LogHelper;

public class MultiBlock {
    
    /*
     *   MultiBlock orientation
     * 
     * 		      WEST
     * 	      {1,1,1,1,1},
     *        {1,0,0,0,1}, 
     *  NORTH {1,0,0,0,1}, SOUTH
     *        {1,0,0,0,1},
     * 		  {1,1,1,1,1}
     * 			  EAST
     * 
     */
	
    private int[][][][] pattern;
    public int layers;
    
    private int[] blocks;
    
    public MultiBlock(int[][][][] blockPattern, int[] blocks) {
        pattern = blockPattern;
        this.blocks = blocks;
        layers = blockPattern.length;
    }
    
    /**
     * <h2>Pattern:</h2> &nbsp;&nbsp;Index 1: Layer<br>
     * &nbsp;&nbsp;Index 2: x-Direction<br>
     * &nbsp;&nbsp;Index 3: z-Direction<br>
     * &nbsp;&nbsp;I needed to change this to fit the new direction system. - xandayn<br>
     * 
     * 
     * @param direction
     *            The direction of the check: 0 = North, 1 = South, 2 = West, 3 = East
     * @param pattern
     *            The pattern of blocks to check against
     * @return If the pattern is matching returns true
     */
    public boolean isPatternCorrect(int direction, int[][][] pattern) {
        int val = 0;
        int size = 0;
        int dirToUse = direction-2;
        LogHelper.debug(dirToUse);
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
}
