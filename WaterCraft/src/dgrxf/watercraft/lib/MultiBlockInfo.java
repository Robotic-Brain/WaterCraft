package dgrxf.watercraft.lib;

import dgrxf.watercraft.multiblock.DockMultiBlock;

public class MultiBlockInfo {
    
    public static DockMultiBlock dock;
    
    public static void init() {
        dock = new DockMultiBlock();
    }
    
}
