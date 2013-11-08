package dgrxf.watercraft.multiblock;

public class MultiBlock {
    
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
     * <br>
     * 
     * @param direction
     *            The direction of the check: 0 = West, 1 = East, 2 = South, 3 =
     *            North, 0 = No Special
     * @param pattern
     *            The pattern of blocks to check against
     * @return If the pattern is matching returns true
     */
    public boolean isPatternCorrect(int direction, int[][][] pattern) {
        int val = 0;
        int size = 0;
        for (int l = 0; l < this.layers; l++) {
            int[][] temp = this.pattern[l][direction];
            
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
