package dgrxf.watercraft.client.gui;

public enum GuiColor {
    BLACK(0, 0x000000),
    BLUE(1, 0x0000AA),
    GREEN(2, 0x00AA00),
    CYAN(3, 0x00AAAA),
    RED(4, 0xAA0000),
    PURPLE(5, 0xAA00AA),
    ORANGE(6, 0xFFAA00),
    LIGHTGRAY(7, 0xAAAAAA),
    GRAY(8, 0x555555),
    LIGHTBLUE(9, 0x5555FF),
    LIME(10, 0x55FF55),
    TURQUISE(11, 0x55FFFF),
    PINK(12, 0xFF5555),
    MAGNETA(13, 0xFF55FF),
    YELLOW(14, 0xFFFF55),
    WHITE(15, 0xFFFFFF);
    
    private int number;
    private int rgb;
    private GuiColor(int number, int rgb) {
        this.number = number;
        this.rgb = rgb;
    }
    
    @Override
    public String toString() {
        return "\u00a7" + Integer.toHexString(number);
    }
    
    public int toRGB() {
        return rgb;
    }
}
