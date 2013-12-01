package dgrxf.watercraft.client.gui.interfaces;

/**
 * 
 * KeySlider
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class KeySlider extends GuiRectangle {
	
	public boolean clicked;

	public KeySlider(int x, int y, int w, int h) {
		super(x, y, w, h);
		clicked = false;
	}
	
	
}
