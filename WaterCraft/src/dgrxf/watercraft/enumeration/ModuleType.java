package dgrxf.watercraft.enumeration;

public enum ModuleType {
    //TODO: Add more modifier types.
	BOAT,
    ENGINE,
    BLOCK,
    INVENTORY;
    
    public final int modSlot;
    
    private ModuleType() {
        modSlot = ordinal();
    }
}
