package dgrxf.watercraft.client.gui.interfaces;

import java.util.ArrayList;

import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.util.Vector2;

public abstract class GuiScrollList extends GuiExtra {
    
    private ArrayList<String> list;
    private int               scrollPos;
    private int               selectedIndex;
    private boolean           isScrolling;
    private int               itemHeight;
    private int               maxItemDisplayed;
    private GuiColor          textColor = GuiColor.LIGHTGRAY;
    
    public GuiScrollList(int x, int y, int w, int h, int itemHeight, ArrayList<String> list) {
        super(x, y, w, h);
        this.itemHeight = itemHeight;
        this.list = list;
        maxItemDisplayed = (h / itemHeight) + 1;
    }
    
    public GuiScrollList(int x, int y, int w, int h, int itemHeight) {
        this(x, y, w, h, itemHeight, new ArrayList<String>());
    }
    
    public void clearList() {
        list.clear();
    }
    
    public void clearPos() {
        scrollPos = 0;
        selectedIndex = -1;
    }
    
    public boolean isActive() {
        return true;
    }
    
    public boolean isFocused() {
        return true;
    }
    
    public int getSize() {
        return list.size();
    }
    
    public void add(String str) {
        list.add(str);
    }
    
    public void remove(int index) {
        if (index < 0) {
            return;
        }
        list.remove(index);
        if (index == selectedIndex && list.size() > 0) {
            selectedIndex = 0;
        } else if (index == selectedIndex) {
            selectedIndex = -1;
        }
        if (list.size() < maxItemDisplayed) {
            scrollPos = 0;
        }
    }
    
    public ArrayList<String> getList() {
        return list;
    }
    
    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    private void doScroll(int y) {
        scrollPos = ((y  )- (getScrollBar()[1]) - (int) (getScrollBarSize().y / 2));
        int maxScroll = getScrollBar()[3] - (int) getScrollBarSize().y;
        if (scrollPos < 0) {
            scrollPos = 0;
        } else if (scrollPos > maxScroll) {
            scrollPos = maxScroll;
        }
    }
    
    private int getScroll() {
        float total = list.size() * itemHeight;
        float avalible = getHeight();
        float overflow = total - avalible;
        float lenght = (float) (getScrollBar()[3] - getScrollBarSize().y);
        return (int) (overflow * (scrollPos / lenght));
    }
    
    private int[] getItemButtonRect(int id) {
        int offsetY = itemHeight * id - getScroll();
        int height = itemHeight;
        int y = getY() + offsetY;
        
        if (offsetY < 0) {
            height += offsetY;
            y -= offsetY;
        } else if (offsetY + height >= getHeight()) {
            height = getHeight() - offsetY;
        }
        return new int[] { getX(), y, getWidth(), height, offsetY };
    }
    
    @Override
    public void drawBackground(GuiBase gui, int x, int y) {
        if (!isActive()) {
            return;
        }
        
        for (int i = 0; i < list.size(); i++) {
            int[] rect = getItemButtonRect(i);
            
            if (rect[3] > 0) {
                
                int srcY = (int) getScrollItemBackgroundPos().y;
                int hoverSrcY = (int) (inRect(gui, x, y, rect) ? getScrollItemHoverBackgroundPos().y
                        : -itemHeight);
                int selectedSrcY = (int) getScrollItemSelectedBackgroundPos().y;
                
                if (rect[4] < 0) {
                    srcY -= rect[4];
                    selectedSrcY -= rect[4];
                    hoverSrcY -= rect[4];
                }
                
                if (i == selectedIndex) {
                    draw(gui, rect, (int) getScrollItemSelectedBackgroundPos().x, selectedSrcY);
                } else {
                    draw(gui, rect, (int) getScrollItemBackgroundPos().x, srcY);
                }
                if (hoverSrcY > 0 && isFocused()) {
                    draw(gui, rect, (int) getScrollItemHoverBackgroundPos().x, hoverSrcY);
                }
                
            }
        }
        
        int[] pos = { getScrollBar()[0], getScrollBar()[1] + scrollPos,
                (int) getScrollBarSize().x, (int) getScrollBarSize().y };
        draw(gui, pos, (list.size() >= maxItemDisplayed ? (int) getScrollBarTexturePos().x
                : (int) getScrollBarTextureDisabledPos().x), (list.size() >= maxItemDisplayed ? (int) getScrollBarTexturePos().y
                : (int) getScrollBarTextureDisabledPos().y));
    }
    
    @Override
    public void drawForeground(GuiBase gui, int x, int y) {
        if (!isActive()) {
            return;
        }
        
        for (int i = 0; i < list.size(); i++) {
            int[] rect = getItemButtonRect(i);
            
            int x1 = rect[0] + 3;
            int textY = (int) Math.ceil(itemHeight / 2F - 4F);
            int y1 = rect[1] + (textY < 0 ? 0 : textY);
            if (rect[4] < 0) {
                y1 += rect[4];
            }
            
            int maxScroll = getScrollBar()[3] - (int) getScrollBarSize().y;
            if ((rect[4] >= -textY) && (rect[4] <= maxScroll)) {
                gui.getFontRenderer().drawString(list.get(i) == null ? ""
                        : list.get(i), x1, y1, textColor.toRGB());
            }
        }
    }
    
    public void onClick(int id) {
    }
    
    @Override
    public void mouseMoveClick(GuiBase gui, int x, int y, int button, long timeSinceClicked) {
        if (!isActive()) {
            return;
        }
        y -= gui.getTop();
        
        if (isScrolling) {
            if (button == -1) {
                this.isScrolling = false;
            } else {
                doScroll(y);
            }
        }
    }
    
    @Override
    public void mouseClick(GuiBase gui, int x, int y, int button) {
        if (!isActive()) {
            return;
        }
        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                int[] rect = getItemButtonRect(i);
                
                if ((rect[3] > 0) && (inRect(gui, x, y, rect))) {
                    if (selectedIndex == i) {
                        selectedIndex = -1;
                    } else {
                        selectedIndex = i;
                    }
                    onClick(i);
                    break;
                }
            }
        }
        int[] pos = { getScrollBar()[0], getScrollBar()[1], getScrollBar()[2],
                getScrollBar()[3] };
        if ((list.size() >= maxItemDisplayed) && (inRect(gui, x, y, pos))) {
            y -= gui.getTop();
            doScroll(y);
            isScrolling = true;
        }
        
    }
    
    @Override
    public void mouseReleased(GuiBase gui, int x, int y, int button) {
        if (isScrolling) {
            isScrolling = false;
        }
    }
    
    private int[] getScrollBar() {
        GuiRectangle scroll = getScrollBarArea();
        return new int[] { scroll.getX(), scroll.getY(), scroll.getWidth(),
                scroll.getHeight() };
    }
    
    public void setTextColor(GuiColor color) {
        textColor = color;
    }
    
    public abstract Vector2 getScrollItemBackgroundPos();
    
    public abstract Vector2 getScrollItemHoverBackgroundPos();
    
    public abstract Vector2 getScrollItemSelectedBackgroundPos();
    
    public abstract GuiRectangle getScrollBarArea();
    
    public abstract Vector2 getScrollBarSize();
    
    public abstract Vector2 getScrollBarTexturePos();
    
    public abstract Vector2 getScrollBarTextureDisabledPos();
    
}
