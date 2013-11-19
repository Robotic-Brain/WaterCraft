package dgrxf.watercraft.enumeration;

public enum ModuleType {
    //TODO: Add more modifier types.
	BOAT,
    ENGINE,
    ACCESSORY, //Not sure what will use this yet.
    BLOCK,
    INVENTORY;
    
    public final int modSlot;
    
    private ModuleType() {
        modSlot = ordinal();
    }
}
