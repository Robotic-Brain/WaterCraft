package dgrxf.watercraft.interfaces;


/** used in tile entities of
 * container blocks
 * that needs to be locked
 */

public interface ILockableBlock {
	
	/**
	 * Sets the lock state
	 * 
	 * @param lock New lock state
	 */
	public void setLocked(boolean lock);
	
	/**
	 * gets the lock state
	 * 
	 * @return Lock state
	 */
	public boolean isLocked();
	
	/**
	 * gets the lock code 
	 * used for unlocking
	 * 
	 * @return Lock code
	 */
	public int getCode();
	
	/**
	 * Sets the lock code
	 * 
	 * @param code the code
	 */
	public void setCode(int code);

}
