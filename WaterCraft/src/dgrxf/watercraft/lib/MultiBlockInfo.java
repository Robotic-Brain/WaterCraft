package dgrxf.watercraft.lib;

import dgrxf.watercraft.multiblock.DockMultiBlock;

/**
 * 
 * MultiBlockInfo
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class MultiBlockInfo {
    
    public static DockMultiBlock dock;
    
    public static void init() {
        dock = new DockMultiBlock();
    }
    
}
