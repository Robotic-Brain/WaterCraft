package dgrxf.watercraft.craneactions;

import java.util.HashMap;

import dgrxf.watercraft.interfaces.ICraneAction;

/**
 * 
 * ActionRegistry
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ActionRegistry {

	HashMap<Integer, ICraneAction> actions = new HashMap();
	
	public static void registerAction(int moduleID, ICraneAction action){
		
	}
	
}
