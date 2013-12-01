package dgrxf.watercraft.interfaces;

/**
 * 
 * ILockableBlock
 * 
 * used in tile entities of container blocks that needs to be locked
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public interface ILockableBlock {
    
    /**
     * Sets the lock state
     * 
     * @param lock
     *            New lock state
     */
    public void setLocked(boolean lock);
    
    /**
     * gets the lock state
     * 
     * @return Lock state
     */
    public boolean isLocked();
    
    /**
     * gets the lock code used for unlocking
     * 
     * @return Lock code
     */
    public int getCode();
    
    /**
     * Sets the lock code
     * 
     * @param code
     *            the code
     */
    public void setCode(int code);
    
}
