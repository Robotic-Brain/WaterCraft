package dgrxf.watercraft.tileentity;

/**
 * 
 * ITileEntityInterfaceEvent
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public interface ITileEntityInterfaceEvent {
    
    public void receiveInterfaceEvent(byte buttonid, byte[] extraInfo);
    
}
