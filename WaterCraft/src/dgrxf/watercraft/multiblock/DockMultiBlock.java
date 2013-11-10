package dgrxf.watercraft.multiblock;

import net.minecraft.block.Block;

public class DockMultiBlock extends MultiBlock {
    
    /*
     * The orientation
     * 
     * WEST {1,1,1,1,1}, {1,0,0,0,1}, NORTH {1,0,0,0,1}, SOUTH {1,0,0,0,1},
     * {1,1,1,1,1} EAST
     */
    
    private static int[][][][] multiBlockArray = { {// Layer 1
            {// North
            { -1, -1, -1, -1, -1 }, { 2, 2, 2, 2, -1 }, { 2, 2, 2, 2, -1 },
            { 2, 2, 2, 2, -1 }, { -1, -1, -1, -1, -1 } },
            
            {// South
            { -1, -1, -1, -1, -1 }, { -1, 2, 2, 2, 2 }, { -1, 2, 2, 2, 2 },
            { -1, 2, 2, 2, 2 }, { -1, -1, -1, -1, -1 } },
            
            {// West
            { -1, 2, 2, 2, -1 }, { -1, 2, 2, 2, -1 }, { -1, 2, 2, 2, -1 },
            { -1, 2, 2, 2, -1 }, { -1, -1, -1, -1, -1 }
            
            },
            
            {// East
            { -1, -1, -1, -1, -1 }, { -1, 2, 2, 2, -1 }, { -1, 2, 2, 2, -1 },
            { -1, 2, 2, 2, -1 }, { -1, 2, 2, 2, -1 }
            
            }
            
            }, {// Layer 2
            
            {// North -z
            
            { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 1 },
            { 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } },
            
            {// South +z
            { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 } },
            
            {// West -x
            { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } },
            
            {// East +x
            { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 } } } };
    
    private static int[]       blocks          = { 0,
            Block.woodSingleSlab.blockID, Block.waterStill.blockID };
    
    public DockMultiBlock() {
        super(multiBlockArray, blocks);
    }
}
