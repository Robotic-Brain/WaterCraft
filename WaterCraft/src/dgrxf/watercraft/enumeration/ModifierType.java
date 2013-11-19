package dgrxf.watercraft.enumeration;

public enum ModifierType {
    //TODO: Add more modifier types.
    ENGINE,
    ACCESSORY;
    
    public final int modSlot;
    
    private ModifierType() {
        modSlot = ordinal();
    }
}
