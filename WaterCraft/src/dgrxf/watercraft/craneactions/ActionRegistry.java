package dgrxf.watercraft.craneactions;

import java.util.HashMap;

import dgrxf.watercraft.interfaces.ICraneAction;

public class ActionRegistry {

	HashMap<Integer, ICraneAction> actions = new HashMap();
	
	public static void registerAction(int moduleID, ICraneAction action){
		
	}
	
}
