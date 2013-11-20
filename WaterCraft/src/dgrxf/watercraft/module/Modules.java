package dgrxf.watercraft.module;

import dgrxf.watercraft.util.ModuleHelper;

public class Modules {
	public static void init(){
        ModuleHelper.registerModule(new dgrxf.watercraft.module.DumbModule());
        ModuleHelper.registerModule(new dgrxf.watercraft.module.ChestModule());
        ModuleHelper.registerModule(new dgrxf.watercraft.module.TankModule());
	}
}
