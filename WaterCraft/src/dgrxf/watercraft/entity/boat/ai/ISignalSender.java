package dgrxf.watercraft.entity.boat.ai;

/**
 * ISignalSender
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public interface ISignalSender {
    public void signalReceivedBy(String signal, Object receiver, Object... args);
}
