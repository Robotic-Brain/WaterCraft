package dgrxf.watercraft.interfaces;

public interface IItemModule {
	/**
	 * @return the class of the module you wish your item to add
	 */
	public Class<? extends IBoatModule> getBoatModule();
}
