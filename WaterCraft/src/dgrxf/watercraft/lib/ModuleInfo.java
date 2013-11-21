package dgrxf.watercraft.lib;

import dgrxf.watercraft.util.ModuleRegistry;

public class ModuleInfo {
	public static void init(){
        ModuleRegistry.registerModule(new dgrxf.watercraft.module.DumbModule());
        ModuleRegistry.registerModule(new dgrxf.watercraft.module.ChestModule());
	}
}
