package dgrxf.watercraft.enumeration;

public enum ModifierType {
	//TODO: Add more modifier types.
	ENGINE;
	
	
	public final int modSlot;
	
	private ModifierType(){
		modSlot = ordinal();
	}
}
